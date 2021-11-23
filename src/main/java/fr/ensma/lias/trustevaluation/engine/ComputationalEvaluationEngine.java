package fr.ensma.lias.trustevaluation.engine;

import fr.ensma.lias.trustevaluation.computationalmodels.ComputationalNetwork;
import fr.ensma.lias.trustevaluation.exceptions.NotYetImplementedException;

/**
 * @author Mickael BARON
 */
public class ComputationalEvaluationEngine {

	public ReportEvaluation eval(Scenario scenario, ComputationalNetwork computationalNetwork) {
		// First step: execute computational model
		this.executeComputationalModel(scenario, computationalNetwork);
		
		// Second step: compute the distances between computational model values and user requirements.
		this.computeDistance(scenario);
		
		// Third step: create a report.
		return createReport(scenario);
	}
	
	protected Scenario executeComputationalModel(Scenario scenario, ComputationalNetwork computationalNetwork) {
		for (int i = 0 ; i < scenario.getExecutedTasks().size(); i++) {
			scenario.getExecutedTask(i).setComputedValue(computationalNetwork.compute(scenario.getExecutedTasks().subList(0, i+1)));
		}
		
		for(ExecutedTask current : scenario.getExecutedTasks()) {
			System.out.println(current.getComputedValue());
		}
		return scenario;
	}
	
	protected Scenario computeDistance(Scenario scenario) {
		throw new NotYetImplementedException();
	}
	
	protected ReportEvaluation createReport(Scenario scenario) {
		throw new NotYetImplementedException();		
	}
}
