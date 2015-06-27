package fr.mypr.security.util;

import fr.mypr.security.user.MyPrUserDetails;
import fr.mypr.user.model.UserAccount;
import org.slf4j.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil
{

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

	public static void logInUser(UserAccount user)
	{
		LOGGER.info("Logging in user: {}", user);

		MyPrUserDetails userDetails = MyPrUserDetails.builder()
				.firstName(user.getFirstName())
				.id(user.getId())
				.lastName(user.getLastName())
				.password(user.getPassword())
				.role(user.getRole())
				.username(user.getEmail())
				.build();

		LOGGER.debug("Logging in principal: {}", userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		LOGGER.info("User: {} has been logged in.", userDetails);
	}
}
