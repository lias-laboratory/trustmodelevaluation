package fr.ensma.lias.trustevaluation.model;

/**
 * @author Mickael BARON
 */
public class PercentageScoreValue extends TrustRequirementValue {

	private int defaultPreviousValue = 10;
	
	public PercentageScoreValue(int pPourcentage, int pDefaultPreviousValue) {
		this(pPourcentage);
		
		this.defaultPreviousValue = pDefaultPreviousValue;
	}
	
	public PercentageScoreValue(int pPourcentage) {
		super(pPourcentage);
	}
	
	public int computeValue(Integer previousValue) {
		if (previousValue == null) {
			previousValue = defaultPreviousValue;
		}
		
		return (int)(previousValue * (1 + (float)this.value/(float)100));
	}
	
	public ExactTrustRequirementValue transformToExactValue(int pValue) {
		return new ExactTrustRequirementValue(pValue);
	}
}
