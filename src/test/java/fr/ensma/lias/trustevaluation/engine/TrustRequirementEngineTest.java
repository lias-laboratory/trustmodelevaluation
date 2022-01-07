package fr.ensma.lias.trustevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.model.AbstractTask;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.Equal;
import fr.ensma.lias.trustevaluation.model.ExactTrustRequirementValue;
import fr.ensma.lias.trustevaluation.model.IterativeTrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.NegativeTask;
import fr.ensma.lias.trustevaluation.model.PercentageScoreValue;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TrustRequirement;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraintElement;

/**
 * @author Mickael BARON
 * 
 * => decomposition 
 * >> sequence 
 * ^n => iteration of n tasks 
 * {...} => score constraint
 */
public class TrustRequirementEngineTest {

	@Test
	public void generateScenarioWithIterativeLeavesTaskTest() {
		// Requirement(score:[0..150]):(Abstract Task{null})^1>>[(Positive Task{null})^15;(Negative Task{null})^5]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);

		Task positiveTask = new PositiveTask("Positive Task");
		positiveTask.setIteration(15);
		Task negativeTask = new NegativeTask("Negative Task");
		negativeTask.setIteration(5);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(20, eval.getLength());
	}

	@Test
	public void generateScenarioWithIterativeTaskTest() {
		// Requirement(score:[0..150]):(Abstract Task{null})^3>>[(Positive Task{null})^15;(Negative Task{null})^5]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		abstractTask.setIteration(3);
		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);

		Task positiveTask = new PositiveTask("Positive Task");
		positiveTask.setIteration(15);
		Task negativeTask = new NegativeTask("Negative Task");
		negativeTask.setIteration(5);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(60, eval.getLength());
	}

	@Test
	public void generateScenarioWithGlobalScoreConstraintTest() {
		// Requirement(score:[0..150]):(Positive Task{null})^1

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		
		Task abstractTask = new PositiveTask("Positive Task");
		abstractTask.setDecomposition(Decomposition.LEAF);
		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(1, eval.getLength());
	}

	@Test
	public void generateScenarioWithTaskScoreConstraintTest() {
		// Requirement(score:[0..150]):(Abstract Task{null})^1>>[(Positive Task{score==8})^1;(Negative Task{score==7})^1;(Negative Task{score==2})^1]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TrustRequirementConstraint positiveTaskConstraint = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(8));
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		Task negativeTask2 = new NegativeTask("Negative Task");
		TrustRequirementConstraint negativeTaskConstraint2 = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(3, eval.getLength());
		Assert.assertTrue(eval.getSimulatedTask(0).isPositive());
		Assert.assertEquals(8, eval.getSimulatedTask(0).getConstraint().getConstraintValue().getValue());
		Assert.assertFalse(eval.getSimulatedTask(1).isPositive());
		Assert.assertTrue(eval.getSimulatedTask(1).getConstraint() == null);
		Assert.assertFalse(eval.getSimulatedTask(2).isPositive());
		Assert.assertEquals(2, eval.getSimulatedTask(2).getConstraint().getConstraintValue().getValue());
	}
	
	@Test
	public void generateScenarioWithIterativeTaskScoreConstraintTest() {
		// Requirement(score:[0..150]):(Abstract Task{null})^1>>[(Positive Task{score==8})^3;(Negative Task{score==7})^2;(Negative Task{score==2})^4]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TrustRequirementConstraint positiveTaskConstraint = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(8));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		TrustRequirementConstraint negativeTaskConstraint1 = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(7));
		negativeTask1.setConstraint(negativeTaskConstraint1);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TrustRequirementConstraint negativeTaskConstraint2 = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(9, eval.getLength());
		Assert.assertEquals(8, eval.getSimulatedTask(0).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(8, eval.getSimulatedTask(1).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(8, eval.getSimulatedTask(2).getConstraint().getConstraintValue().getValue());
	}
	
	@Test
	public void generateScenarioWithIterativeScoreConstraintTest() {
		// Requirement{score:[0..150]}:(Abstract Task{null})^1>>[(Positive Task{score==8})^3;(Negative Task{score==7})^2;(Negative Task{score==2})^4]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TrustRequirementConstraint positiveTaskConstraint = new IterativeTrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(8));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TrustRequirementConstraint negativeTaskConstraint2 = new IterativeTrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(9, eval.getLength());
		Assert.assertTrue(eval.getSimulatedTask(0).getConstraint() == null);
		Assert.assertTrue(eval.getSimulatedTask(1).getConstraint() == null);
		Assert.assertEquals(8, eval.getSimulatedTask(2).getConstraint().getConstraintValue().getValue());
		Assert.assertTrue(eval.getSimulatedTask(3).getConstraint() == null);
		Assert.assertTrue(eval.getSimulatedTask(4).getConstraint() == null);
		Assert.assertTrue(eval.getSimulatedTask(5).getConstraint() == null);
		Assert.assertTrue(eval.getSimulatedTask(6).getConstraint() == null);
		Assert.assertTrue(eval.getSimulatedTask(7).getConstraint() == null);
		Assert.assertEquals(2, eval.getSimulatedTask(8).getConstraint().getConstraintValue().getValue());
	}
	
	@Test
	public void generateScenarioWithTaskScoreConstraintAndPercentageValueTest() {
		// Requirement(score:[0..150]):(Abstract Task{null})^1>>[(Positive Task{score==10})^1;(Positive Task{score==+10%})^1;(Positive Task{score==+20})^1]

		// Given
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 150);
		
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		TrustRequirement current = new TrustRequirement(abstractTask, scoreElement);
		
		Task positiveTask1 = new PositiveTask("Positive Task 1");
		TrustRequirementConstraint positiveTask1Constraint = new TrustRequirementConstraint(scoreElement, new Equal(), new ExactTrustRequirementValue(20));
		positiveTask1.setConstraint(positiveTask1Constraint);
		
		Task positiveTask2 = new PositiveTask("Positive Task 2");
		TrustRequirementConstraint positiveTask2Constraint = new TrustRequirementConstraint(scoreElement, new Equal(), new PercentageScoreValue(10,10));
		positiveTask2.setConstraint(positiveTask2Constraint);
		
		Task positiveTask3 = new PositiveTask("Positive Task 3");
		TrustRequirementConstraint positiveTask3Constraint = new TrustRequirementConstraint(scoreElement, new Equal(), new PercentageScoreValue(20,10));
		positiveTask3.setConstraint(positiveTask3Constraint);

		abstractTask.addTask(positiveTask1);
		abstractTask.addTask(positiveTask2);
		abstractTask.addTask(positiveTask3);

		// When
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(3, eval.getLength());
		Assert.assertEquals(20, eval.getSimulatedTask(0).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(22, eval.getSimulatedTask(1).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(26, eval.getSimulatedTask(2).getConstraint().getConstraintValue().getValue());
	}
}
