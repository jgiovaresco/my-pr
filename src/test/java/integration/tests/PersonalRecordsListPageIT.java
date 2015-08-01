package integration.tests;

import fr.mypr.MyPrApplication;
import integration.IntegrationTestConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static integration.IntegrationTestConstants.User.REGISTERED_USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"integrationTest"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {IntegrationTestConfig.class})
@WebAppConfiguration
public class PersonalRecordsListPageIT
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
	public void showPersonalRecordsListPage_asAnonymous_should_render_login_page() throws Exception
	{
		mockMvc.perform(get("/pr/exercise/new"))
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void showPersonalRecordsListPage_asRegisteredUser_should_render_personal_records_list() throws Exception
	{
		mockMvc.perform(get("/pr")
				                .with(user(REGISTERED_USER.getUserDetails())))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("/pr/list"));
	}
}
