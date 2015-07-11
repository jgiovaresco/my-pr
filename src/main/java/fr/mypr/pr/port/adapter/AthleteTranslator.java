package fr.mypr.pr.port.adapter;

import fr.mypr.identityaccess.application.IdentityApplicationService;
import fr.mypr.identityaccess.domain.model.User;
import fr.mypr.pr.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AthleteTranslator implements AthleteService
{
	private IdentityApplicationService identityApplicationService;

	@Autowired
	public AthleteTranslator(IdentityApplicationService identityApplicationService)
	{
		this.identityApplicationService = identityApplicationService;
	}

	@Override
	public Athlete athleteFrom(String anIdentity)
	{
		Athlete athlete = null;
		User user = identityApplicationService.user(anIdentity);
		if (null != user)
		{
			athlete = Athlete.builder()
					.identity(user.email())
					.name(user.person().name().asFormattedName())
					.build();
		}
		return athlete;
	}
}
