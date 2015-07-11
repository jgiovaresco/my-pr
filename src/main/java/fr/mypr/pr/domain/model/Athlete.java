package fr.mypr.pr.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
public final class Athlete
{
	private String identity;
	private String name;
}
