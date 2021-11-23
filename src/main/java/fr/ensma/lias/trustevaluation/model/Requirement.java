package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class Requirement {

	private Task describedBy;
	
	private ScoreConstraint globalScoreConstraint;
	
	public Requirement(Task pDescribedBy) {
		this.describedBy = pDescribedBy;
	}

	public ScoreConstraint getGlobalScoreConstraint() {
		return globalScoreConstraint;
	}

	public void setGlobalScoreConstraint(ScoreConstraint finalCondition) {
		this.globalScoreConstraint = finalCondition;
	}
	
	public Task getDescribedBy() {
		return this.describedBy;
	}
}
