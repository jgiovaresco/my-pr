package fr.mypr.security.controller;

import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage()
	{
		LOGGER.debug("Rendering login page");
		return "user/login";
	}
}
