package fr.ensma.lias.trustevaluation.engine;

import org.junit.Assert;
import org.junit.Test;

import fr.ensma.lias.trustevaluation.model.Action;
import fr.ensma.lias.trustevaluation.model.Application;
import fr.ensma.lias.trustevaluation.model.ApplicationConstraint;
import fr.ensma.lias.trustevaluation.model.ApplicationValue;
import fr.ensma.lias.trustevaluation.model.Comment;
import fr.ensma.lias.trustevaluation.model.GreaterEqual;
import fr.ensma.lias.trustevaluation.model.Lower;
import fr.ensma.lias.trustevaluation.model.NegativeAction;
import fr.ensma.lias.trustevaluation.model.PositiveAction;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirementsEngineTest {

	@Test
	public void generateBasicActions() {
		// Given
		Comment comment = new Comment(0, 5);
		
		PositiveAction positiveAction = new PositiveAction(new ApplicationConstraint(comment, new GreaterEqual(), new ApplicationValue(3)));
		NegativeAction negativeAction = new NegativeAction(new ApplicationConstraint(comment, new Lower(), new ApplicationValue(3)));
		Application covoiturage = new Application("Covoiturage", positiveAction, negativeAction);
		
		ApplicationRequirementsEngine engine = new ApplicationRequirementsEngine();
		engine.addApplication(covoiturage);
		
		TraceLog appLog1 = new TraceLog("Covoiturage", 3);
		TraceLog appLog2 = new TraceLog("Covoiturage", 2);
		
		// When 
		Action eval = engine.eval(appLog1);
		
		// Then
		Assert.assertEquals(positiveAction, eval);
		
		eval = engine.eval(appLog2);
		Assert.assertEquals(negativeAction, eval);
	}
}
