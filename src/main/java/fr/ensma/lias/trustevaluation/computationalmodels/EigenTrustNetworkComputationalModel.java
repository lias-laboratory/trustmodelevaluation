package fr.ensma.lias.trustevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustevaluation.engine.SimulatedTask;

/**
 * @author Mickael BARON
 */
public class EigenTrustNetworkComputationalModel implements ComputationalModel {

	@Override
	public int compute(List<SimulatedTask> p, int max, int min) {
		long positive = p.stream().filter(sc -> sc.isPositive()).count();
		long negative = p.stream().filter(sc -> !sc.isPositive()).count();

		long num = Math.max(positive - negative, 0);
		long den = (positive + negative);
		
		float value = (float) num / den;

		if (value >= 0) {
			return (int) (value * max);
		} else {
			return (int) (value * Math.abs(min));
		}
	}

	@Override
	public String getComputationalModelName() {
		return "EigenTrustNetworkComputationalModel";
	}

}
