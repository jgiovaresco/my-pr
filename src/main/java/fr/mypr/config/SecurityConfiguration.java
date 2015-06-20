package fr.mypr.config;

import fr.mypr.security.service.*;
import fr.mypr.user.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		// @formatter:off
		web
			//Spring Security ignores request to static resources such as CSS or JS files.
			.ignoring()
				.antMatchers("/static/**");
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
				.usernameParameter("email")
			.and()
				.logout()
					.deleteCookies("JSESSIONID")
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
			.and()
				.authorizeRequests()
					.antMatchers(
							"/login"
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
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public UserDetailsService userDetailsService()
	{
		return new RepositoryUserDetailsService(userAccountRepository);
	}
}
