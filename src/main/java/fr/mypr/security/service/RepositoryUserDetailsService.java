package fr.mypr.security.service;

import fr.mypr.security.user.MyPrUserDetails;
import fr.mypr.user.model.UserAccount;
import fr.mypr.user.repository.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;


@Component
public class RepositoryUserDetailsService implements UserDetailsService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserDetailsService.class);

	private UserAccountRepository repository;

	@Autowired
	public RepositoryUserDetailsService(UserAccountRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		LOGGER.debug("Loading user by email : '{}'", email);

		UserAccount user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with email : " + email));
		LOGGER.debug("Found user : {}", user);

		MyPrUserDetails principal = MyPrUserDetails.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.id(user.getId())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.role(user.getRole())
				.build();

		LOGGER.debug("Returning principal : {}", principal);
		return principal;
	}
}
