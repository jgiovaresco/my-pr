package fr.mypr.ihm;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.*;
import java.time.*;
import java.util.Date;

public class MyPrExceptionResolver extends SimpleMappingExceptionResolver
{
	public static final String DEFAULT_ERROR_VIEW = "error/error";

	private Clock clock;

	public MyPrExceptionResolver()
	{
		clock = Clock.systemDefaultZone();
		setWarnLogCategory(MyPrExceptionResolver.class.getName());
	}

	@Override
	public String buildLogMessage(Exception e, HttpServletRequest req)
	{
		return "MVC exception: " + e.getLocalizedMessage();
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
	{
		ModelAndView mav = super.doResolveException(request, response, handler, exception);
		mav.addObject("url", request.getRequestURL().toString());
		mav.addObject("timestamp", Date.from(Instant.now(clock)));
		return mav;
	}
}
