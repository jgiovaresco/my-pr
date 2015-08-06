package fr.mypr.pr.domain.model;

import org.junit.*;

import java.time.LocalDate;

public class AthleteTest
{
	private static final String IDENTITY = "john@doe.com";
	private static final String NAME = "John Doe";

	private static final String PR_ID = "prId";
	private static final LocalDate PR_DATE = LocalDate.of(2014, 12, 12);
	private static final Float PR_VALUE = 103.5f;

	private Athlete athlete;

	@Before
	public void setUp() throws Exception
	{
		athlete = Athlete.builder()
				.identity(IDENTITY)
				.name(NAME)
				.build();
	}

	@Test
	public void newPersonalRecord_should_create_a_personal_record()
	{
		Exercise exercise = Exercise.builder().id("exId").build();
		PersonalRecord result = athlete.newPersonalRecord(PR_ID, PR_DATE, PR_VALUE, exercise);

		PersonalRecordAssert.assertThat(result)
				.hasId(PR_ID)
				.hasDate(PR_DATE)
				.hasValue(PR_VALUE)
				.hasExercise(exercise)
				.hasAthlete(athlete);
	}
}