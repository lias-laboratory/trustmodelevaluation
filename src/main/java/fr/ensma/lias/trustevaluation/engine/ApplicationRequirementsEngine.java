package fr.ensma.lias.trustevaluation.engine;

import java.util.HashMap;
import java.util.Map;

import fr.ensma.lias.trustevaluation.exceptions.NotYetImplementedException;
import fr.ensma.lias.trustevaluation.model.Action;
import fr.ensma.lias.trustevaluation.model.Application;
import fr.ensma.lias.trustevaluation.model.ApplicationConstraint;
import fr.ensma.lias.trustevaluation.model.IValueInt;
import fr.ensma.lias.trustevaluation.model.NeutralAction;

/**
 * @author Mickael BARON
 */
public class ApplicationRequirementsEngine {

	private Map<String, Application> store;

	public ApplicationRequirementsEngine() {
		this.store = new HashMap<>();
	}

	public void addApplication(Application current) {
		this.store.put(current.getName(), current);
	}

	public Action eval(TraceLog currentLog) {
		// Find current application.
		Application application = store.get(currentLog.getApplication());

		if (application == null) {
			throw new NotYetImplementedException("Application identifier not found.");
		}

		boolean resultPositiveAction = checkAction(application.getPositiveAction().getConstraint(), currentLog.getValue());
		boolean resultNegativeAction = checkAction(application.getNegativeAction().getConstraint(), currentLog.getValue());
		
		if (!resultPositiveAction && !resultNegativeAction) {
			NeutralAction neutralAction = new NeutralAction();
			neutralAction.setApplication(application);
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

	protected boolean checkAction(ApplicationConstraint constraint, IValueInt currentValue) {
		// Check valid value about the Feedback constraint.
		if (!constraint.checkValue(currentValue)) {
			throw new NotYetImplementedException("Value is not valid.");
		}

		return constraint.eval(currentValue);
	}
}
