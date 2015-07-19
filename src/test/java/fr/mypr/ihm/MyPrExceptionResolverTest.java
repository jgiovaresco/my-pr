package fr.mypr.ihm;

import org.assertj.core.data.MapEntry;
import org.junit.*;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.mock.web.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.*;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class MyPrExceptionResolverTest
{
	private static final ZoneId ZONE_ID = ZoneId.systemDefault();
	private static final LocalDateTime DATE_TIME = LocalDateTime.of(2015, 5, 15, 10, 10, 20, 222);
	private static final Clock FIXED_CLOCK = Clock.fixed(DATE_TIME.atZone(ZONE_ID).toInstant(), ZONE_ID);

	private MyPrExceptionResolver exceptionResolver;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Exception exception;

	@Before
	public void setUp() throws Exception
	{
		exceptionResolver = new MyPrExceptionResolver();
		exceptionResolver.setDefaultErrorView("default-view");

		Whitebox.setInternalState(exceptionResolver, "clock", FIXED_CLOCK);

		request = new MockHttpServletRequest();
		request.setRequestURI("/my_page");

		response = new MockHttpServletResponse();

		exception = new Exception("an exception");
	}

	@Test
	public void buildLogMessage_should_return_log_message()
	{
		assertThat(exceptionResolver.buildLogMessage(exception, request))
				.isEqualTo("MVC exception: an exception");
	}

	@Test
	public void resolveException_should_apply_default_behaviour_and_provide_model_with_date_and_url_properties()
	{
		ModelAndView mav = exceptionResolver.resolveException(request, response, new Object(), exception);
		assertThat(mav.getModel())
				.contains(MapEntry.entry("url", "http://localhost/my_page"))
				.contains(MapEntry.entry("timestamp", Date.from(Instant.now(FIXED_CLOCK))));
	}

}