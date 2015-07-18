package fr.mypr.ihm.validation;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;

import static org.hamcrest.Matchers.isA;

public class ValidatorUtilTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void constructor_should_throw_an_exception() throws Exception
	{
		exception.expectCause(isA(UnsupportedOperationException.class));

		Constructor<ValidatorUtil> constructor = ValidatorUtil.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}