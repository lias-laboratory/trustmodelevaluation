package fr.ensma.lias.trustevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustevaluation.engine.SimulatedTask;

/**
 * @author Mickael BARON
 */
public abstract class ComputationalModel {

	public abstract int compute(List<SimulatedTask> p, int max, int min);
}
