package fr.mypr.pr.port.adapter.persistence.repository;

import fr.mypr.pr.domain.model.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpringJpaPersonalRecordRepositoryTest
{
	private static final String PR_ID = "prId";
	private static final LocalDate PR_DATE = LocalDate.of(2015, 6, 25);
	private static final Float PR_VALUE = 150.0f;
	private static final String EXERCISE_ID = "exerciseId";
	private static final String EXERCISE_NAME = "Deadlift";
	private static final String EXERCISE_UNIT = "kg";
	private static final String ATHLETE_ID = "john@doe.com";
	private static final String ATHLETE_NAME = "John Doe";

	private SpringJpaPersonalRecordRepository repository;

	@Mock
	private JpaPersonalRecordRepository personalRecordRepository;

	@Before
	public void setUp() throws Exception
	{
		repository = new SpringJpaPersonalRecordRepository(personalRecordRepository);
	}

	@Test
	public void nextIdentity_should_return_unique_identity()
	{
		String id1 = repository.nextIdentity();
		String id2 = repository.nextIdentity();

		assertThat(id1).isNotEqualTo(id2);
	}

	@Test
	public void save_should_persist_personal_record() throws Exception
	{
		PersonalRecord pr = PersonalRecord.builder()
				.id(PR_ID)
				.date(PR_DATE)
				.value(PR_VALUE)
				.exercise(Exercise.builder()
						          .id(EXERCISE_ID)
						          .name(EXERCISE_NAME)
						          .unit(EXERCISE_UNIT)
						          .build())
				.athlete(Athlete.builder()
						         .identity(ATHLETE_ID)
						         .name(ATHLETE_NAME)
						         .build())
				.build();

		repository.save(pr);

		ArgumentCaptor<PersonalRecordJpa> arg = ArgumentCaptor.forClass(PersonalRecordJpa.class);
		verify(personalRecordRepository, times(1)).save(arg.capture());

		PersonalRecordJpaAssert.assertThat(arg.getValue())
				.hasId(PR_ID)
				.hasDate(PR_DATE)
				.hasValue(PR_VALUE)
				.hasExerciseId(EXERCISE_ID)
				.hasExerciseName(EXERCISE_NAME)
				.hasExerciseUnit(EXERCISE_UNIT)
				.hasAthleteId(ATHLETE_ID)
				.hasAthleteName(ATHLETE_NAME);
	}

	@Test
	public void personalRecordOfId_should_return_null_when_not_exist()
	{
		when(personalRecordRepository.findOne(PR_ID)).thenReturn(null);

		PersonalRecord pr = repository.personalRecordOfId(PR_ID);

		assertThat(pr).isNull();
	}

	@Test
	public void personalRecordOfId_should_return_PersonalRecord_when_exist()
	{
		when(personalRecordRepository.findOne(PR_ID)).thenReturn(
				PersonalRecordJpa.builder()
						.id(PR_ID)
						.value(PR_VALUE)
						.date(PR_DATE)
						.athleteId(ATHLETE_ID)
						.athleteName(ATHLETE_NAME)
						.exerciseId(EXERCISE_ID)
						.exerciseName(EXERCISE_NAME)
						.exerciseUnit(EXERCISE_UNIT)
						.build()
		);

		PersonalRecord pr = repository.personalRecordOfId(PR_ID);

		PersonalRecordAssert.assertThat(pr)
				.hasId(PR_ID)
				.hasDate(PR_DATE)
				.hasValue(PR_VALUE)
				.hasAthlete(Athlete.builder().identity(ATHLETE_ID).name(ATHLETE_NAME).build())
				.hasExercise(Exercise.builder().id(EXERCISE_ID).name(EXERCISE_NAME).unit(EXERCISE_UNIT).build())
		;
	}
}