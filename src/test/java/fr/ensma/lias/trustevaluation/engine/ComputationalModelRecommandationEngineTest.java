package fr.ensma.lias.trustevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.computationalmodels.ArithmeticFunction;
import fr.ensma.lias.trustevaluation.computationalmodels.BayesienNetworkComputationalModel;
import fr.ensma.lias.trustevaluation.computationalmodels.MachineLearningFunction;
import fr.ensma.lias.trustevaluation.model.AbstractTask;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.ExactTrustRequirementValue;
import fr.ensma.lias.trustevaluation.model.GreaterEqual;
import fr.ensma.lias.trustevaluation.model.Lower;
import fr.ensma.lias.trustevaluation.model.NegativeTask;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TrustRequirement;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraintElement;

/**
 * @author Mickael BARON
 */
public class ComputationalModelRecommandationEngineTest {
	
	@Test
	public void evaluateScenarioWithBayesienNetworkWithArithmeticFunctionTest() {
		// Requirement{score:[-100..100]}:(Abstract Task{null})^1>>[(Positive Task{score>=8})^3;(Negative Task{score>=7})^2;(Negative Task{score>=-2})^4]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(-100, 100);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TrustRequirementConstraint positiveTaskConstraint = new TrustRequirementConstraint(scoreElement, new GreaterEqual(), new ExactTrustRequirementValue(8));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		TrustRequirementConstraint negativeTaskConstraint1 = new TrustRequirementConstraint(scoreElement, new GreaterEqual(), new ExactTrustRequirementValue(7));
		negativeTask1.setConstraint(negativeTaskConstraint1);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TrustRequirementConstraint negativeTaskConstraint2 = new TrustRequirementConstraint(scoreElement, new GreaterEqual(), new ExactTrustRequirementValue(-2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When	
		TrustRequirementEngine userEngine = new TrustRequirementEngine();
		Scenario eval = userEngine.eval(current);
		ComputationalModelRecommandationEngine ceEngine = new ComputationalModelRecommandationEngine();
		ReportEvaluation reportEvaluation = ceEngine.eval(eval, new BayesienNetworkComputationalModel(), new ArithmeticFunction());
		
		// Then
		Assert.assertTrue(reportEvaluation.getScore() > 50);
	}
	
	@Test
	public void evaluateScenarioWithBayesienNetworkWithMachineLearningFunctionTest() {
		// Requirement{score:[-100..100]}:(Abstract Task{null})^1>>[(Positive Task{score>=50})^3;(Negative Task{score<50})^2;(Negative Task{score<50})^4]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(-100, 100);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TrustRequirementConstraint positiveTaskConstraint = new TrustRequirementConstraint(scoreElement, new GreaterEqual(), new ExactTrustRequirementValue(50));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		TrustRequirementConstraint negativeTaskConstraint1 = new TrustRequirementConstraint(scoreElement, new Lower(), new ExactTrustRequirementValue(50));
		negativeTask1.setConstraint(negativeTaskConstraint1);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TrustRequirementConstraint negativeTaskConstraint2 = new TrustRequirementConstraint(scoreElement, new Lower(), new ExactTrustRequirementValue(50));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		TrustRequirementEngine userEngine = new TrustRequirementEngine();
		Scenario eval = userEngine.eval(current);
		ComputationalModelRecommandationEngine ceEngine = new ComputationalModelRecommandationEngine();
		ReportEvaluation reportEvaluation = ceEngine.eval(eval, new BayesienNetworkComputationalModel(), new MachineLearningFunction());

		// Then
		Assert.assertTrue(reportEvaluation.getScore() > 50);
	}
}
