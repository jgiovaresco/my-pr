package fr.mypr;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.pr.application.*;
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
	public PersonalRecordApplicationService personalRecordApplicationService()
	{
		return mock(PersonalRecordApplicationService.class);
	}

	@Bean
	public PersonalRecordQueryService personalRecordQueryService()
	{
		return mock(PersonalRecordQueryService.class);
	}

	@Bean
	public ExerciseQueryService exerciseQueryService()
	{
		return mock(ExerciseQueryService.class);
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
