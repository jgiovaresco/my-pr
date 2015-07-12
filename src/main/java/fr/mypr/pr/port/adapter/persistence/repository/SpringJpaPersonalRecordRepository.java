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
		PersonalRecordJpa exerciseJpa = PersonalRecordJpa.builder()
				.id(aPersonalRecord.id())
				.athleteId(aPersonalRecord.athlete().identity())
				.athleteName(aPersonalRecord.athlete().name())
				.exerciseId(aPersonalRecord.exercise().id())
				.exerciseName(aPersonalRecord.exercise().name())
				.exerciseUnit(aPersonalRecord.exercise().unit())
				.date(aPersonalRecord.date())
				.value(aPersonalRecord.value())
				.build();

		jpaPersonalRecordRepository.save(exerciseJpa);
	}

}
