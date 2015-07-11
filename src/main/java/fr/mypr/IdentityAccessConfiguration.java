package fr.mypr;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;

@Configuration
@ComponentScan(basePackages = {
		"fr.mypr.identityaccess"
})
public class IdentityAccessConfiguration
{

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}

}
