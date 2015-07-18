package fr.mypr.identityaccess.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@EqualsAndHashCode
@Builder
@Accessors(fluent = true)
public class User
{
	private String email;
	private String password;

	private Person person;
}
