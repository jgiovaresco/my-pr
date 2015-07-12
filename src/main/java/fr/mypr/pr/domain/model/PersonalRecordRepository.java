package fr.mypr.pr.domain.model;


public interface PersonalRecordRepository
{
	String nextIdentity();

	void save(PersonalRecord aPersonalRecord);
}
