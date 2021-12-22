package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class TrustRequirement {

	private Task describedBy;

	private TrustRequirementConstraintElement trustConstraintElement;

	public TrustRequirement(Task pDescribedBy, TrustRequirementConstraintElement pScoreElement) {
		this.describedBy = pDescribedBy;
		this.trustConstraintElement = pScoreElement;
	}

	public TrustRequirementConstraintElement getScoreElement() {
		return trustConstraintElement;
	}

	public Task getDescribedBy() {
		return this.describedBy;
	}
}
