package fr.mypr.pr.application;

import liquibase.integration.spring.SpringLiquibase;
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
				.build();
	}
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource)
	{
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:liquibase/db.changelog.xml");
		return springLiquibase;
	}
}