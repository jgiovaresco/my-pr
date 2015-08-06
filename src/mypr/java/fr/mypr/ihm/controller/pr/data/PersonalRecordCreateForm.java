package fr.mypr.ihm.controller.pr.data;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Builder
public class PersonalRecordCreateForm
{
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_EXERCISE = "selectedExerciseId";

	public static final String PR_ATTRIBUTE_FORM = "personalRecord";
	public static final String EXERCISES_ATTRIBUTE_FORM = "exercises";

	@NotNull
	private Float value;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	@NotEmpty
	private String selectedExerciseId;
}
