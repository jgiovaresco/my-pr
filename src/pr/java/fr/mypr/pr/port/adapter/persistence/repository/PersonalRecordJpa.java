package fr.mypr.pr.port.adapter.persistence.repository;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_records")
public class PersonalRecordJpa
{
	@Id
	private String id;
	@Column
	private String athleteId;
	@Column
	private String athleteName;
	@Column
	private String exerciseId;
	@Column
	private String exerciseName;
	@Column
	private String exerciseUnit;
	@Column(name = "pr_date")
	private LocalDate date;
	@Column(name = "pr_value")
	private Float value;
}
