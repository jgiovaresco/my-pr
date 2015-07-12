package integration;

import fr.mypr.identityaccess.domain.model.MyPrUserDetails;
import fr.mypr.ihm.security.service.Role;

public class IntegrationTestConstants
{
	public enum User
	{
		REGISTERED_USER("registered@user.com", "password");

		private String email;
		private String password;
		private String firstName;
		private String lastName;
		private MyPrUserDetails userDetails;

		User(String email, String password)
		{
			this.email = email;
			this.password = password;
			this.firstName = "RegisteredUser";
			this.lastName = "User";

			userDetails = MyPrUserDetails.builder()
					.username(email)
					.password(password)
					.firstName(firstName)
					.role(Role.ROLE_USER)
					.build();
		}

		public String getEmail()
		{
			return email;
		}

		public String getPassword()
		{
			return password;
		}

		public String getFirstName()
		{
			return firstName;
		}

		public String getFullName()
		{
			return firstName + " " + lastName;
		}

		public MyPrUserDetails getUserDetails()
		{
			return userDetails;
		}
	}
}
