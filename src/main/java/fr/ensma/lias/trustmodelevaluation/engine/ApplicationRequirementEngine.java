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

import java.util.HashMap;
import java.util.Map;

import fr.ensma.lias.trustmodelevaluation.exceptions.NotYetImplementedException;
import fr.ensma.lias.trustmodelevaluation.model.Action;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirement;
import fr.ensma.lias.trustmodelevaluation.model.ApplicationRequirementConstraint;
import fr.ensma.lias.trustmodelevaluation.model.IValueInt;
import fr.ensma.lias.trustmodelevaluation.model.NeutralAction;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirementEngine {

	private Map<String, ApplicationRequirement> store;

	public ApplicationRequirementEngine() {
		this.store = new HashMap<>();
	}

	public void addApplication(ApplicationRequirement current) {
		this.store.put(current.getName(), current);
	}

	public Action eval(TraceLog currentLog) {
		// Find current application.
		ApplicationRequirement application = store.get(currentLog.getApplication());

		if (application == null) {
			throw new NotYetImplementedException("Application identifier not found.");
		}

		boolean resultPositiveAction = checkAction(application.getPositiveAction().getConstraint(),
				currentLog.getValue());
		boolean resultNegativeAction = checkAction(application.getNegativeAction().getConstraint(),
				currentLog.getValue());

		if (!resultPositiveAction && !resultNegativeAction) {
			NeutralAction neutralAction = new NeutralAction();
			neutralAction.setApplicationRequirement(application);
			return neutralAction;
		}

		if (resultPositiveAction && resultNegativeAction) {
			throw new NotYetImplementedException("Ambigous evaluation on action onstraints.");
		}

		if (resultPositiveAction) {
			return application.getPositiveAction();
		} else {
			return application.getNegativeAction();
		}
	}

	protected boolean checkAction(ApplicationRequirementConstraint constraint, IValueInt currentValue) {
		// Check valid value about the Feedback constraint.
		if (!constraint.checkValue(currentValue)) {
			throw new NotYetImplementedException("Value is not valid.");
		}

		return constraint.eval(currentValue);
	}
}
