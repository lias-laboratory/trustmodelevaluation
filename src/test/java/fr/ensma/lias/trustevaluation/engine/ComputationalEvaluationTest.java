package fr.ensma.lias.trustevaluation.engine;

import org.junit.Test;

import fr.ensma.lias.trustevaluation.computationalmodels.BayesienNetworkComputationalNetwork;
import fr.ensma.lias.trustevaluation.engine.ComputationalEvaluationEngine;
import fr.ensma.lias.trustevaluation.engine.Scenario;
import fr.ensma.lias.trustevaluation.engine.UserRequirementsEngine;
import fr.ensma.lias.trustevaluation.model.AbstractTask;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.Equal;
import fr.ensma.lias.trustevaluation.model.NegativeTask;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Requirement;
import fr.ensma.lias.trustevaluation.model.ScoreElement;
import fr.ensma.lias.trustevaluation.model.ScoreValue;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 */
public class ComputationalEvaluationTest {
	
	@Test
	public void evaluateScenarioWithBayesienNetworkTest() {
		// Requirement{null}:(Abstract Task{null})^1>>[(Positive Task{score==8})^3;(Negative Task{score==7})^2;(Negative Task{score==2})^4]

		// Given
		ScoreElement scoreElement = new ScoreElement(-100, 100);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		Requirement current = new Requirement(abstractTask);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TaskScoreConstraint positiveTaskConstraint = new TaskScoreConstraint(scoreElement, new Equal(), new ScoreValue(8));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		TaskScoreConstraint negativeTaskConstraint1 = new TaskScoreConstraint(scoreElement, new Equal(), new ScoreValue(7));
		negativeTask1.setConstraint(negativeTaskConstraint1);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TaskScoreConstraint negativeTaskConstraint2 = new TaskScoreConstraint(scoreElement, new Equal(), new ScoreValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		UserRequirementsEngine userEngine = new UserRequirementsEngine();
		Scenario eval = userEngine.eval(current);
		ComputationalEvaluationEngine ceEngine = new ComputationalEvaluationEngine();
		ceEngine.eval(eval, new BayesienNetworkComputationalNetwork());		
	}
}
