package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class TrustRequirementConstraintElement extends ConstraintElement {

	private int max = 100;

	private int min = -100;

	public TrustRequirementConstraintElement(int pMin, int pMax) {
		this.max = pMax;
		this.min = pMin;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}
}
