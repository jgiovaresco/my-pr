package fr.mypr.identityaccess.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Builder
@Setter(AccessLevel.PROTECTED)
@Accessors(fluent = true)
public class Person
{
	@NotNull
	private FullName name;
}
