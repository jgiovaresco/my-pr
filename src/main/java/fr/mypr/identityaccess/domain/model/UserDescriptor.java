package fr.mypr.identityaccess.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class UserDescriptor
{
	private String email;
	private String password;

	public static UserDescriptor nullDescriptorInstance()
	{
		return new UserDescriptor();
	}

	public boolean isNullDescriptor()
	{
		return null == this.email();
	}

}
