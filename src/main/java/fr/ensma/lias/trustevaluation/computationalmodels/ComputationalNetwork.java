package fr.ensma.lias.trustevaluation.computationalmodels;

import java.util.List;

import fr.ensma.lias.trustevaluation.engine.ExecutedTask;

/**
 * @author Mickael BARON
 */
public abstract class ComputationalNetwork {

	public abstract int compute(List<ExecutedTask> p);
}
