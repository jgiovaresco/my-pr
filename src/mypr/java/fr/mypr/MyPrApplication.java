package fr.mypr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
public class MyPrApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(MyPrApplication.class, args);
	}
}
