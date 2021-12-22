package fr.ensma.lias.trustevaluation.engine;

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.trustevaluation.exceptions.NotYetImplementedException;
import fr.ensma.lias.trustevaluation.model.Reason;
import fr.ensma.lias.trustevaluation.model.Decomposition;
import fr.ensma.lias.trustevaluation.model.IterativeTrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.PercentageScoreValue;
import fr.ensma.lias.trustevaluation.model.PositiveTask;
import fr.ensma.lias.trustevaluation.model.Task;
import fr.ensma.lias.trustevaluation.model.TrustRequirement;
import fr.ensma.lias.trustevaluation.model.TrustRequirementConstraint;
import fr.ensma.lias.trustevaluation.model.TrustRequirementValue;

/**
 * @author Mickael BARON
 */
public class TrustRequirementEngine {

	private TrustRequirementConstraint previousTaskConstraint;

	public Scenario eval(TrustRequirement pRequirement) {
		List<SimulatedTask> eval = this.eval(pRequirement.getDescribedBy());
		Scenario currentScenario = new Scenario();
		currentScenario.setSimulatedTasks(eval);
		currentScenario.setScoreElement(pRequirement.getScoreElement());

		return currentScenario;
	}

	protected List<SimulatedTask> eval(Task task) {
		if (task.getDecomposition() == Decomposition.LEAF) {
			List<SimulatedTask> executedAllTasks = new ArrayList<>();

			for (int i = 0; i < task.getIteration(); i++) {
				if (task.getConstraint() instanceof IterativeTrustRequirementConstraint) {
					// Last element.
					if (i == task.getIteration() - 1) {
						executedAllTasks.add(build(task, true));
					} else {
						executedAllTasks.add(build(task, false));
					}
				} else {
					executedAllTasks.add(build(task, true));
				}
			}

			return executedAllTasks;
		} else if (task.getDecomposition() == Decomposition.SEQ) {
			List<SimulatedTask> executedAllTasks = new ArrayList<>();
			List<Task> subTasks = task.getSubTasks();

			for (int i = 0; i < task.getIteration(); i++) {
				for (Task current : subTasks) {
					List<SimulatedTask> executedTasks = eval(current);
					executedAllTasks.addAll(executedTasks);
				}
			}

			return executedAllTasks;
		} else {
			throw new NotYetImplementedException(task.getDecomposition().toString());
		}
	}

	protected SimulatedTask build(Task task, boolean withCause) {
		TrustRequirementConstraint constraint = null;
		if (withCause) {
			constraint = task.getConstraint();
		}
		List<Reason> reason = task.getReasons();
		String name = task.getName();

		if (task.getConstraint() != null) {
			TrustRequirementValue constraintValue = task.getConstraint().getConstraintValue();
			if (constraintValue instanceof PercentageScoreValue) {
				((PercentageScoreValue) constraintValue)
						.setPreviousValue(previousTaskConstraint.getConstraintValue().getValue());
			}

			this.previousTaskConstraint = task.getConstraint();
		}

		return new SimulatedTask(name, reason, constraint, task instanceof PositiveTask);
	}
}