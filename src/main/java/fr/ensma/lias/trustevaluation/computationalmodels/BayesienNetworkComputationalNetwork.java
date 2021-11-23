package fr.ensma.lias.trustevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustevaluation.engine.ExecutedTask;
import fr.ensma.lias.trustevaluation.engine.NegativeExecutedTask;
import fr.ensma.lias.trustevaluation.engine.PositiveExecutedTask;

/**
 * @author Mickael BARON
 */
public class BayesienNetworkComputationalNetwork extends ComputationalNetwork {

	@Override
	public int compute(List<ExecutedTask> p) {
		int max = p.get(0).getConstraint().getConstraintElement().getMax();
		int min = p.get(0).getConstraint().getConstraintElement().getMin();
		
		long positive = p.stream().filter(sc -> sc instanceof PositiveExecutedTask).count();
		long negative = p.stream().filter(sc -> sc instanceof NegativeExecutedTask).count();
		
		long num = positive - negative;
		long den = (positive + negative + 2);

		float value = (float)num/den;
		
		if (value >= 0) {
			return (int)(value*max);
		} else {
			return (int)(value*Math.abs(min));
		}		
	}
}