package fr.mypr.ihm.controller.pr.data;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecordEditForm
{
	public static final String FIELD_ID = "id";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_DATE = "date";
	public static final String FIELD_EXERCISE_NAME = "exerciseName";

	public static final String PR_ATTRIBUTE_FORM = "personalRecord";

	@NotEmpty
	private String id;

	@NotNull
	private Float value;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;

	private String exerciseName;
}
