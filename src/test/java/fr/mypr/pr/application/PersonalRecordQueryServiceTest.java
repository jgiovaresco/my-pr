package fr.mypr.pr.application;

import com.ninja_squad.dbsetup.*;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import fr.mypr.pr.application.data.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QueryServiceTestConfiguration.class)
@ActiveProfiles("unitTest")
public class PersonalRecordQueryServiceTest
{
	private static final String ATHLETE_ID = "athlete_1";
	private static final String PR_ID = "1";

	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PersonalRecordQueryService service;

	@Before
	public void prepare() throws Exception
	{
		Operation operation =
				sequenceOf(
						insertInto("personal_records")
								.columns("ID", "ATHLETE_ID", "ATHLETE_NAME", "EXERCISE_ID", "EXERCISE_NAME", "EXERCISE_UNIT", "PR_DATE", "PR_VALUE")
								.values("1", "athlete_1", "John Doe", "ex1", "Deadlift", "kg", "2014-12-12", 130.0f)
								.values("2", "athlete_2", "John Smith", "ex1", "Deadlift", "kg", "2014-6-10", 120.0f)
								.values("3", "athlete_1", "John Doe", "ex2", "Snatch", "kg", "2015-6-12", 57.0f)
								.values("4", "athlete_1", "John Doe", "ex3", "Back Squat", "kg", "2015-6-25", 105.0f)
								.values("5", "athlete_2", "John Smith", "ex3", "Back Squat", "kg", "2015-7-25", 115.0f)
								.build()
				);

		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
		dbSetupTracker.launchIfNecessary(dbSetup);
		dbSetupTracker.skipNextLaunch();
	}

	@Test
	public void personalRecordForAthlete_should_return_athlete_s_personal_records()
	{
		Collection<AthleteExercisePersonalRecordData> result = service.allPersonalRecordsOfAthlete(ATHLETE_ID);

		assertThat(result)
				.isNotNull()
				.extracting("athleteIdentity", "athleteName", "exerciseId", "exerciseName", "exerciseUnit", "id", "date", "value")
				.containsOnly(
						tuple(ATHLETE_ID, "John Doe", "ex1", "Deadlift", "kg", "1", LocalDate.of(2014, 12, 12), 130.0f),
						tuple(ATHLETE_ID, "John Doe", "ex2", "Snatch", "kg", "3", LocalDate.of(2015, 6, 12), 57.0f),
						tuple(ATHLETE_ID, "John Doe", "ex3", "Back Squat", "kg", "4", LocalDate.of(2015, 6, 25), 105.0f)
				);
	}

	@Test
	public void personalRecordDataOfId_should_return_personal_record()
	{
		AthleteExercisePersonalRecordData result = service.personalRecordDataOfId(ATHLETE_ID, PR_ID);

		AthleteExercisePersonalRecordDataAssert.assertThat(result)
				.isNotNull()
				.hasId(PR_ID)
				.hasDate(LocalDate.of(2014, 12, 12))
				.hasValue(130.0f)
				.hasExerciseId("ex1")
				.hasExerciseName("Deadlift")
				.hasExerciseUnit("kg")
				.hasAthleteId(ATHLETE_ID)
				.hasAthleteName("John Doe");
	}
}