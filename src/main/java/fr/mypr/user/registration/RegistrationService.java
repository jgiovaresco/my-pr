package fr.mypr.user.registration;

import fr.mypr.ihm.controller.RegistrationForm;
import fr.mypr.user.model.UserAccount;

public interface RegistrationService
{
	UserAccount registerNewUserAccount(RegistrationForm userAccountData);
}
