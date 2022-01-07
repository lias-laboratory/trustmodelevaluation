package fr.ensma.lias.trustevaluation.casestudies;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.engine.Scenario;
import fr.ensma.lias.trustevaluation.engine.TrustRequirementEngine;
import fr.ensma.lias.trustevaluation.model.AbstractTask;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirement;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirementValue;
import fr.ensma.lias.trustevaluation.model.Comment;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.ExactTrustRequirementValue;
import fr.ensma.lias.trustevaluation.model.Greater;
import fr.ensma.lias.trustevaluation.model.Lower;
import fr.ensma.lias.trustevaluation.model.NegativeAction;
import fr.ensma.lias.trustevaluation.model.PercentageScoreValue;
import fr.ensma.lias.trustevaluation.model.PositiveAction;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TrustRequirement;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraintElement;

public class RCISPaper {

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
		
		TrustRequirementConstraintElement scoreElement = new TrustRequirementConstraintElement(0, 300);
		
		// Weighted toward current behavior
		// Requirement(score:[0..300]):(Weighted toward current behavior{null})^1>>[(Positive Task{score>+10%})^7;(Negative Task{score<-20%})^4]
		
		Task wtch = new AbstractTask("Weighted toward current behavior");
		wtch.setDecomposition(Decomposition.SEQ);
		TrustRequirement wtchRequirement = new TrustRequirement(wtch, scoreElement);
		
		Task positiveTaskWtch = new PositiveTask("Positive Task");
		positiveTaskWtch.setIteration(7);
		TrustRequirementConstraint positiveTaskConstraintWtch = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(15,10));
		positiveTaskWtch.setConstraint(positiveTaskConstraintWtch);
		
		Task negativeTaskWtch = new PositiveTask("Negative Task");
		negativeTaskWtch.setIteration(4);
		TrustRequirementConstraint negativeTaskConstraintWtch = new TrustRequirementConstraint(scoreElement, new Lower(), new PercentageScoreValue(-30));
		negativeTaskWtch.setConstraint(negativeTaskConstraintWtch);
		
		wtch.addTask(positiveTaskWtch);
		wtch.addTask(negativeTaskWtch);
		
		TrustRequirementEngine engine = new TrustRequirementEngine();
		Scenario wtchScenario = engine.eval(wtchRequirement);
		
		Assert.assertEquals(11, wtchScenario.getLength());

		// Efficient
		// Requirement(score:[0..300]):(Efficient{null})^1>>[(Positive Task{score>+10%})^4;(Negative Task{score<6)^1;(Positive Task{score>20%})^2;(Negative Task{score<6)^1]
				
		Task efficient = new AbstractTask("Efficient");
		efficient.setDecomposition(Decomposition.SEQ);
		TrustRequirement efficientRequirement = new TrustRequirement(efficient, scoreElement);

		
		Task positiveTask1Efficient = new PositiveTask("Positive Task 1");
		positiveTask1Efficient.setIteration(4);
		TrustRequirementConstraint positiveTask1ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,10));
		positiveTask1Efficient.setConstraint(positiveTask1ConstraintEfficient);
		
		Task negativeTask1Efficient = new PositiveTask("Negative Task 1");
		TrustRequirementConstraint negativeTask1ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Lower(), new ExactTrustRequirementValue(6));
		negativeTask1Efficient.setConstraint(negativeTask1ConstraintEfficient);

		Task positiveTask2Efficient = new PositiveTask("Positive Task 2");
		positiveTask2Efficient.setIteration(2);
		TrustRequirementConstraint positiveTask2ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Greater(), new PercentageScoreValue(10,10));
		positiveTask2Efficient.setConstraint(positiveTask2ConstraintEfficient);

		Task negativeTask2Efficient = new PositiveTask("Negative Task 2");
		TrustRequirementConstraint negativeTask2ConstraintEfficient = new TrustRequirementConstraint(scoreElement, new Lower(), new ExactTrustRequirementValue(6));
		negativeTask2Efficient.setConstraint(negativeTask2ConstraintEfficient);
		
		efficient.addTask(positiveTask1Efficient);
		efficient.addTask(negativeTask1Efficient);
		efficient.addTask(positiveTask2Efficient);
		efficient.addTask(negativeTask2Efficient);
		
		engine = new TrustRequirementEngine();
		Scenario efficientScenario = engine.eval(efficientRequirement);
		
		Assert.assertEquals(8, efficientScenario.getLength());
		
		// Smooth

		
		// ComputationalModel recommandation.
	}	
}
