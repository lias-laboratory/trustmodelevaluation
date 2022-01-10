package fr.ensma.lias.trustmodelevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustmodelevaluation.engine.SimulatedTask;

/**
 * @author Mickael BARON
 */
public interface ComputationalModel {

	int compute(List<SimulatedTask> p, int max, int min);

	String getComputationalModelName();
}
