package fr.mypr.pr.port.adapter.persistence.repository;

import fr.mypr.pr.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SpringJpaPersonalRecordRepository implements PersonalRecordRepository
{
	private JpaPersonalRecordRepository jpaPersonalRecordRepository;

	@Autowired
	public SpringJpaPersonalRecordRepository(JpaPersonalRecordRepository jpaPersonalRecordRepository)
	{
		this.jpaPersonalRecordRepository = jpaPersonalRecordRepository;
	}

	@Override
	public String nextIdentity()
	{
		return UUID.randomUUID().toString().toUpperCase();
	}

	@Override
	public void save(PersonalRecord aPersonalRecord)
	{
		PersonalRecordJpa exerciseJpa = toPersonalRecordJpa(aPersonalRecord);

		jpaPersonalRecordRepository.save(exerciseJpa);
	}

	@Override
	public PersonalRecord personalRecordOfId(String aPersonalRecordId)
	{
		return toPersonalRecord(jpaPersonalRecordRepository.findOne(aPersonalRecordId));
	}

	private PersonalRecordJpa toPersonalRecordJpa(PersonalRecord aPersonalRecord)
	{
		return PersonalRecordJpa.builder()
				.id(aPersonalRecord.id())
				.athleteId(aPersonalRecord.athlete().identity())
				.athleteName(aPersonalRecord.athlete().name())
				.exerciseId(aPersonalRecord.exercise().id())
				.exerciseName(aPersonalRecord.exercise().name())
				.exerciseUnit(aPersonalRecord.exercise().unit())
				.date(aPersonalRecord.date())
				.value(aPersonalRecord.value())
				.build();
	}

	private PersonalRecord toPersonalRecord(PersonalRecordJpa aPersonalRecordJpa)
	{
		PersonalRecord pr = null;
		if (null != aPersonalRecordJpa)
		{
			pr = PersonalRecord.builder()
					.id(aPersonalRecordJpa.getId())
					.athlete(Athlete.builder()
							         .identity(aPersonalRecordJpa.getAthleteId())
							         .name(aPersonalRecordJpa.getAthleteName())
							         .build())
					.exercise(Exercise.builder()
							          .id(aPersonalRecordJpa.getExerciseId())
							          .name(aPersonalRecordJpa.getExerciseName())
							          .unit(aPersonalRecordJpa.getExerciseUnit())
							          .build())
					.date(aPersonalRecordJpa.getDate())
					.value(aPersonalRecordJpa.getValue())
					.build();
		}
		return pr;
	}
}
