package fr.mypr.identityaccess.application;

import fr.mypr.identityaccess.command.RegisterUserCommand;
import fr.mypr.identityaccess.domain.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class IdentityApplicationService
{
	private AuthenticationService authenticationService;
	private UserRepository userRepository;

	@Autowired
	public IdentityApplicationService(AuthenticationService authenticationService, UserRepository userRepository)
	{
		this.authenticationService = authenticationService;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public UserDescriptor authenticateUser(String email, String password)
	{
		return authenticationService.authenticate(email, password);
	}

	@Transactional(readOnly = true)
	public User user(String email)
	{
		return userRepository.userWithEmail(email).orElse(null);
	}

	@Transactional
	public User registerUser(RegisterUserCommand aCommand)
	{
		if (emailExists(aCommand.getEmail()))
		{
			log.debug("Email: {} exists. Throwing exception.", aCommand.getEmail());
			throw new DuplicateEmailException(String.format("The email address: %s is already in use.", aCommand.getEmail()));
		}

		User user = User.builder()
				.email(aCommand.getEmail())
				.password(aCommand.getPassword())
				.person(Person.builder()
						        .name(new FullName(aCommand.getFirstName(), aCommand.getLastName()))
						        .build())
				.build();

		userRepository.add(user);

		return user;
	}


	private boolean emailExists(String email)
	{
		log.debug("Checking if email {} is already found from the database.", email);

		Optional<User> user = userRepository.userWithEmail(email);

		if (user.isPresent())
		{
			log.debug("User account: {} found with email: {}.", user, email);
			return true;
		}

		log.debug("No user account found with email: {}.", email);
		return false;
	}
}
