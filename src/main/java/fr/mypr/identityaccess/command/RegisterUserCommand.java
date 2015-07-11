package fr.mypr.identityaccess.command;

import lombok.*;

@Data
@Builder
public class RegisterUserCommand
{
	private String email;
	private String password;

	private String firstName;
	private String lastName;
}
