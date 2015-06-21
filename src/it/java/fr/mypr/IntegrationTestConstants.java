package fr.mypr;

public class IntegrationTestConstants
{
	public enum User
	{
		REGISTERED_USER("registered@user.com", "password");

		private String email;
		private String password;

		User(String email, String password)
		{
			this.email = email;
			this.password = password;
		}

		public String getEmail()
		{
			return email;
		}

		public String getPassword()
		{
			return password;
		}
	}
}
