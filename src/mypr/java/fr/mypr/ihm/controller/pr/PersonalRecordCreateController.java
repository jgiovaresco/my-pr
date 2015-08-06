package fr.mypr.ihm.controller.pr;


import fr.mypr.ihm.controller.pr.data.PersonalRecordCreateForm;
import fr.mypr.ihm.security.SecurityUtil;
import fr.mypr.pr.application.*;
import fr.mypr.pr.application.data.ExerciseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@SessionAttributes({PersonalRecordCreateForm.PR_ATTRIBUTE_FORM, PersonalRecordCreateForm.EXERCISES_ATTRIBUTE_FORM})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalRecordCreateController
{
	protected static final String VIEW_NAME_PR_CREATION_PAGE = "pr/creationForm";

	private PersonalRecordQueryService personalRecordQueryService;
	private PersonalRecordApplicationService personalRecordApplicationService;
	private ExerciseQueryService exerciseQueryService;

	@RequestMapping(value = "/pr/new", method = RequestMethod.GET)
	public String showPersonalRecordCreationForm(Model model)
	{
		log.debug("Rendering personal record creation form page.");

		model.addAttribute(PersonalRecordCreateForm.PR_ATTRIBUTE_FORM, PersonalRecordCreateForm.builder().build());

		Collection<ExerciseData> exercises = exerciseQueryService.allExercises();
		model.addAttribute(PersonalRecordCreateForm.EXERCISES_ATTRIBUTE_FORM, exercises);

		return VIEW_NAME_PR_CREATION_PAGE;
	}

	@RequestMapping(value = "/pr/new", method = RequestMethod.POST)
	public String addNewPersonalRecord(
			@Valid @ModelAttribute(PersonalRecordCreateForm.PR_ATTRIBUTE_FORM) PersonalRecordCreateForm personalRecordData,
			BindingResult result)
	{
		log.debug("Creating new pr with information: {}", personalRecordData);
		if (result.hasErrors())
		{
			log.debug("Validation errors found. Rendering form view.");
			return VIEW_NAME_PR_CREATION_PAGE;
		}

		log.debug("No validation errors found. Continuing personal creation process.");

		String athleteId = SecurityUtil.principal().getUsername();
		personalRecordApplicationService
				.newExercisePersonalRecordForAthlete(athleteId,
				                                     personalRecordData.getSelectedExerciseId(), personalRecordData.getDate(),
				                                     personalRecordData.getValue());

		return "redirect:/pr";
	}
}
