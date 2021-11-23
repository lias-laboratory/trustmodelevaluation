package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 * 
 * Constraint defined on the last iterate element.
 */
public class IterativeScoreConstraint extends TaskScoreConstraint {

	public IterativeScoreConstraint(ScoreElement pElement, ComparisonOperator pOperator, ScoreValue pConditionValue) {
		super(pElement, pOperator, pConditionValue);
	}
}
