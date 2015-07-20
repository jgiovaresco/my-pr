package fr.mypr.pr.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Builder
@Accessors(fluent = true)
public class PersonalRecord
{
	private String id;
	private Athlete athlete;
	private Exercise exercise;

	@Setter(AccessLevel.PRIVATE)
	private LocalDate date;
	@Setter(AccessLevel.PRIVATE)
	private Float value;

	public void newRecord(LocalDate aDate, Float aValue)
	{
		date(aDate);
		value(aValue);
	}
}
