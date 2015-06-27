package fr.mypr;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {
		"fr.mypr.controller",
		"fr.mypr.security.controller",
		"fr.mypr.user.registration.controller"
})
public class WebContext
{

}
