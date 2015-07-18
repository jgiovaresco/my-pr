package fr.mypr.identityaccess.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FullNameTest
{
	@Test
	public void constructor_should_create_an_object() throws Exception
	{

		FullName fullName = new FullName("John", "Doe");
		FullNameAssert.assertThat(fullName)
				.hasFirstname("John")
				.hasLastname("Doe");
	}

	@Test
	public void asFormattedName_should_return_a_string_containing_first_name_and_last_name() throws Exception
	{
		FullName fullName = new FullName("John", "Doe");
		assertThat(fullName.asFormattedName()).isEqualTo("John Doe");
	}
}