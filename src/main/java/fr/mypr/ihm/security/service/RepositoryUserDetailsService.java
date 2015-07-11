package fr.mypr.ihm.security.service;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.identityaccess.domain.model.User;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;


@Component
public class RepositoryUserDetailsService implements UserDetailsService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserDetailsService.class);

	private IdentityApplicationService identityApplicationService;

	@Autowired
	public RepositoryUserDetailsService(IdentityApplicationService identityApplicationService)
	{
		this.identityApplicationService = identityApplicationService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		LOGGER.debug("Loading user by email : '{}'", email);

		MyPrUserDetails principal;
		User user = identityApplicationService.user(email);

		if (null == user)
		{
			throw new UsernameNotFoundException("No user found with email : " + email);
		}
		else
		{
			LOGGER.debug("Found user : {}", user);

			principal = MyPrUserDetails.builder()
					.username(user.email())
					.password(user.password())
					.firstName(user.person().name().firstName())
					.lastName(user.person().name().lastName())
					.role(Role.ROLE_USER)
					.build();
		}

		LOGGER.debug("Returning principal : {}", principal);
		return principal;
	}
}
