package fr.mypr.ihm.controller;


import fr.mypr.identityaccess.domain.model.MyPrUserDetails;
import fr.mypr.ihm.controller.data.PersonalRecordForm;
import fr.mypr.pr.application.*;
import fr.mypr.pr.application.data.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@SessionAttributes({PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM, PersonalRecordForm.EXERCISES_ATTRIBUTE_FORM})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalRecordController
{
	protected static final String VIEW_NAME_PR_CREATION_PAGE = "pr/creationForm";

	private PersonalRecordQueryService personalRecordQueryService;
	private PersonalRecordApplicationService personalRecordApplicationService;
	private ExerciseQueryService exerciseQueryService;

	@RequestMapping(value = "/pr", method = RequestMethod.GET)
	public String showPersonalRecords(Model model)
	{
		log.debug("Rendering personal records list page.");

		String athleteId = ((MyPrUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		Collection<AthleteExercisePersonalRecordData> personalRecordData = personalRecordQueryService.personalRecordForAthlete(athleteId);
		model.addAttribute("personalRecords", personalRecordData);

		return "/pr/list";
	}

	@RequestMapping(value = "/pr/new", method = RequestMethod.GET)
	public String showPersonalRecordCreationForm(Model model)
	{
		log.debug("Rendering personal record creation form page.");

		model.addAttribute(PersonalRecordForm.PR_ATTRIBUTE_FORM, new PersonalRecordForm());

		Collection<ExerciseData> exercises = exerciseQueryService.allExercises();
		model.addAttribute("exercises", exercises);

		return VIEW_NAME_PR_CREATION_PAGE;
	}


	@RequestMapping(value = "/pr/new", method = RequestMethod.POST)
	public String addNewPersonalRecord(@Valid @ModelAttribute(PersonalRecordForm.PR_ATTRIBUTE_FORM) PersonalRecordForm personalRecordData,
	                                   BindingResult result)
	{
		log.debug("Creating new pr with information: {}", personalRecordData);
		if (result.hasErrors())
		{
			log.debug("Validation errors found. Rendering form view.");
			return VIEW_NAME_PR_CREATION_PAGE;
		}

		log.debug("No validation errors found. Continuing personal creation process.");

		String athleteId = ((MyPrUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		personalRecordApplicationService
				.newExercisePersonalRecordForAthlete(athleteId,
				                                     personalRecordData.getSelectedExerciseId(), personalRecordData.getDate(),
				                                     personalRecordData.getValue());

		return "redirect:/pr";
	}
}
