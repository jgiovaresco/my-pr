package fr.mypr.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
		"fr.mypr.controller",
		"fr.mypr.security.controller",
		"fr.mypr.user.registration.controller"
})
public class WebConfiguration
{
	@Bean
	public SimpleMappingExceptionResolver exceptionResolver()
	{
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

		Properties exceptionMappings = new Properties();

		exceptionMappings.put("java.lang.Exception", "error/error");
		exceptionMappings.put("java.lang.RuntimeException", "error/error");

		exceptionResolver.setExceptionMappings(exceptionMappings);

		Properties statusCodes = new Properties();

		statusCodes.put("error/404", "404");
		statusCodes.put("error/error", "500");

		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}

}
