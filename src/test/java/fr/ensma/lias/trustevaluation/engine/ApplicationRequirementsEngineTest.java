package fr.ensma.lias.trustevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.model.Action;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirement;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.ApplicationRequirementValue;
import fr.ensma.lias.trustevaluation.model.Comment;
import fr.ensma.lias.trustevaluation.model.Greater;
import fr.ensma.lias.trustevaluation.model.GreaterEqual;
import fr.ensma.lias.trustevaluation.model.Lower;
import fr.ensma.lias.trustevaluation.model.NegativeAction;
import fr.ensma.lias.trustevaluation.model.NeutralAction;
import fr.ensma.lias.trustevaluation.model.PositiveAction;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirementsEngineTest {

	@Test
	public void generatePositiveandNegativeActions() {
		// Given
		Comment comment = new Comment(0, 5);

		// Comment >= 3
		PositiveAction positiveAction = new PositiveAction(
				new ApplicationRequirementConstraint(comment, new GreaterEqual(), new ApplicationRequirementValue(3)));
		// Comment < 3
		NegativeAction negativeAction = new NegativeAction(
				new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(3)));
		ApplicationRequirement covoiturage = new ApplicationRequirement("Covoiturage", positiveAction, negativeAction);

		ApplicationRequirementEngine engine = new ApplicationRequirementEngine();
		engine.addApplication(covoiturage);

		// When
		Action eval = engine.eval(new TraceLog("Covoiturage", 3));
		// Then
		Assert.assertEquals(positiveAction, eval);
		
		// When
		eval = engine.eval(new TraceLog("Covoiturage", 2));
		// Then
		Assert.assertEquals(negativeAction, eval);
	}
	
	@Test
	public void generateNeutralAction() {
		// Given
		Comment comment = new Comment(0, 5);

		// Comment >= 3
		PositiveAction positiveAction = new PositiveAction(
				new ApplicationRequirementConstraint(comment, new Greater(), new ApplicationRequirementValue(3)));
		// Comment < 3
		NegativeAction negativeAction = new NegativeAction(
				new ApplicationRequirementConstraint(comment, new Lower(), new ApplicationRequirementValue(2)));
		ApplicationRequirement covoiturage = new ApplicationRequirement("Covoiturage", positiveAction, negativeAction);

		ApplicationRequirementEngine engine = new ApplicationRequirementEngine();
		engine.addApplication(covoiturage);

		// When
		Action eval = engine.eval(new TraceLog("Covoiturage", 3));
		// Then
		Assert.assertTrue(eval instanceof NeutralAction);
	}
}
