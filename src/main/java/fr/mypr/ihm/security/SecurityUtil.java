package fr.mypr.ihm.security;

import fr.mypr.identityaccess.domain.model.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@UtilityClass
public class SecurityUtil
{
	public static void logInUser(User user)
	{
		log.info("Logging in user: {}", user);

		MyPrUserDetails userDetails = MyPrUserDetails.builder()
				.firstName(user.person().name().firstName())
				.lastName(user.person().name().lastName())
				.username(user.email())
				.password(user.password())
				.role(Role.ROLE_USER)
				.build();

		log.debug("Logging in principal: {}", userDetails);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		log.info("User: {} has been logged in.", userDetails);
	}
}
