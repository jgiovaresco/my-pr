package fr.mypr;

import fr.mypr.user.registration.RegistrationService;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;

@Configuration
public class UnitTestContext
{
	@Bean
	public RegistrationService repositoryRegistrationService()
	{
		return mock(RegistrationService.class);
	}

	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("templates/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}
}
