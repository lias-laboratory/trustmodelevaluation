package fr.ensma.lias.trustevaluation.engine;

import java.util.List;

import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraintElement;

/**
 * @author Mickael BARON
 */
public class ReportEvaluation {

	private List<EvaluatedTask> evaluatedTasks;

	private TrustRequirementConstraintElement scoreElement;

	private int score;

	public List<EvaluatedTask> getEvaluatedTasks() {
		return evaluatedTasks;
	}

	public void setEvaluatedTasks(List<EvaluatedTask> evaluatedTasks) {
		this.evaluatedTasks = evaluatedTasks;
	}

	public TrustRequirementConstraintElement getScoreElement() {
		return this.scoreElement;
	}

	public void setScoreElement(TrustRequirementConstraintElement initScoreConstraint) {
		this.scoreElement = initScoreConstraint;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
