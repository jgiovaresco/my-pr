package fr.mypr.pr.application;

import fr.mypr.pr.application.data.ExerciseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collection;

@Service
public class ExerciseQueryService
{
	private JdbcTemplate database;

	@Autowired
	public ExerciseQueryService(DataSource dataSource)
	{
		this.database = new JdbcTemplate(dataSource);
	}

	public Collection<ExerciseData> allExercises()
	{
		return database.query(
				"select ID, NAME, UNIT from EXERCISES",
				(rs, rowNum) -> {
					return ExerciseData.builder()
							.id(rs.getString("ID"))
							.name(rs.getString("NAME"))
							.unit(rs.getString("UNIT"))
							.build();
				});
	}
}
