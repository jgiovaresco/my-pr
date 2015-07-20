package fr.mypr.pr.domain.model;

import org.junit.Test;

import java.time.LocalDate;

public class PersonalRecordTest
{
	private static final String PR_ID = "pr_id";
	private static final String ATHLETE_ID = "athlete_id";
	private static final LocalDate PR_DATE = LocalDate.of(2015, 11, 29);
	private static final Float PR_VALUE = 150.0f;
	private static final String EXERCISE_ID = "exercise_id";

	@Test
	public void newRecord_should_update_date_and_value() throws Exception
	{
		PersonalRecord pr = PersonalRecord.builder()
				.athlete(Athlete.builder().identity(ATHLETE_ID).build())
				.exercise(Exercise.builder().id(EXERCISE_ID).build())
				.id(PR_ID)
				.date(LocalDate.now())
				.value(0f)
				.build();

		pr.newRecord(PR_DATE, PR_VALUE);

		PersonalRecordAssert.assertThat(pr)
				.hasId(PR_ID)
				.hasDate(PR_DATE)
				.hasValue(PR_VALUE);
	}
}