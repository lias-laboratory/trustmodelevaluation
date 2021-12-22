package fr.ensma.lias.trustevaluation.model;

import fr.ensma.lias.trustevaluation.exceptions.NotYetImplementedException;

/**
 * @author Mickael BARON
 */
public class PercentageScoreValue extends TrustRequirementValue {

	protected Integer previousValue = null; 
	
	public PercentageScoreValue(int pPourcentage) {
		super(pPourcentage);
	}
	
	public void setPreviousValue(int value) {
		this.previousValue = value;
	}

	@Override
	public int getValue() {
		if (previousValue == null) {
			throw new NotYetImplementedException("Previous value not defined.");
		} else {			
			return (int)(previousValue * (1 + (float)this.value/(float)100));
		}
	}	
}
