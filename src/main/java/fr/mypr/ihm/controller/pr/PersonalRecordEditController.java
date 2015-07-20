package fr.mypr.ihm.controller.pr;


import fr.mypr.ihm.controller.pr.data.PersonalRecordEditForm;
import fr.mypr.ihm.security.SecurityUtil;
import fr.mypr.pr.application.*;
import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalRecordEditController
{
	protected static final String VIEW_NAME_PR_EDITION_PAGE = "pr/editionForm";

	private PersonalRecordQueryService personalRecordQueryService;
	private PersonalRecordApplicationService personalRecordApplicationService;
	private ExerciseQueryService exerciseQueryService;

	@RequestMapping(value = "/pr/edit", method = RequestMethod.GET)
	public String showPersonalRecordEditionForm(Model model, @RequestParam("prId") String aPersonalRecordId)
	{
		log.debug("Rendering personal record edition form page.");

		String athleteId = SecurityUtil.principal().getUsername();
		AthleteExercisePersonalRecordData personalRecordData = personalRecordQueryService
				.personalRecordDataOfId(athleteId, aPersonalRecordId);

		model.addAttribute(PersonalRecordEditForm.PR_ATTRIBUTE_FORM,
		                   PersonalRecordEditForm.builder()
				                   .id(personalRecordData.getId())
				                   .value(personalRecordData.getValue())
				                   .date(personalRecordData.getDate())
				                   .exerciseName(personalRecordData.getExerciseName())
				                   .build());

		return VIEW_NAME_PR_EDITION_PAGE;
	}

	@RequestMapping(value = "/pr/edit", method = RequestMethod.POST)
	public String updatePersonalRecord(
			@Valid @ModelAttribute(PersonalRecordEditForm.PR_ATTRIBUTE_FORM) PersonalRecordEditForm personalRecordData,
			BindingResult result)
	{
		log.debug("Updating pr with information: {}", personalRecordData);
		if (result.hasErrors())
		{
			log.debug("Validation errors found. Rendering form view.");
			return VIEW_NAME_PR_EDITION_PAGE;
		}

		log.debug("No validation errors found. Continuing personal edition process.");

		personalRecordApplicationService
				.newPersonalRecordForExercise(personalRecordData.getId(),
				                              personalRecordData.getDate(),
				                              personalRecordData.getValue());

		return "redirect:/pr";
	}
}
