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
package fr.ensma.lias.trustmodelevaluation.casestudies;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustmodelevaluation.computationalmodels.ArithmeticFunction;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.BayesienNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.EigenTrustNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.engine.ComputationalModelRecommandationEngine;
import fr.ensma.lias.trustmodelevaluation.engine.ReportEvaluation;
import fr.ensma.lias.trustmodelevaluation.engine.Scenario;
import fr.ensma.lias.trustmodelevaluation.engine.SimulatedTask;
import fr.ensma.lias.trustmodelevaluation.engine.TrustRequirementEngine;
import fr.ensma.lias.trustmodelevaluation.model.AbstractTask;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirementValue;
import fr.ensma.lias.trustmodelevaluation.model.Comment;
import fr.ensma.lias.trustmodelevaluation.model.Decomposition;
import fr.ensma.lias.trustmodelevaluation.model.Greater;
import fr.ensma.lias.trustmodelevaluation.model.Lower;
import fr.ensma.lias.trustmodelevaluation.model.NegativeAction;
import fr.ensma.lias.trustmodelevaluation.model.NegativeTask;
import fr.ensma.lias.trustmodelevaluation.model.PercentageScoreValue;
import fr.ensma.lias.trustmodelevaluation.model.PositiveAction;
import fr.ensma.lias.trustmodelevaluation.model.PositiveTask;
import fr.ensma.lias.trustmodelevaluation.model.Task;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirement;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.TrustRequirementConstraintElement;

/**
 * @author Mickael BARON
 */
public class Sample {

	@Test
	public void sample() {
		// **
		// * Define all Application Requirements.
		// **

		// Rating type based on comment.
		Comment comment = new Comment(0, 5);
		// DriverRate (Comment > 2)
		PositiveAction driverRatePositiveAction = new PositiveAction(
				new ApplicationRequirementConstraint(comment, new Greater(), new ApplicationRequirementValue(2)));
		// DriverRate (Comment < 2)
		NegativeAction driverRateNegativeAction = new NegativeAction(
				new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(2)));

		// **
		// * Define all TrustRequirement based on the previous ApplicationRequirement objects.
		// **

		// Weighted toward current behavior.
		// Requirement(score:[0..100]):(Weighted toward current behavior{null})^1>>[(Positive Task 1{score>+10%})^7;(Negative Task 2{score<-20%})^4]
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 100);

		Task wtcb = new AbstractTask("Weighted toward current behavior");
		wtcb.setDecomposition(Decomposition.SEQ);
		TrustRequirement wtcbRequirement = new TrustRequirement(wtcb, scoreElement);

		Task positiveTask1Wtch = new PositiveTask("Positive Task 1");
		positiveTask1Wtch.setIteration(7);
		positiveTask1Wtch.addReason(driverRatePositiveAction);
		TrustRequirementConstraint positiveTask1ConstraintWtch = new TrustRequirementConstraint(scoreElement,
				new Greater(), new PercentageScoreValue(10, 20));
		positiveTask1Wtch.setConstraint(positiveTask1ConstraintWtch);

		Task negativeTask2Wtch = new NegativeTask("Negative Task 2");
		negativeTask2Wtch.addReason(driverRateNegativeAction);
		negativeTask2Wtch.setIteration(4);
		TrustRequirementConstraint negativeTask2ConstraintWtch = new TrustRequirementConstraint(scoreElement,
				new Lower(), new PercentageScoreValue(-35));
		negativeTask2Wtch.setConstraint(negativeTask2ConstraintWtch);

		wtcb.addTask(positiveTask1Wtch);
		wtcb.addTask(negativeTask2Wtch);

		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario wtcbScenario = engine.eval(wtcbRequirement);

		Assert.assertEquals(11, wtcbScenario.getLength());

		for (SimulatedTask current : wtcbScenario.getSimulatedTasks()) {
			System.out.println(current);
		}

		ComputationalModelRecommandationEngine ceEngine = new ComputationalModelRecommandationEngine();
		ReportEvaluation reportEvaluation = ceEngine.eval(wtcbScenario, new BayesienNetworkComputationalModel(),
				new ArithmeticFunction());
		System.out.println(reportEvaluation.toString());
		reportEvaluation = ceEngine.eval(wtcbScenario, new EigenTrustNetworkComputationalModel(),
				new ArithmeticFunction());
		System.out.println(reportEvaluation.toString());
	}
}
