package fr.mypr.rule;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import fr.mypr.IntegrationTestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.rules.MethodRule;
import org.junit.runners.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import static com.ninja_squad.dbsetup.Operations.*;

@Component
@Slf4j
public class InitUserAccountRule implements MethodRule
{
	private DataSource dataSource;

	@Autowired
	public InitUserAccountRule(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Statement apply(Statement base, FrameworkMethod method, Object target)
	{
		return new Statement()
		{
			@Override
			public void evaluate() throws Throwable
			{
				// @formatter:off
				Operation operation =
						sequenceOf(
								deleteAllFrom("USER_ACCOUNT"),
								insertInto("USER_ACCOUNT")
										.columns("ID", "EMAIL",               "PASSWORD", "FIRST_NAME",     "LAST_NAME", "ROLE",      "VERSION")
										.values (1L,   "registered@user.com", "password", "RegisteredUser", "User",      "ROLE_USER", 1L)
										.build());
				// @formatter:on
				DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
				dbSetup.launch();

				base.evaluate();
			}
		};
	}
}
