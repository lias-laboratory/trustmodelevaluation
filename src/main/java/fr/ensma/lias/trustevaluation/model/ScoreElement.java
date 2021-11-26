package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class ScoreElement extends ConstraintElement {

	private int max = 100;

	private int min = -100;

	public ScoreElement(int pMin, int pMax) {
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
