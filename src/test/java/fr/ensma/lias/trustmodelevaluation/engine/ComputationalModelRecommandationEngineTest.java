/*********************************************************************************
 * This file is part of TME (Trust Model Evaluation) tool.
 * Copyright (C) 2022 LIAS/ISAE-ENSMA and O°Code
 * 
 * TMEDe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with TME.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************************/
package fr.ensma.lias.trustmodelevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustmodelevaluation.computationalmodels.ArithmeticFunction;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.BayesienNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.EigenTrustNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.model.AbstractTask;
import fr.ensma.lias.trustmodelevaluation.model.Decomposition;
import fr.ensma.lias.trustmodelevaluation.model.ExactTrustRequirementValue;
import fr.ensma.lias.trustmodelevaluation.model.GreaterEqual;
import fr.ensma.lias.trustmodelevaluation.model.NegativeTask;
import fr.ensma.lias.trustmodelevaluation.model.PositiveTask;
import fr.ensma.lias.trustmodelevaluation.model.Task;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirement;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraintElement;

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
	public void evaluateScenarioWithEigenTrustNetworkWithArithmeticFunctionTest() {
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
		ReportEvaluation reportEvaluation = ceEngine.eval(eval, new EigenTrustNetworkComputationalModel(), new ArithmeticFunction());
		
		// Then
		Assert.assertTrue(reportEvaluation.getScore() > 50);
	}
}
