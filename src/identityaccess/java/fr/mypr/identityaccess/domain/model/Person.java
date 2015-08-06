package fr.mypr.identityaccess.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@Accessors(fluent = true)
public class Person
{
	@NotNull
	private FullName name;
}
