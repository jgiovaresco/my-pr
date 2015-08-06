package fr.mypr.pr.application;

import fr.mypr.pr.application.data.AthleteExercisePersonalRecordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;

@Service
public class PersonalRecordQueryService
{
	private JdbcTemplate database;
	private RowMapper<AthleteExercisePersonalRecordData> rowMapper;

	@Autowired
	public PersonalRecordQueryService(DataSource dataSource)
	{
		this.database = new JdbcTemplate(dataSource);
		this.rowMapper = new AthleteExercisePersonalRecordDataRowMapper();
	}

	public Collection<AthleteExercisePersonalRecordData> allPersonalRecordsOfAthlete(String anAthleteId)
	{
		return database.query(
				"select ID, EXERCISE_ID, EXERCISE_NAME, EXERCISE_UNIT, ATHLETE_ID, ATHLETE_NAME, PR_DATE, PR_VALUE from PERSONAL_RECORDS where ATHLETE_ID = ?",
				new Object[]{anAthleteId},
				this.rowMapper
		);
	}

	public AthleteExercisePersonalRecordData personalRecordDataOfId(String anAthleteId, String aPrId)
	{
		return database.queryForObject(
				"select ID, EXERCISE_ID, EXERCISE_NAME, EXERCISE_UNIT, ATHLETE_ID, ATHLETE_NAME, PR_DATE, PR_VALUE from PERSONAL_RECORDS where ATHLETE_ID = ? and ID = ?",
				new Object[]{anAthleteId, aPrId},
				this.rowMapper
		);
	}

	private class AthleteExercisePersonalRecordDataRowMapper implements RowMapper<AthleteExercisePersonalRecordData>
	{
		@Override
		public AthleteExercisePersonalRecordData mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			return AthleteExercisePersonalRecordData.builder()
					.id(rs.getString("ID"))
					.athleteIdentity(rs.getString("ATHLETE_ID"))
					.athleteName(rs.getString("ATHLETE_NAME"))
					.exerciseId(rs.getString("EXERCISE_ID"))
					.exerciseName(rs.getString("EXERCISE_NAME"))
					.exerciseUnit(rs.getString("EXERCISE_UNIT"))
					.date(rs.getDate("PR_DATE").toLocalDate())
					.value(rs.getFloat("PR_VALUE"))
					.build();
		}
	}
}
