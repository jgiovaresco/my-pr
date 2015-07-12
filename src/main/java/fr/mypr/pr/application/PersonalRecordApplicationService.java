package fr.mypr.pr.application;

import fr.mypr.pr.domain.model.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonalRecordApplicationService
{
	private PersonalRecordRepository personalRecordRepository;
	private ExerciseRepository exerciseRepository;
	private AthleteService athleteService;

	public void newExercisePersonalRecordForAthlete(String anAthleteId, String anExerciseId, LocalDate aDate, Float aValue)
	{
		Athlete athlete = athleteService.athleteFrom(anAthleteId);
		Exercise exercise = exerciseRepository.exerciseOfId(anExerciseId);

		PersonalRecord pr = athlete.newPersonalRecord(personalRecordRepository.nextIdentity(), aDate, aValue, exercise);
		personalRecordRepository.save(pr);
	}
}
