package fr.mypr.pr.application;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersonalRecordQueryServiceTest.PersonalRecordQueryServiceConfiguration.class)
public class PersonalRecordQueryServiceTest
{
	private static final String ATHLETE_ID = "athlete_1";

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
		dbSetup.launch();
	}

	@Test
	public void personalRecordForAthlete_should_return_athlete_s_personal_records()
	{
		Collection<AthleteExercisePersonalRecordData> result = service.personalRecordForAthlete(ATHLETE_ID);

		assertThat(result)
				.isNotNull()
				.extracting("athleteIdentity", "exerciseId", "id", "prDate", "prValue")
				.containsOnly(
						tuple(ATHLETE_ID, "ex1", "1", LocalDate.of(2014, 12, 12), 130.0f),
						tuple(ATHLETE_ID, "ex2", "3", LocalDate.of(2015, 6, 12), 57.0f),
						tuple(ATHLETE_ID, "ex3", "4", LocalDate.of(2015, 6, 25), 105.0f)
				);
	}

	public static class PersonalRecordQueryServiceConfiguration
	{
		@Bean
		public PersonalRecordQueryService personalRecordQueryService(DataSource dataSource)
		{
			return new PersonalRecordQueryService(dataSource);
		}

		@Bean
		public DataSource getDataSource() throws Exception
		{
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.addScript("schema-pr.sql")
					.build();
		}
	}
}