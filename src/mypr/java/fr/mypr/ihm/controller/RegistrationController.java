package fr.mypr.ihm.controller;


import fr.mypr.identityaccess.application.*;
import fr.mypr.identityaccess.command.RegisterUserCommand;
import fr.mypr.identityaccess.domain.model.User;
import fr.mypr.ihm.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes("user")
@Slf4j
public class RegistrationController
{
	protected static final String ERROR_CODE_EMAIL_EXIST = "Email already exists";
	protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registrationForm";

	private IdentityApplicationService registrationService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public RegistrationController(IdentityApplicationService registrationService, PasswordEncoder passwordEncoder)
	{
		this.registrationService = registrationService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String showRegistrationForm(Model model)
	{
		log.debug("Rendering registration page.");

		RegistrationForm registration = RegistrationForm.builder().build();
		log.debug("Rendering registration form with information: {}", registration);

		model.addAttribute(RegistrationForm.MODEL_ATTRIBUTE_USER_FORM, registration);

		return VIEW_NAME_REGISTRATION_PAGE;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
	                                  BindingResult result) throws DuplicateEmailException
	{
		log.debug("Registering user account with information: {}", userAccountData);
		if (result.hasErrors())
		{
			log.debug("Validation errors found. Rendering form view.");
			return VIEW_NAME_REGISTRATION_PAGE;
		}

		log.debug("No validation errors found. Continuing registration process.");

		User registered;
		try
		{
			registered = createUserAccount(userAccountData, result);
		}
		catch (DuplicateEmailException e)
		{
			log.debug("An email address was found from the database. Rendering form view.");
			return VIEW_NAME_REGISTRATION_PAGE;
		}

		log.debug("Registered user account with information: {}", registered);

		SecurityUtil.logInUser(registered);
		log.debug("User {} has been signed in", registered);

		return "redirect:/";
	}

	private User createUserAccount(RegistrationForm userAccountData, BindingResult result)
	{
		log.debug("Creating user account with information: {}", userAccountData);
		User registered;

		try
		{
			registered = registrationService.registerUser(RegisterUserCommand.builder()
					                                              .email(userAccountData.getEmail())
					                                              .password(passwordEncoder.encode(userAccountData.getPassword()))
					                                              .firstName(userAccountData.getFirstName())
					                                              .lastName(userAccountData.getLastName())
					                                              .build());
		}
		catch (DuplicateEmailException ex)
		{
			log.debug("An email address: {} exists.", userAccountData.getEmail());
			addFieldError(
					RegistrationForm.MODEL_ATTRIBUTE_USER_FORM,
					RegistrationForm.FIELD_NAME_EMAIL,
					userAccountData.getEmail(),
					ERROR_CODE_EMAIL_EXIST,
					result);
			throw ex;
		}

		return registered;
	}

	private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode, BindingResult result)
	{
		log.debug("Adding field error object's: {} field: {} with error code: {}", objectName, fieldName, errorCode);
		FieldError error = new FieldError(
				objectName,
				fieldName,
				fieldValue,
				false,
				new String[]{errorCode},
				new Object[]{},
				errorCode
		);

		result.addError(error);
		log.debug("Added field error: {} to binding result: {}", error, result);
	}
}
