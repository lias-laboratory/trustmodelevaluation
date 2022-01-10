package fr.ensma.lias.trustmodelevaluation.model;

import java.util.ArrayList;
import java.util.List;

import fr.ensma.lias.trustmodelevaluation.exceptions.NotYetImplementedException;

/**
 * @author Mickael BARON
 */
public abstract class Task {

	private String name;

	private int iteration = 1;

	private List<Task> isComposedOf;

	private Task parent;

	protected Decomposition decomposition;

	protected List<Reason> reasons;

	protected TrustRequirementConstraint constraint;

	public Task(String pName) {
		this.name = pName;
		this.isComposedOf = new ArrayList<>();
		this.reasons = new ArrayList<>();
		this.decomposition = Decomposition.UNKNOWN;
	}

	public void addTask(Task newTask) {
		this.isComposedOf.add(newTask);
		newTask.setParent(this);
	}

	private void setParent(Task pParent) {
		this.parent = pParent;
	}

	public void setIteration(int pIteration) {
		if (pIteration <= 0) {
			throw new NotYetImplementedException("Iteration must be positive");
		}
		this.iteration = pIteration;
	}

	public int getIteration() {
		return this.iteration;
	}

	public boolean isLeaf() {
		return this.decomposition == Decomposition.LEAF;
	}

	public Decomposition getDecomposition() {
		return decomposition;
	}

	public void setDecomposition(Decomposition pDecomposition) {
		this.decomposition = pDecomposition;
	}

	public List<Task> getSubTasks() {
		return this.isComposedOf;
	}

	public String getName() {
		return this.name;
	}

	public Task getParent() {
		return this.parent;
	}

	public List<Reason> getReasons() {
		return this.reasons;
	}

	public TrustRequirementConstraint getConstraint() {
		return constraint;
	}

	public void setConstraint(TrustRequirementConstraint constraint) {
		this.constraint = constraint;
	}
	
	protected void addNewCause(Action pAction, int value) {
		Reason newReason = new Reason();
		newReason.action = pAction;
		newReason.value = value;
		
		this.reasons.add(newReason);
	}
}
