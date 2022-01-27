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

/**
 * @author Mickael BARON
 */
public class TrustRequirementConstraint extends Constraint<TrustRequirementConstraintElement, TrustRequirementValue>
		implements Cloneable {

	public TrustRequirementConstraint(TrustRequirementConstraintElement pElement, ComparisonOperator pOperator,
			TrustRequirementValue pConditionValue) {
		this.element = pElement;
		this.operator = pOperator;
		this.value = pConditionValue;
	}

	public boolean eval(IValueInt pValue) {
		return this.operator.eval(pValue, this.getConstraintValue());
	}

	public TrustRequirementConstraint transform(int value) {
		TrustRequirementConstraint clone = null;
		try {
			clone = (TrustRequirementConstraint) super.clone();
			clone.element = this.element;
			clone.operator = (ComparisonOperator)this.operator.clone();
			clone.value = this.value.transformToExactValue(value);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return clone;
	}
	
	public String toString() {
		return ("score" + " " + this.operator.toString() + " " + this.value.toString());
	}
}
