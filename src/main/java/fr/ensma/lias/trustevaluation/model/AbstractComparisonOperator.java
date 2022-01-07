package fr.ensma.lias.trustevaluation.model;

public abstract class AbstractComparisonOperator implements ComparisonOperator {

	public Object clone() {
		ComparisonOperator newObject = null;
		try {
			newObject = (ComparisonOperator)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return newObject;
	}
}
