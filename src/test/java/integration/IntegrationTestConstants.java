package integration;

public class IntegrationTestConstants
{
	public enum User
	{
		REGISTERED_USER("registered@user.com", "password");

		private String email;
		private String password;
		private String firstName;

		User(String email, String password)
		{
			this.email = email;
			this.password = password;
			this.firstName = "John";
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
	}
}
