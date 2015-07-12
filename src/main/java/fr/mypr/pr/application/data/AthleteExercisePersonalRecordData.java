package fr.mypr.pr.application.data;


import lombok.*;

import java.time.LocalDate;

@Data
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
	private LocalDate prDate;
	private Float prValue;
}
