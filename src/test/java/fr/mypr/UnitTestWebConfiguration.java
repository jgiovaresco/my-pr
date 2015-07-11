package fr.mypr;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;

@EnableWebMvc
public class UnitTestWebConfiguration
{
	@Bean
	public IdentityApplicationService identityApplicationService()
	{
		return mock(IdentityApplicationService.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return mock(PasswordEncoder.class);
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
