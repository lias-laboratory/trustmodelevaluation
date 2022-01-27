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
public class ExactTrustRequirementValue extends TrustRequirementValue {

	public ExactTrustRequirementValue(int pExactValue) {
		super(pExactValue);
	}

	@Override
	public int getValue() {
		return this.value;
	}	
	
	public ExactTrustRequirementValue transformToExactValue(int value) {
		return new ExactTrustRequirementValue(value);
	}
	
	@Override
	public String toString() {
		if (this.value != null) {
			return Integer.toString(getValue());
		} else {
			return "NaN";
		}
	}
}
