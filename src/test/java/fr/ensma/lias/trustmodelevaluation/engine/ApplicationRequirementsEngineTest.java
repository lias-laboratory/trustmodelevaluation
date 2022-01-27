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

import fr.ensma.lias.trustmodelevaluation.model.Action;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirement;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirementValue;
import fr.ensma.lias.trustmodelevaluation.model.Comment;
import fr.ensma.lias.trustmodelevaluation.model.Greater;
import fr.ensma.lias.trustmodelevaluation.model.GreaterEqual;
import fr.ensma.lias.trustmodelevaluation.model.Lower;
import fr.ensma.lias.trustmodelevaluation.model.NegativeAction;
import fr.ensma.lias.trustmodelevaluation.model.NeutralAction;
import fr.ensma.lias.trustmodelevaluation.model.PositiveAction;

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
