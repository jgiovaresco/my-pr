package fr.mypr.user.registration;

import fr.mypr.user.model.UserAccount;
import fr.mypr.user.repository.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RepositoryRegistrationService implements RegistrationService
{
	private PasswordEncoder passwordEncoder;

	private UserAccountRepository userAccountRepository;

	@Autowired
	public RepositoryRegistrationService(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository)
	{
		this.passwordEncoder = passwordEncoder;
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public UserAccount registerNewUserAccount(RegistrationForm registrationData)
	{
		log.debug("Registering new user account with information: {}", registrationData);

		if (emailExist(registrationData.getEmail()))
		{
			log.debug("Email: {} exists. Throwing exception.", registrationData.getEmail());
			throw new DuplicateEmailException(String.format("The email address: %s is already in use.", registrationData.getEmail()));
		}

		log.debug("Email: {} does not exist. Continuing registration.", registrationData.getEmail());

		String encodedPassword = encodePassword(registrationData);

		UserAccount.Builder user = UserAccount.builder()
				.email(registrationData.getEmail())
				.firstName(registrationData.getFirstName())
				.lastName(registrationData.getLastName())
				.password(encodedPassword);

		UserAccount registered = user.build();

		log.debug("Persisting new user with information: {}", registered);

		return userAccountRepository.save(registered);
	}

	private boolean emailExist(String email)
	{
		log.debug("Checking if email {} is already found from the database.", email);

		Optional<UserAccount> user = userAccountRepository.findByEmail(email);

		if (user.isPresent())
		{
			log.debug("User account: {} found with email: {}.", user, email);
			return true;
		}

		log.debug("No user account found with email: {}.", email);
		return false;
	}

	private String encodePassword(RegistrationForm registrationData)
	{
		String encodedPassword;

		log.debug("Encoding password.");
		encodedPassword = passwordEncoder.encode(registrationData.getPassword());

		return encodedPassword;
	}
}
