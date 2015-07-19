package fr.mypr.pr.application;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.*;

import javax.sql.DataSource;

@Configuration
@Profile("unitTest")
public class QueryServiceTestConfiguration
{
	@Bean
	public PersonalRecordQueryService personalRecordQueryService(DataSource dataSource)
	{
		return new PersonalRecordQueryService(dataSource);
	}

	@Bean
	public ExerciseQueryService exerciseQueryService(DataSource dataSource)
	{
		return new ExerciseQueryService(dataSource);
	}

	@Bean
	public DataSource getDataSource() throws Exception
	{
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/schema-readModel.sql")
				.build();
	}
}