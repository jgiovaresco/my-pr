package fr.mypr.user.registration;

import fr.mypr.user.model.UserAccount;

/**
 * Created by julien on 6/26/15.
 */
public interface RegistrationService
{
	UserAccount registerNewUserAccount(RegistrationForm userAccountData);
}
