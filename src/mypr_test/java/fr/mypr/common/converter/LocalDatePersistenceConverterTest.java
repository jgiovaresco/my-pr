package fr.mypr.common.converter;

import org.junit.*;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDatePersistenceConverterTest
{
	private LocalDatePersistenceConverter converter;

	@Before
	public void setUp() throws Exception
	{
		converter = new LocalDatePersistenceConverter();
	}

	@Test
	public void convertToDatabaseColumn_should_return_null_when_converting_null() throws Exception
	{
		assertThat(converter.convertToDatabaseColumn(null))
				.isNull();
	}

	@Test
	public void convertToDatabaseColumn_should_return_a_Date_instance() throws Exception
	{
		LocalDate myLocalDate = LocalDate.of(2015, 7, 19);

		assertThat(converter.convertToDatabaseColumn(myLocalDate))
				.isInstanceOf(Date.class)
				.isEqualTo("2015-07-19");
	}

	@Test
	public void convertToEntityAttribute_should_return_null_when_converting_null() throws Exception
	{
		assertThat(converter.convertToEntityAttribute(null))
				.isNull();
	}

	@Test
	public void convertToEntity_should_return_a_LocalDate_instance() throws Exception
	{
		Date myDate = Date.valueOf("2015-07-19");

		assertThat(converter.convertToEntityAttribute(myDate))
				.isInstanceOf(LocalDate.class)
				.isEqualTo("2015-07-19");
	}
}