package fr.mypr.ihm;

import org.springframework.context.annotation.*;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
		"fr.mypr.ihm.controller"
})
public class WebConfiguration
{
	@Bean
	public MyPrExceptionResolver exceptionResolver()
	{
		MyPrExceptionResolver exceptionResolver = new MyPrExceptionResolver();

		Properties exceptionMappings = new Properties();
		exceptionMappings.put("java.lang.Exception", MyPrExceptionResolver.DEFAULT_ERROR_VIEW);
		exceptionMappings.put("java.lang.RuntimeException", MyPrExceptionResolver.DEFAULT_ERROR_VIEW);
		exceptionResolver.setExceptionMappings(exceptionMappings);

		Properties statusCodes = new Properties();
		statusCodes.put("error/404", "404");
		statusCodes.put(MyPrExceptionResolver.DEFAULT_ERROR_VIEW, "500");
		exceptionResolver.setStatusCodes(statusCodes);

		return exceptionResolver;
	}

}
