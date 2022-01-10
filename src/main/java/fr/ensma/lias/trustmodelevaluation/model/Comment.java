package fr.ensma.lias.trustmodelevaluation.model;

/**
 * @author Mickael BARON
 */
public class Comment extends ApplicationRequirementConstraintElement implements IValueInt {

	private int max;

	private int min;

	public Comment(int pMin, int pMax) {
		this.min = pMin;
		this.max = pMax;
	}

	@Override
	public int getValue() {
		return 0;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	@Override
	public boolean checkValue(IValueInt current) {
		return current.getValue() >= this.min && current.getValue() <= this.max;
	}

	public String toString() {
		return "Comment(min=" + this.min + ",max=" + this.max + ")";
	}
}
