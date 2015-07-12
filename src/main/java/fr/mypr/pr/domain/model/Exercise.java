package fr.mypr.pr.domain.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Accessors(fluent = true)
@Slf4j
public class Exercise
{
	private String id;
	private String name;
	private String unit;

}
