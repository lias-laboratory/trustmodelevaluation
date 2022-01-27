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
public class Comment extends ApplicationRequirementConstraintElement implements IValueInt {

	private int max;

	private int min;

	public Comment(int pMin, int pMax) {
		this.min = pMin;
		this.max = pMax;
	}

	@Override
	public int getValue() {
		return 0;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	@Override
	public boolean checkValue(IValueInt current) {
		return current.getValue() >= this.min && current.getValue() <= this.max;
	}

	public String toString() {
		return "Comment(min=" + this.min + ",max=" + this.max + ")";
	}
}
