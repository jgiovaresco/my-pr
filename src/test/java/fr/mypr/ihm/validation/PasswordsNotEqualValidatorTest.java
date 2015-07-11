package fr.mypr.ihm.validation;

import lombok.*;
import org.junit.*;

import javax.validation.*;
import java.util.Set;

import static fr.mypr.ihm.validation.PasswordsNotEqualValidatorTest.PasswordsNotEqualAssert.assertThat;


public class PasswordsNotEqualValidatorTest
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
	public void passwordsNotEqual_BothPasswordsAreNull_ShouldPassValidation()
	{
		Pojo passesValidation = Pojo.builder().build();

		assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
	}

	@Test
	public void passwordsNotEqual_PasswordIsNull_ShouldReturnValidationErrorsForBothFields()
	{
		Pojo failsValidation = Pojo.builder()
				.confirmPassword(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void passwordsNotEqual_PasswordVerificationIsNull_ShouldReturnValidationErrorsForBothFields()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void passwordsNotEqual_BothPasswordsAreEmpty_ShouldPassValidation()
	{
		Pojo passesValidation = Pojo.builder()
				.password("")
				.confirmPassword("")
				.build();

		assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
	}

	@Test
	public void passwordsNotEqual_PasswordIsEmpty_ShouldReturnValidationErrorsForBothFields()
	{
		Pojo failsValidation = Pojo.builder()
				.password("")
				.confirmPassword(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void passwordsNotEqual_PasswordVerificationIsEmpty_ShouldReturnValidationErrorsForBothFields()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.confirmPassword("")
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void passwordsNotEqual_PasswordMismatch_ShouldReturnValidationErrorsForBothFields()
	{
		Pojo failsValidation = Pojo.builder()
				.password(PASSWORD)
				.confirmPassword(PASSWORD_VERIFICATION)
				.build();

		assertThat(validator.validate(failsValidation))
				.numberOfValidationErrorsIs(2)
				.hasValidationErrorForField("password")
				.hasValidationErrorForField("confirmPassword");
	}

	@Test
	public void passwordsNotEqual_PasswordsMatch_ShouldPassValidation()
	{
		Pojo passesValidation = Pojo.builder()
				.password(PASSWORD)
				.confirmPassword(PASSWORD)
				.build();

		assertThat(validator.validate(passesValidation)).hasNoValidationErrors();
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEqual_InvalidPasswordField_ShouldThrowException()
	{
		InvalidPasswordFieldDTO invalid = new InvalidPasswordFieldDTO();

		validator.validate(invalid);
	}

	@Test(expected = ValidationException.class)
	public void passwordsNotEqual_InvalidPasswordVerificationField_ShouldThrowException()
	{
		InvalidPasswordVerificationFieldDTO invalid = new InvalidPasswordVerificationFieldDTO();

		validator.validate(invalid);
	}

	@Getter
	@Setter
	@Builder
	@PasswordsNotEqual(
			passwordFieldName = "password",
			passwordVerificationFieldName = "confirmPassword"
	)
	static class Pojo
	{

		private String password;
		private String confirmPassword;

	}

	@PasswordsNotEqual(
			passwordFieldName = "password",
			passwordVerificationFieldName = "confirmPassword"
	)
	class InvalidPasswordFieldDTO
	{
		private String confirmPassword;
	}

	@PasswordsNotEqual(
			passwordFieldName = "password",
			passwordVerificationFieldName = "confirmPassword"
	)
	private class InvalidPasswordVerificationFieldDTO
	{
		private String password;
	}

	static class PasswordsNotEqualAssert extends ConstraintViolationAssert<Pojo>
	{

		private static final String VALIDATION_ERROR_MESSAGE = "PasswordsNotEqual";

		public PasswordsNotEqualAssert(Set<ConstraintViolation<Pojo>> actual)
		{
			super(PasswordsNotEqualAssert.class, actual);
		}

		public static PasswordsNotEqualAssert assertThat(Set<ConstraintViolation<Pojo>> actual)
		{
			return new PasswordsNotEqualAssert(actual);
		}

		@Override
		protected String getErrorMessage()
		{
			return VALIDATION_ERROR_MESSAGE;
		}
	}

}
