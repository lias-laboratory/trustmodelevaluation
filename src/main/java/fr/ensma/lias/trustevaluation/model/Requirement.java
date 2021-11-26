package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class Requirement {

	private Task describedBy;

	private ScoreElement scoreElement;

	public Requirement(Task pDescribedBy, ScoreElement pScoreElement) {
		this.describedBy = pDescribedBy;
		this.scoreElement = pScoreElement;
	}

	public ScoreElement getScoreElement() {
		return scoreElement;
	}

	public Task getDescribedBy() {
		return this.describedBy;
	}
}
