/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2011 - 2015 OpenWorm.
 * http://openworm.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution, and is available at
 * http://opensource.org/licenses/MIT
 *
 * Contributors:
 *     	OpenWorm - http://openworm.org/people.html
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package org.geppetto.simulator.sph;
import java.util.List;

import org.geppetto.core.simulator.AVariableWatchFeature;
import org.geppetto.core.solver.ISolver;

/**
 * Variable watch feature for SPH simulator
 * @author Jesus Martinez (jesus@metacell.us)
 *
 */
public class SPHVariableWatchFeature extends AVariableWatchFeature{

	private ISolver _sphSolver;
	
	public SPHVariableWatchFeature(ISolver solver){
		this._sphSolver = solver;
	}

	@Override
	public void addWatchVariables(List<String> variableNames) {
		super.addWatchVariables(variableNames);
		_sphSolver.addWatchVariables(variableNames);
	}

	@Override
	public void clearWatchVariables() {
		super.clearWatchVariables();
		_sphSolver.clearWatchVariables();
	}

	@Override
	public void startWatch() {
		super.startWatch();
		_sphSolver.startWatch();
	}

	@Override
	public void stopWatch() {
		super.stopWatch();
		_sphSolver.stopWatch();
	}
}