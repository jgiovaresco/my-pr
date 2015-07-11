package fr.mypr.ihm.security.util;

import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.ihm.security.service.Role;
import org.slf4j.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

	public static void logInUser(User user)
	{
		LOGGER.info("Logging in user: {}", user);

		MyPrUserDetails userDetails = MyPrUserDetails.builder()
				.firstName(user.person().name().firstName())
				.lastName(user.person().name().lastName())
				.username(user.email())
				.password(user.password())
				.role(Role.ROLE_USER)
				.build();

		LOGGER.debug("Logging in principal: {}", userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		LOGGER.info("User: {} has been logged in.", userDetails);
	}
}
