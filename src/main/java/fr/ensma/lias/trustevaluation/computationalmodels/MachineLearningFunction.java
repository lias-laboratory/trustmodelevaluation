package fr.ensma.lias.trustevaluation.computationalmodels;

import fr.ensma.lias.trustevaluation.engine.ReportEvaluation;
import fr.ensma.lias.trustevaluation.exceptions.NotYetImplementedException;

public class MachineLearningFunction implements ComputeScore {

	public static final String SEUIL= "SEUIL";
	
	@Override
	public void computeScore(ReportEvaluation report) {
		throw new NotYetImplementedException();
	}

	@Override
	public void computeScore(ReportEvaluation report, Parameters pParameters) {
		if (!pParameters.isExistKey(SEUIL)) {
			throw new NotYetImplementedException();
		}
		int seuil = pParameters.getParameterValue(SEUIL);
		
		// TODO
	}

}
