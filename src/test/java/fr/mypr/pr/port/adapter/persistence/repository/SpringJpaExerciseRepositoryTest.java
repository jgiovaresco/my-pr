package fr.mypr.pr.port.adapter.persistence.repository;

import fr.mypr.pr.domain.model.Exercise;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpringJpaExerciseRepositoryTest
{
	private static final String EXERCISE_ID = "exerciseId";
	private static final String EXERCISE_NAME = "Deadlift";
	private static final String EXERCISE_UNIT = "kg";


	private SpringJpaExerciseRepository repository;

	@Mock
	private JpaExerciseRepository exerciseRepositoryMock;

	@Before
	public void setUp() throws Exception
	{
		repository = new SpringJpaExerciseRepository(exerciseRepositoryMock);
	}

	@Test
	public void nextIdentity_should_return_unique_identity()
	{
		String id1 = repository.nextIdentity();
		String id2 = repository.nextIdentity();

		assertThat(id1).isNotEqualTo(id2);
	}

	@Test
	public void save_should_persist_exercise() throws Exception
	{

		Exercise exercise = Exercise.builder()
				.id(EXERCISE_ID)
				.name(EXERCISE_NAME)
				.unit(EXERCISE_UNIT)
				.build();
		repository.save(exercise);

		ArgumentCaptor<ExerciseJpa> arg = ArgumentCaptor.forClass(ExerciseJpa.class);
		verify(exerciseRepositoryMock, times(1)).save(arg.capture());

		ExerciseJpaAssert.assertThat(arg.getValue())
				.hasId(EXERCISE_ID)
				.hasName(EXERCISE_NAME)
				.hasUnit(EXERCISE_UNIT)
		;
	}
}
