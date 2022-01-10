package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustmodelevaluation.engine.SimulatedTask;

/**
 * @author Mickael BARON
 */
public class BayesienNetworkComputationalModel implements ComputationalModel {

	@Override
	public int compute(List<SimulatedTask> p, int max, int min) {
		long positive = p.stream().filter(sc -> sc.isPositive()).count();
		long negative = p.stream().filter(sc -> !sc.isPositive()).count();

		long num = positive - negative;
		long den = (positive + negative + 2);

		float value = (float) num / den;

		if (value >= 0) {
			return (int) (value * max);
		} else {
			return (int) (value * Math.abs(min));
		}
	}

	@Override
	public String getComputationalModelName() {
		return "BayesienNetworkComputationalModel";
	}
}