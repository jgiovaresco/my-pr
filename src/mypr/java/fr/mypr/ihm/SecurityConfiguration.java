package fr.mypr.ihm;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.ihm.security.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private IdentityApplicationService identityApplicationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		// @formatter:off
		web
			.ignoring()
				.antMatchers("/css/**", "/js/**", "/fonts/**", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.jpg");
		// @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// @formatter:off
		http
			.csrf()
				.disable()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login/authenticate")
				.failureUrl("/login?error=bad_credentials")
				.defaultSuccessUrl("/pr")
				.usernameParameter("email")
			.and()
				.logout()
					.deleteCookies("JSESSIONID")
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
			.and()
				.authorizeRequests()
					.antMatchers(
							"/", "/login", "/register", "/user/register/**"
					).permitAll()
					.antMatchers("/**").hasRole("USER")
			;
		// @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
				.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder);
	}

	@Bean
	public UserDetailsService userDetailsService()
	{
		return new RepositoryUserDetailsService(identityApplicationService);
	}
}
