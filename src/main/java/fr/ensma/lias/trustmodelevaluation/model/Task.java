/*********************************************************************************
 * This file is part of TME (Trust Model Evaluation) tool.
 * Copyright (C) 2022 LIAS/ISAE-ENSMA and O°Code
 * 
 * TMEDe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with TME.  If not, see <http://www.gnu.org/licenses/>.
 **********************************************************************************/
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

	public void addReason(Action p) {
		Reason current = new Reason();
		current.setAction(p);

		this.reasons.add(current);
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
