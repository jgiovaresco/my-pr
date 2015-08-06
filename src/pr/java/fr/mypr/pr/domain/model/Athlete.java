package fr.mypr.pr.domain.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Builder
@Slf4j
@Accessors(fluent = true)
public final class Athlete
{
	private String identity;
	private String name;

	public PersonalRecord newPersonalRecord(String anId, LocalDate aDate, Float aValue, Exercise anExercise)
	{
		PersonalRecord pr = PersonalRecord.builder()
				.id(anId)
				.date(aDate)
				.value(aValue)
				.exercise(anExercise)
				.athlete(this)
				.build();

		log.debug("PersonalRecord created : {}", pr);

		return pr;
	}
}
