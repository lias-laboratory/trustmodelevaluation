package fr.ensma.lias.trustevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.engine.NegativeExecutedTask;
import fr.ensma.lias.trustevaluation.engine.PositiveExecutedTask;
import fr.ensma.lias.trustevaluation.engine.Scenario;
import fr.ensma.lias.trustevaluation.engine.UserRequirementsEngine;
import fr.ensma.lias.trustevaluation.model.AbstractTask;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.Equal;
import fr.ensma.lias.trustevaluation.model.ExactValue;
import fr.ensma.lias.trustevaluation.model.GlobalScoreConstraint;
import fr.ensma.lias.trustevaluation.model.IterativeScoreConstraint;
import fr.ensma.lias.trustevaluation.model.NegativeTask;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Requirement;
import fr.ensma.lias.trustevaluation.model.ScoreConstraint;
import fr.ensma.lias.trustevaluation.model.ScoreElement;
import fr.ensma.lias.trustevaluation.model.ScoreValue;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TaskScoreConstraint;

/**
 * @author Mickael BARON
 * 
 * => decomposition 
 * >> sequence 
 * ^n => iteration of n tasks 
 * {...} => score constraint
 */
public class UserRequirementEngineTest {

	@Test
	public void generateScenarioWithIterativeLeavesTaskTest() {
		// Requirement{score==0}:(Abstract Task)^1>>[(Positive Task{null})^15;(Negative Task{null})^5]

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		Requirement current = new Requirement(abstractTask);
		ScoreConstraint finalCondition = new GlobalScoreConstraint(scoreElement, new Equal(), new ExactValue(0));
		current.setGlobalScoreConstraint(finalCondition);

		Task positiveTask = new PositiveTask("Positive Task");
		positiveTask.setIteration(15);
		Task negativeTask = new NegativeTask("Negative Task");
		negativeTask.setIteration(5);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask);

		// When
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(20, eval.getLength());
	}

	@Test
	public void generateScenarioWithIterativeTaskTest() {
		// Requirement{score==0):(Abstract Task)^3>>[(Positive Task{null})^15;(Negative Task{null})^5]

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);
		abstractTask.setIteration(3);

		Requirement current = new Requirement(abstractTask);
		ScoreConstraint finalCondition = new GlobalScoreConstraint(scoreElement, new Equal(), new ExactValue(0));
		current.setGlobalScoreConstraint(finalCondition);

		Task positiveTask = new PositiveTask("Positive Task");
		positiveTask.setIteration(15);
		Task negativeTask = new NegativeTask("Negative Task");
		negativeTask.setIteration(5);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask);

		// When
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(60, eval.getLength());
	}

	@Test
	public void generateScenarioWithGlobalScoreConstraintTest() {
		// Requirement{score==0}:(Positive Task{null})^1

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
		Task abstractTask = new PositiveTask("Positive Task");
		abstractTask.setDecomposition(Decomposition.LEAF);
		Requirement current = new Requirement(abstractTask);
		ScoreConstraint finalCondition = new GlobalScoreConstraint(scoreElement, new Equal(), new ExactValue(0));
		current.setGlobalScoreConstraint(finalCondition);

		// When
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(1, eval.getLength());
	}

	@Test
	public void generateScenarioWithTaskScoreConstraintTest() {
		// Requirement{null}:(Abstract Task{null})^1>>[(Positive Task{score==8})^1;(Negative Task{score==7})^1;(Negative Task{score==2})^1]

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		Requirement current = new Requirement(abstractTask);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TaskScoreConstraint positiveTaskConstraint = new TaskScoreConstraint(scoreElement, new Equal(), new ScoreValue(8));
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		Task negativeTask2 = new NegativeTask("Negative Task");
		TaskScoreConstraint negativeTaskConstraint2 = new TaskScoreConstraint(scoreElement, new Equal(), new ScoreValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(3, eval.getLength());
		Assert.assertTrue(eval.getExecutedTask(0) instanceof PositiveExecutedTask);
		Assert.assertEquals(8, eval.getExecutedTask(0).getConstraint().getConstraintValue().getValue());
		Assert.assertTrue(eval.getExecutedTask(1) instanceof NegativeExecutedTask);
		Assert.assertTrue(eval.getExecutedTask(1).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(2) instanceof NegativeExecutedTask);
		Assert.assertEquals(2, eval.getExecutedTask(2).getConstraint().getConstraintValue().getValue());
	}
	
	@Test
	public void generateScenarioWithIterativeTaskScoreConstraintTest() {
		// Requirement{null}:(Abstract Task{null})^1>>[(Positive Task{score==8})^3;(Negative Task{score==7})^2;(Negative Task{score==2})^4]

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
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
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(9, eval.getLength());
		Assert.assertEquals(8, eval.getExecutedTask(0).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(8, eval.getExecutedTask(1).getConstraint().getConstraintValue().getValue());
		Assert.assertEquals(8, eval.getExecutedTask(2).getConstraint().getConstraintValue().getValue());
	}
	
	@Test
	public void generateScenarioWithIterativeScoreConstraintTest() {
		// Requirement{null}:(Abstract Task{null})^1>>[(Positive Task{score==8})^3;(Negative Task{score==7})^2;(Negative Task{score==2})^4]

		// Given
		ScoreElement scoreElement = new ScoreElement(0, 150);
		Task abstractTask = new AbstractTask("Abstract Task");
		abstractTask.setDecomposition(Decomposition.SEQ);

		Requirement current = new Requirement(abstractTask);
		
		Task positiveTask = new PositiveTask("Positive Task");
		TaskScoreConstraint positiveTaskConstraint = new IterativeScoreConstraint(scoreElement, new Equal(), new ScoreValue(8));
		positiveTask.setIteration(3);
		positiveTask.setConstraint(positiveTaskConstraint);
		Task negativeTask1 = new NegativeTask("Negative Task");
		negativeTask1.setIteration(2);
		Task negativeTask2 = new NegativeTask("Negative Task");
		negativeTask2.setIteration(4);
		TaskScoreConstraint negativeTaskConstraint2 = new IterativeScoreConstraint(scoreElement, new Equal(), new ScoreValue(2));
		negativeTask2.setConstraint(negativeTaskConstraint2);

		abstractTask.addTask(positiveTask);
		abstractTask.addTask(negativeTask1);
		abstractTask.addTask(negativeTask2);

		// When
		UserRequirementsEngine engine = new UserRequirementsEngine();
		Scenario eval = engine.eval(current);

		// Then
		Assert.assertEquals(9, eval.getLength());
		Assert.assertTrue(eval.getExecutedTask(0).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(1).getConstraint() == null);
		Assert.assertEquals(8, eval.getExecutedTask(2).getConstraint().getConstraintValue().getValue());
		Assert.assertTrue(eval.getExecutedTask(3).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(4).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(5).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(6).getConstraint() == null);
		Assert.assertTrue(eval.getExecutedTask(7).getConstraint() == null);
		Assert.assertEquals(2, eval.getExecutedTask(8).getConstraint().getConstraintValue().getValue());
	}
}
