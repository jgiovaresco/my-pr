package fr.mypr.pr.application.data;


import lombok.*;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@Builder
public class AthleteExercisePersonalRecordData
{
	private String athleteIdentity;
	private String athleteName;
	// Exercise
	private String exerciseId;
	private String exerciseName;
	private String exerciseUnit;
	// PR
	private String id;
	private LocalDate date;
	private Float value;
}
