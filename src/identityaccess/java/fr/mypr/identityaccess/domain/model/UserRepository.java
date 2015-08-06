package fr.mypr.identityaccess.domain.model;

import java.util.Optional;

public interface UserRepository
{
	Optional<User> userWithCredentials(String anEmail, String anEncryptedPassword);

	Optional<User> userWithEmail(String anEmail);

	void add(User aUser);
}
