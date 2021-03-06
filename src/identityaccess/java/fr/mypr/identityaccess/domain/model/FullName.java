package fr.mypr.identityaccess.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class FullName
{
	private String firstName;
	private String lastName;

	public String asFormattedName()
	{
		return firstName() + " " + lastName();
	}
}
