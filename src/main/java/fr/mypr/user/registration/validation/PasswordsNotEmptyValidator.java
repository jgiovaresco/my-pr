package fr.mypr.user.registration.validation;

import javax.validation.*;

public class PasswordsNotEmptyValidator implements ConstraintValidator<PasswordsNotEmpty, Object>
{

	private String passwordFieldName;
	private String passwordVerificationFieldName;

	@Override
	public void initialize(PasswordsNotEmpty constraintAnnotation)
	{
		passwordFieldName = constraintAnnotation.passwordFieldName();
		passwordVerificationFieldName = constraintAnnotation.passwordVerificationFieldName();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		boolean valid;
		context.disableDefaultConstraintViolation();
		try
		{
			valid = passwordFieldsAreValid(value, context);
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Exception occurred during validation", ex);
		}

		return valid;
	}

	private boolean passwordFieldsAreValid(Object value, ConstraintValidatorContext context)
			throws NoSuchFieldException, IllegalAccessException
	{
		boolean passwordWordFieldsAreValid = true;

		String password = (String) ValidatorUtil.getFieldValue(value, passwordFieldName);
		if (isNullOrEmpty(password))
		{
			ValidatorUtil.addValidationError(passwordFieldName, context);
			passwordWordFieldsAreValid = false;
		}

		String passwordVerification = (String) ValidatorUtil.getFieldValue(value, passwordVerificationFieldName);
		if (isNullOrEmpty(passwordVerification))
		{
			ValidatorUtil.addValidationError(passwordVerificationFieldName, context);
			passwordWordFieldsAreValid = false;
		}

		return passwordWordFieldsAreValid;
	}

	private boolean isNullOrEmpty(String field)
	{
		return field == null || field.trim().isEmpty();
	}
}
