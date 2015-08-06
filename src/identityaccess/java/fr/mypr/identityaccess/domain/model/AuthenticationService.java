package fr.mypr.identityaccess.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService
{
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDescriptor authenticate(String anEmail, String aPassword)
	{
		Validate.notEmpty(anEmail, "Email must be provided.");
		Validate.notEmpty(aPassword, "Password must be provided.");

		UserDescriptor userDescriptor;
		String encryptedPassword = passwordEncoder.encode(aPassword);

		Optional<User> user = userRepository.userWithCredentials(anEmail, encryptedPassword);
		if (user.isPresent())
		{
			log.debug("Found user : {}", user.get());
			userDescriptor = UserDescriptor.builder()
					.email(user.get().email())
					.password(user.get().password())
					.build();
		}
		else
		{
			userDescriptor = UserDescriptor.nullDescriptorInstance();
		}

		return userDescriptor;
	}
}
