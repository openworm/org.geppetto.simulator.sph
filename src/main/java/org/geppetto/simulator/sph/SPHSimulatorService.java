/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2011, 2013 OpenWorm.
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.beans.SimulatorConfig;
import org.geppetto.core.common.GeppettoExecutionException;
import org.geppetto.core.common.GeppettoInitializationException;
import org.geppetto.core.model.IModel;
import org.geppetto.core.model.ModelInterpreterException;
import org.geppetto.core.model.runtime.AspectNode;
import org.geppetto.core.model.runtime.AspectSubTreeNode;
import org.geppetto.core.model.runtime.AspectSubTreeNode.AspectTreeType;
import org.geppetto.core.simulation.IRunConfiguration;
import org.geppetto.core.simulation.ISimulatorCallbackListener;
import org.geppetto.core.simulator.ASimulator;
import org.geppetto.core.solver.ISolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author matteocantarelli
 *
 */
@Service
public class SPHSimulatorService extends ASimulator {

	private static Log _logger = LogFactory.getLog(SPHSimulatorService.class);

	public SPHSimulatorService(){
		_logger.info("New SPH Simulator service created");
	}
	
	@Autowired
	private ISolver sphSolver;
	
	@Autowired
	private SimulatorConfig simulatorConfig;


	@Override
	public void simulate(IRunConfiguration runConfiguration, AspectNode aspect) throws GeppettoExecutionException
	{
		_logger.info("SPH Simulate method invoked");
		sphSolver.solve(runConfiguration,aspect);
		advanceTimeStep(0.000005); //TODO Fix me, what's the correct timestep? how to calculate it?
		getListener().stateTreeUpdated(aspect);
	}

	@Override
	public void initialize(List<IModel> model, ISimulatorCallbackListener listener) throws GeppettoInitializationException, GeppettoExecutionException
	{
		super.initialize(model, listener);
//		//TODO Refactor simulators to deal with more than one model!
		sphSolver.initialize(model.get(0));
		setTimeStepUnit("s");
		advanceTimeStep(0);
		setWatchableVariables();
		setForceableVariables();
	}
	

	@Override
	public boolean populateVisualTree(AspectNode aspectNode) throws ModelInterpreterException, GeppettoExecutionException {
		AspectSubTreeNode visualizationTree = (AspectSubTreeNode) aspectNode.getSubTree(AspectTreeType.VISUALIZATION_TREE);
		
		//Ask the solver to populate the visual tree
		try
		{
			sphSolver.populateVisualTree(aspectNode.getModel(),visualizationTree);
		}
		catch(GeppettoInitializationException e)
		{
			throw new ModelInterpreterException(e);
		}
		
		getListener().stateTreeUpdated(aspectNode);

		return true;
	}

	/**
	 * 
	 */
	public void setForceableVariables()
	{
		// the simulator could do some filtering here to expose a sub-set of the available variables
		getForceableVariables().setVariables(sphSolver.getForceableVariables().getVariables());
	}

	/**
	 * 
	 */
	public void setWatchableVariables()
	{
		// the simulator could do some filtering here to expose a sub-set of the available variables
		getWatchableVariables().setVariables(sphSolver.getWatchableVariables().getVariables());
	}
	
	@Override
	public void addWatchVariables(List<String> variableNames)
	{
		super.addWatchVariables(variableNames);
		sphSolver.addWatchVariables(variableNames);
	}
	
	@Override
	public void clearWatchVariables()
	{
		super.clearWatchVariables();
		sphSolver.clearWatchVariables();
	}
	
	@Override
	public void startWatch()
	{
		super.startWatch();
		sphSolver.startWatch();
	}
	
	@Override
	public void stopWatch()
	{
		super.stopWatch();
		sphSolver.stopWatch();
	}

	@Override
	public String getName() {
		return simulatorConfig.getSimulatorName();
	}

	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return simulatorConfig.getSimulatorID();
	}
}
