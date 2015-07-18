package fr.mypr.identityaccess.command;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class RegisterUserCommand
{
	private String email;
	private String password;

	private String firstName;
	private String lastName;
}
