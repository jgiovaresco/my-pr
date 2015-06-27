package fr.mypr.user.registration.validation;

import lombok.*;
import org.junit.*;

import javax.validation.*;
import java.util.Set;

import static fr.mypr.user.registration.validation.PasswordsNotEmptyValidatorTest.PasswordNotEmptyAssert.assertThat;


public class PasswordsNotEmptyValidatorTest
{

	private static final String PASSWORD = "password";
	private static final String PASSWORD_VERIFICATION = "passwordVerification";

	private Validator validator;

	@Before
	public void setUp()
	{
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validate_should_pass_validation_when_password_fields_have_same_values()
	{
		Pojo passesValidation = Pojo.builder()
				.password(PASSWORD)
				.passwordVerification(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
	}

	@Test
	public void validate_should_return_validation_error_for_password_field_when_password_field_is_null()
	{
		Pojo failsValidation = Pojo.builder()
				.passwordVerification(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(1)
				.hasValidationErrorForField("password");
	}

	@Test
	public void validate_should_return_validation_error_for_password_field_when_password_field_is_empty()
	{
		Pojo failsValidation = Pojo.builder()
				.password("")
				.passwordVerification(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(1)
				.hasValidationErrorForField("password");
	}


	@Test
	public void validate_should_return_validation_error_for_passwordVerification_field_when_passwordVerification_field_is_null()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(1)
				.hasValidationErrorForField("passwordVerification");
	}

	@Test
	public void validate_should_return_validation_error_for_passwordVerification_field_when_passwordVerification_field_is_empty()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.passwordVerification("")
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(1)
				.hasValidationErrorForField("passwordVerification");
	}

	@Test
	public void validate_should_return_validation_error_for_both_password_fields_when_both_password_fields_are_null()
	{
		Pojo failsValidation = Pojo.builder().build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordVerification");
	}

	@Test
	public void validate_should_return_validation_error_for_both_password_fields_when_both_password_fields_are_empty()
	{
		Pojo failsValidation = Pojo.builder()
				.password("")
				.passwordVerification("")
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("passwordVerification");
	}

	@Test(expected = ValidationException.class)
	public void validate_should_throw_exception_when_invalid_password_field_in_annotation()
	{
		InvalidPasswordFieldPojo invalid = new InvalidPasswordFieldPojo();

		validator.validate(invalid);
	}

	@Test(expected = ValidationException.class)
	public void validate_should_throw_exception_when_invalid_passwordVerification_field_in_annotation()
	{
		InvalidPasswordVerificationFieldPojo invalid = new InvalidPasswordVerificationFieldPojo();

		validator.validate(invalid);
	}

	@Getter
	@Setter
	@Builder
	@PasswordsNotEmpty(
			passwordFieldName = "password",
			passwordVerificationFieldName = "passwordVerification"
	)
	static class Pojo
	{
		private String password;
		private String passwordVerification;
	}

	@PasswordsNotEmpty(
			passwordFieldName = "password",
			passwordVerificationFieldName = "passwordVerification"
	)
	private class InvalidPasswordFieldPojo
	{
		private String invalid;
		private String passwordVerification;
	}

	@PasswordsNotEmpty(
			passwordFieldName = "password",
			passwordVerificationFieldName = "passwordVerification"
	)
	private class InvalidPasswordVerificationFieldPojo
	{
		private String password;
		private String invalid;
	}

	static class PasswordNotEmptyAssert extends ConstraintViolationAssert<Pojo>
	{

		private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEmpty";

		public PasswordNotEmptyAssert(Set<ConstraintViolation<Pojo>> actual)
		{
			super(PasswordNotEmptyAssert.class, actual);
		}

		public static PasswordNotEmptyAssert assertThat(Set<ConstraintViolation<Pojo>> actual)
		{
			return new PasswordNotEmptyAssert(actual);
		}

		@Override
		protected String getErrorMessage()
		{
			return VALIDATION_ERROR_MESSAGE;
		}
	}

}
