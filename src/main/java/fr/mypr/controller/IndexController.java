package fr.mypr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
	@RequestMapping("/")
	public String showFirstPage(Model p_model)
	{
		p_model.addAttribute("name", "My PR");
		return "index";
	}
}
