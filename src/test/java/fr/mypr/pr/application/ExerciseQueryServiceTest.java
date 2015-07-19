package fr.mypr.pr.application;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import fr.mypr.pr.application.data.ExerciseData;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.Collection;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QueryServiceTestConfiguration.class)
@ActiveProfiles("unitTest")
public class ExerciseQueryServiceTest
{
	@Autowired
	private DataSource dataSource;

	@Autowired
	private ExerciseQueryService service;

	@Before
	public void prepare() throws Exception
	{
		Operation operation =
				sequenceOf(
						insertInto("exercises")
								.columns("ID", "NAME", "UNIT")
								.values("1", "Deadlift", "kg")
								.values("2", "Snatch", "kg")
								.values("3", "Back Squat", "kg")
								.build()
				);

		DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
		dbSetup.launch();
	}

	@Test
	public void testAllExercises() throws Exception
	{
		Collection<ExerciseData> result = service.allExercises();

		assertThat(result)
				.isNotNull()
				.extracting("id", "name", "unit")
				.containsOnly(
						tuple("1", "Deadlift", "kg"),
						tuple("2", "Snatch", "kg"),
						tuple("3", "Back Squat", "kg")
				);
	}
}