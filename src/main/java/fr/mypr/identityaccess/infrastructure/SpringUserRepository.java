package fr.mypr.identityaccess.infrastructure;

import fr.mypr.identityaccess.domain.model.*;
import fr.mypr.identityaccess.domain.persistence.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class SpringUserRepository implements UserRepository
{
	private UserAccountRepository userAccountRepository;

	@Autowired
	public SpringUserRepository(UserAccountRepository userAccountRepository)
	{
		this.userAccountRepository = userAccountRepository;
	}

	public Optional<User> userWithCredentials(String anEmail, String anEncryptedPassword)
	{
		Optional<User> user = Optional.empty();

		log.debug("Loading user by credentials : '{}', '{}'", anEmail, anEncryptedPassword);

		Optional<UserAccount> userAccount = userAccountRepository.findByCredentials(anEmail, anEncryptedPassword);
		if (userAccount.isPresent())
		{
			log.debug("Found user : {}", user);
			user = Optional.of(buildUserFromUserAccount(userAccount.get()));
		}
		else
		{
			log.debug("No user '{}' Found", anEmail);
			user = Optional.empty();
		}

		return user;
	}

	@Override
	public Optional<User> userWithEmail(String aUsername)
	{
		Optional<User> user = Optional.empty();

		log.debug("Loading user by username : '{}'", aUsername);

		Optional<UserAccount> userAccount = userAccountRepository.findByEmail(aUsername);
		if (userAccount.isPresent())
		{
			log.debug("Found user : {}", user);
			user = Optional.of(buildUserFromUserAccount(userAccount.get()));
		}
		else
		{
			log.debug("No user '{}' Found", aUsername);
			user = Optional.empty();
		}

		return user;
	}

	@Override
	public void add(User aUser)
	{
		log.debug("Saving user {}", aUser);

		userAccountRepository.save(buildUserAccountFromUser(aUser));
	}

	private User buildUserFromUserAccount(UserAccount userAccount)
	{
		return User.builder()
				.email(userAccount.getEmail())
				.password(userAccount.getPassword())
				.person(Person.builder()
						        .name(new FullName(userAccount.getFirstName(), userAccount.getLastName()))
						        .build())
				.build();
	}

	private UserAccount buildUserAccountFromUser(User user)
	{
		return UserAccount.builder()
				.email(user.email())
				.password(user.password())
				.firstName(user.person().name().firstName())
				.lastName(user.person().name().lastName())
				.build();
	}
}
