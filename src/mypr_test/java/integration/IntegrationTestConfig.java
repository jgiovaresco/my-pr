package integration;

import fr.mypr.MyPrApplication;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Profile({"integrationTest"})
@Import({MyPrApplication.class})
@Configuration
public class IntegrationTestConfig
{
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource)
	{
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource);
		springLiquibase.setChangeLog("classpath:liquibase/db.changelog.xml");
		return springLiquibase;
	}
}
