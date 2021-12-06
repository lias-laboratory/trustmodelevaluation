package fr.ensma.lias.trustevaluation.computationalmodels;

import fr.ensma.lias.trustevaluation.engine.ReportEvaluation;

/**
 * @author Mickael BARON
 */
public interface ComputeScore {

	void computeScore(ReportEvaluation report);
	
	void computeScore(ReportEvaluation report, Parameters pParameters);
}
