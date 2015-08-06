package fr.mypr.ihm.validation;

import lombok.*;
import org.junit.*;

import javax.validation.*;
import java.util.Set;

import static fr.mypr.ihm.validation.PasswordsNotEmptyValidatorTest.PasswordNotEmptyAssert.assertThat;


public class PasswordsNotEmptyValidatorTest
{

	private static final String PASSWORD = "password";
	private static final String PASSWORD_VERIFICATION = "confirmPassword";

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
				.confirmPassword(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
	}

	@Test
	public void validate_should_return_validation_error_for_password_field_when_password_field_is_null()
	{
		Pojo failsValidation = Pojo.builder()
				.confirmPassword(PASSWORD_VERIFICATION)
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
				.confirmPassword(PASSWORD_VERIFICATION)
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
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void validate_should_return_validation_error_for_passwordVerification_field_when_passwordVerification_field_is_empty()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.confirmPassword("")
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(1)
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void validate_should_return_validation_error_for_both_password_fields_when_both_password_fields_are_null()
	{
		Pojo failsValidation = Pojo.builder().build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void validate_should_return_validation_error_for_both_password_fields_when_both_password_fields_are_empty()
	{
		Pojo failsValidation = Pojo.builder()
				.password("")
				.confirmPassword("")
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
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
			passwordVerificationFieldName = "confirmPassword"
	)
	static class Pojo
	{
		private String password;
		private String confirmPassword;
	}

	@PasswordsNotEmpty(
			passwordFieldName = "password",
			passwordVerificationFieldName = "confirmPassword"
	)
	private class InvalidPasswordFieldPojo
	{
		private String invalid;
		private String confirmPassword;
	}

	@PasswordsNotEmpty(
			passwordFieldName = "password",
			passwordVerificationFieldName = "confirmPassword"
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
