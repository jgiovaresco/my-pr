package fr.mypr.pr.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@Accessors(fluent = true)
public class PersonalRecord
{
	private String id;
	private LocalDate date;
	private Float value;
	private Athlete athlete;
	private Exercise exercise;
}
