package fr.ensma.lias.trustmodelevaluation.casestudies;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustmodelevaluation.computationalmodels.ArithmeticFunction;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.BayesienNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.ComputationalModel;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.ComputeScore;
import fr.ensma.lias.trustmodelevaluation.computationalmodels.EigenTrustNetworkComputationalModel;
import fr.ensma.lias.trustmodelevaluation.engine.ComputationalModelRecommandationEngine;
import fr.ensma.lias.trustmodelevaluation.engine.ReportEvaluation;
import fr.ensma.lias.trustmodelevaluation.engine.Scenario;
import fr.ensma.lias.trustmodelevaluation.engine.SimulatedTask;
import fr.ensma.lias.trustmodelevaluation.engine.TrustRequirementEngine;
import fr.ensma.lias.trustmodelevaluation.model.AbstractTask;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirement;
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
public class RCISPaper {

	private String computationalModel(Scenario s, ComputationalModel cm, ComputeScore cs) {
		ComputationalModelRecommandationEngine ceEngine = new ComputationalModelRecommandationEngine();
		ReportEvaluation reportEvaluation = ceEngine.eval(s, cm, cs);

		return reportEvaluation.toString();
	}
	
	@Test
	public void allUseCase() {
		// **
		// * Define all ApplicationRequirement.
		// **
		
		// DriverRate, PassengerRate (CancelCarPoolingTrip, ReferPeople)
		
		Comment comment = new Comment(0, 5);
		// DriverRate (Comment > 2)
		PositiveAction driverRatePositiveAction = new PositiveAction(
				new ApplicationRequirementConstraint(comment, new Greater(), new ApplicationRequirementValue(2)));
		// DriverRate (Comment < 2)
		NegativeAction driverRateNegativeAction = new NegativeAction(
				new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(2)));
		ApplicationRequirement driverRate = new ApplicationRequirement("DriverRate", driverRatePositiveAction, driverRateNegativeAction);

		// PassengerRate (Comment > 2)
		PositiveAction passengerRatePositiveAction = new PositiveAction(
				new ApplicationRequirementConstraint(comment, new Greater(), new ApplicationRequirementValue(2)));
		// PassengerRate (Comment < 2)
		NegativeAction passengerRateNegativeAction = new NegativeAction(
				new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(2)));
		ApplicationRequirement passengerRate = new ApplicationRequirement("PassengerRate", passengerRatePositiveAction, passengerRateNegativeAction);

		// **
		// * Define all TrustRequirement based on the previous ApplicationRequirement objects.
		// **
		
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 100);
		
		// Weighted toward current behavior.
		// Requirement(score:[0..100]):(Weighted toward current behavior{null})^1>>[(Positive Task 1{score>+10%})^7;(Negative Task 2{score<-20%})^4]
		
		Task wtcb = new AbstractTask("Weighted toward current behavior");
		wtcb.setDecomposition(Decomposition.SEQ);
		TrustRequirement wtcbRequirement = new TrustRequirement(wtcb, scoreElement);
		
		Task positiveTask1Wtch = new PositiveTask("Positive Task 1");
		positiveTask1Wtch.setIteration(7);
		positiveTask1Wtch.addReason(passengerRatePositiveAction);
		positiveTask1Wtch.addReason(driverRatePositiveAction);
		TrustRequirementConstraint positiveTask1ConstraintWtch = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,20));
		positiveTask1Wtch.setConstraint(positiveTask1ConstraintWtch);
		
		Task negativeTask2Wtch = new NegativeTask("Negative Task 2");
		positiveTask1Wtch.addReason(passengerRateNegativeAction);
		positiveTask1Wtch.addReason(driverRateNegativeAction);
		negativeTask2Wtch.setIteration(4);
		TrustRequirementConstraint negativeTask2ConstraintWtch = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-20));
		negativeTask2Wtch.setConstraint(negativeTask2ConstraintWtch);
		
		wtcb.addTask(positiveTask1Wtch);
		wtcb.addTask(negativeTask2Wtch);
		
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario wtcbScenario = engine.eval(wtcbRequirement);
		
		
		System.out.println("** Weighted toward current behavior");
		Assert.assertEquals(11, wtcbScenario.getLength());

		for(SimulatedTask current : wtcbScenario.getSimulatedTasks()) {
			System.out.println(current);
		}
		
		System.out.println(computationalModel(wtcbScenario, new BayesienNetworkComputationalModel(), new ArithmeticFunction()));
		System.out.println(computationalModel(wtcbScenario, new EigenTrustNetworkComputationalModel(), new ArithmeticFunction()));
		
		System.out.println("");

		// Efficient.
		// Requirement(score:[0..100]):(Efficient{null})^1>>[(Positive Task 1{score>+10%})^4;(Negative Task 2{score<6)^1;(Positive Task 3{score>+20%})^2;(Negative Task 4{score<6)^1]
				
		Task efficient = new AbstractTask("Efficient");
		efficient.setDecomposition(Decomposition.SEQ);
		TrustRequirement efficientRequirement = new TrustRequirement(efficient, scoreElement);

		
		Task positiveTask1Efficient = new PositiveTask("Positive Task 1");
		positiveTask1Efficient.setIteration(4);
		TrustRequirementConstraint positiveTask1ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,20));
		positiveTask1Efficient.setConstraint(positiveTask1ConstraintEfficient);
		positiveTask1Efficient.addReason(passengerRatePositiveAction);
		positiveTask1Efficient.addReason(driverRatePositiveAction);
		
		Task negativeTask2Efficient = new NegativeTask("Negative Task 2");
		TrustRequirementConstraint negativeTask2ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-20,-25));
		negativeTask2Efficient.setConstraint(negativeTask2ConstraintEfficient);
		negativeTask2Efficient.addReason(passengerRateNegativeAction);
		negativeTask2Efficient.addReason(driverRateNegativeAction);

		Task positiveTask3Efficient = new PositiveTask("Positive Task 3");
		positiveTask3Efficient.setIteration(2);
		TrustRequirementConstraint positiveTask3ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,20));
		positiveTask3Efficient.setConstraint(positiveTask3ConstraintEfficient);
		positiveTask3Efficient.addReason(passengerRatePositiveAction);
		positiveTask3Efficient.addReason(driverRatePositiveAction);

		Task negativeTask4Efficient = new NegativeTask("Negative Task 4");
		negativeTask4Efficient.setIteration(1);
		TrustRequirementConstraint negativeTask4ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-20,-25));
		negativeTask4Efficient.setConstraint(negativeTask4ConstraintEfficient);
		negativeTask4Efficient.addReason(passengerRateNegativeAction);
		negativeTask4Efficient.addReason(driverRateNegativeAction);
		
		efficient.addTask(positiveTask1Efficient);
		efficient.addTask(negativeTask2Efficient);
		efficient.addTask(positiveTask3Efficient);
		efficient.addTask(negativeTask4Efficient);
		
		engine = new TrustRequirementEngine();
		Scenario efficientScenario = engine.eval(efficientRequirement);
			
		System.out.println("** Efficient");
		Assert.assertEquals(8, efficientScenario.getLength());
		
		for(SimulatedTask current : efficientScenario.getSimulatedTasks()) {
			System.out.println(current);
		}
		
		System.out.println(computationalModel(efficientScenario, new BayesienNetworkComputationalModel(), new ArithmeticFunction()));
		System.out.println(computationalModel(efficientScenario, new EigenTrustNetworkComputationalModel(), new ArithmeticFunction()));

		System.out.println("");
		
		// Smooth
		// Requirement(score:[0..100]):(Smooth{null})^1>>[(Positive Task 1{score>+10%})^5;(Negative Task 2{score<-5%)^1;(Positive Task 3{score>+10%})^5;(Negative Task{score<-5%)^6]
		
		Task smooth = new AbstractTask("Smooth");
		smooth.setDecomposition(Decomposition.SEQ);
		TrustRequirement smoothRequirement = new TrustRequirement(smooth, scoreElement);
		
		Task positiveTask1Smooth = new PositiveTask("Positive Task 1");
		positiveTask1Smooth.setIteration(5);
		TrustRequirementConstraint positiveTask1ConstraintSmooth = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,20));
		positiveTask1Smooth.setConstraint(positiveTask1ConstraintSmooth);
		positiveTask1Smooth.addReason(passengerRatePositiveAction);
		positiveTask1Smooth.addReason(driverRatePositiveAction);
		
		Task negativeTask2Smooth = new NegativeTask("Negative Task 2");
		negativeTask2Smooth.setIteration(1);
		TrustRequirementConstraint negativeTask2ConstraintSmooth = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-5,-10));
		negativeTask2Smooth.setConstraint(negativeTask2ConstraintSmooth);
		negativeTask2Smooth.addReason(passengerRatePositiveAction);
		negativeTask2Smooth.addReason(driverRatePositiveAction);
		
		Task positiveTask3Smooth = new PositiveTask("Positive Task 3");
		positiveTask3Smooth.setIteration(5);
		TrustRequirementConstraint positiveTask3ConstraintSmooth = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(5,10));
		positiveTask3Smooth.setConstraint(positiveTask3ConstraintSmooth);
		positiveTask3Smooth.addReason(passengerRatePositiveAction);
		positiveTask3Smooth.addReason(driverRatePositiveAction);
		
		Task negativeTask4Smooth = new NegativeTask("Negative Task 4");
		negativeTask4Smooth.setIteration(6);
		TrustRequirementConstraint negativeTask4ConstraintSmooth = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-10,-15));
		negativeTask4Smooth.setConstraint(negativeTask4ConstraintSmooth);
		negativeTask4Smooth.addReason(passengerRatePositiveAction);
		negativeTask4Smooth.addReason(driverRatePositiveAction);
		
		smooth.addTask(positiveTask1Smooth);
		smooth.addTask(negativeTask2Smooth);
		smooth.addTask(positiveTask3Smooth);
		smooth.addTask(negativeTask4Smooth);
		
		engine = new TrustRequirementEngine();
		Scenario smoothScenario = engine.eval(smoothRequirement);
		
		System.out.println("** Smooth");
		Assert.assertEquals(17, smoothScenario.getLength());
		for(SimulatedTask current : smoothScenario.getSimulatedTasks()) {
			System.out.println(current);
		}
		
		System.out.println(computationalModel(smoothScenario, new BayesienNetworkComputationalModel(), new ArithmeticFunction()));
		System.out.println(computationalModel(smoothScenario, new EigenTrustNetworkComputationalModel(), new ArithmeticFunction()));
	}	
}
