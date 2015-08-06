package fr.mypr.common.converter;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Component
@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date>
{
	@Override
	public Date convertToDatabaseColumn(LocalDate entityValue)
	{
		return null == entityValue ? null : Date.valueOf(entityValue);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date databaseValue)
	{
		return null == databaseValue ? null : databaseValue.toLocalDate();
	}
}