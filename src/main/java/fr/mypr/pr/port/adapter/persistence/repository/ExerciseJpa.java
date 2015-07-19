package fr.mypr.pr.port.adapter.persistence.repository;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercises")
public class ExerciseJpa
{
	@Id
	private String id;
	@Column
	private String name;
	@Column
	private String unit;
}
