package fr.mypr.ihm.controller.pr;


import fr.mypr.ihm.security.SecurityUtil;
import fr.mypr.pr.application.PersonalRecordQueryService;
import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalRecordListController
{
	private PersonalRecordQueryService personalRecordQueryService;

	@RequestMapping(value = "/pr", method = RequestMethod.GET)
	public String showPersonalRecords(Model model)
	{
		log.debug("Rendering personal records list page.");

		String athleteId = SecurityUtil.principal().getUsername();
		Collection<AthleteExercisePersonalRecordData> personalRecordData = personalRecordQueryService.allPersonalRecordsOfAthlete(athleteId);
		model.addAttribute("personalRecords", personalRecordData);

		return "/pr/list";
	}
}
