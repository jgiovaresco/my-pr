package integration.tests;

import fr.mypr.MyPrApplication;
import fr.mypr.identityaccess.domain.model.MyPrUserDetails;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MyPrApplication.class})
@WebAppConfiguration
public class IndexPageIT
{
	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(springSecurityFilterChain)
				.build();
	}

	@Test
	public void showIndexPage_asAnonymous_should_render_index_page_with_login_link() throws Exception
	{
		mockMvc.perform(get("/"))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().string(containsString("<a href=\"/login\">Log In</a>")));
	}

	@Test
	public void showIndexPage_asRegisteredUser_should_render_index_page_with_logout_link() throws Exception
	{
		UserDetails registeredUser = MyPrUserDetails.builder()
				.username(REGISTERED_USER.getEmail())
				.password(REGISTERED_USER.getPassword())
				.firstName(REGISTERED_USER.getFirstName())
				.build();

		// @formatter:off
		mockMvc.perform(get("/")
				.with(user(registeredUser))
				)
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().string(containsString("<a href=\"/logout\">Log Out</a>")));
		// @formatter:on
	}
}
