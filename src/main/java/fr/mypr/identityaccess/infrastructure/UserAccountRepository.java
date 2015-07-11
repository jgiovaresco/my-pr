package fr.mypr.identityaccess.infrastructure;

import fr.mypr.identityaccess.domain.persistence.UserAccount;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>
{
	Optional<UserAccount> findByEmail(String email);

	@Query("select u from fr.mypr.identityaccess.domain.persistence.UserAccount u where u.email = ?1 and u.password = ?2")
	Optional<UserAccount> findByCredentials(String anEmail, String anEncryptedPassword);
}
