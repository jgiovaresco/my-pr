package fr.mypr.ihm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PersonalRecordController
{
	@RequestMapping(value = "/pr", method = RequestMethod.GET)
	public String showPersonalRecords()
	{
		return "/pr/list";
	}
}
