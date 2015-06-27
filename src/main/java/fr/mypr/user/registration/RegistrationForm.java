package fr.mypr.user.registration;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm
{

	public static final String FIELD_NAME_EMAIL = "email";
	public static final String FIELD_NAME_FIRST_NAME = "firstName";
	public static final String FIELD_NAME_LAST_NAME = "lastName";
	public static final String FIELD_NAME_PASSWORD = "password";
	public static final String FIELD_NAME_PASSWORD_VERIFICATION = "passwordVerification";

	public static final String MODEL_ATTRIBUTE_USER_FORM = "user";
	public static final String SESSION_ATTRIBUTE_USER_FORM = "user";

	@Email
	@NotEmpty
	@Size(max = 100)
	private String email;

	@NotEmpty
	@Size(max = 100)
	private String firstName;

	@NotEmpty
	@Size(max = 100)
	private String lastName;

	private String password;

	private String passwordVerification;
}