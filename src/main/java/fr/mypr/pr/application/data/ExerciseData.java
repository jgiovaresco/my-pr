package fr.mypr.pr.application.data;

import lombok.*;


@Getter
@EqualsAndHashCode
@Builder
public class ExerciseData
{
	private String id;
	private String name;
	private String unit;
}
