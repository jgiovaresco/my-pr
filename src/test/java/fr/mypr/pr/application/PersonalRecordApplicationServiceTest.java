package fr.mypr.pr.application;

import fr.mypr.pr.domain.model.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonalRecordApplicationServiceTest
{
	private static final String ATHLETE_ID = "athlete_id";
	private static final String PR_ID = "pr_id";
	private static final LocalDate PR_DATE = LocalDate.of(2015, 11, 29);
	private static final Float PR_VALUE = 150.0f;
	private static final String EXERCISE_ID = "exercise_id";

	private PersonalRecordApplicationService service;

	@Mock
	private PersonalRecordRepository personalRecordRepositoryMock;
	@Mock
	private ExerciseRepository exerciseRepositoryMock;
	@Mock
	private AthleteService athleteService;

	@Before
	public void setUp() throws Exception
	{
		service = new PersonalRecordApplicationService(personalRecordRepositoryMock, exerciseRepositoryMock, athleteService);
	}

	@Test
	public void newExercisePersonalRecordForAthlete_should_save_a_pr()
	{
		Athlete athlete = Athlete.builder().identity(ATHLETE_ID).build();
		when(athleteService.athleteFrom(ATHLETE_ID)).thenReturn(athlete);
		Exercise exercise = Exercise.builder().id(EXERCISE_ID).build();
		when(exerciseRepositoryMock.exerciseOfId(EXERCISE_ID)).thenReturn(exercise);
		when(personalRecordRepositoryMock.nextIdentity()).thenReturn(PR_ID);

		service.newExercisePersonalRecordForAthlete(ATHLETE_ID, EXERCISE_ID, PR_DATE, PR_VALUE);

		ArgumentCaptor<PersonalRecord> arg = ArgumentCaptor.forClass(PersonalRecord.class);
		verify(personalRecordRepositoryMock, times(1)).nextIdentity();
		verify(personalRecordRepositoryMock, times(1)).save(arg.capture());
		verifyNoMoreInteractions(personalRecordRepositoryMock);

		PersonalRecordAssert.assertThat(arg.getValue())
				.hasId(PR_ID)
				.hasDate(PR_DATE)
				.hasValue(PR_VALUE)
				.hasAthlete(athlete)
				.hasExercise(exercise);
	}
}