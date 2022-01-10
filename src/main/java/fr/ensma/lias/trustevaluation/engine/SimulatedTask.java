package fr.ensma.lias.trustevaluation.engine;

import java.util.List;

import fr.ensma.lias.trustevaluation.model.Reason;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraint;

/**
 * @author Mickael BARON
 */
public class SimulatedTask {

	private List<Reason> reasons;

	private TrustRequirementConstraint constraint;

	private String name;

	private boolean isPositive = false;

	public SimulatedTask(String pName, List<Reason> pReasons, TrustRequirementConstraint pConstraint, boolean pIsPositive) {
		this.reasons= pReasons;
		this.constraint = pConstraint;
		this.name = pName;
		this.isPositive = pIsPositive;
	}

	public List<Reason> getReasons() {
		return this.reasons;
	}

	public TrustRequirementConstraint getConstraint() {
		return this.constraint;
	}

	public String getName() {
		return this.name;
	}

	public boolean isPositive() {
		return this.isPositive;
	}
	
	public String toString() {
		return this.name + " " + this.constraint;
	}
}
