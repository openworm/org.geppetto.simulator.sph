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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.beans.SimulatorConfig;
import org.geppetto.core.common.GeppettoExecutionException;
import org.geppetto.core.common.GeppettoInitializationException;
import org.geppetto.core.data.model.IAspectConfiguration;
import org.geppetto.core.features.IDynamicVisualTreeFeature;
import org.geppetto.core.model.IModel;
import org.geppetto.core.model.runtime.AspectNode;
import org.geppetto.core.services.GeppettoFeature;
import org.geppetto.core.services.ModelFormat;
import org.geppetto.core.services.registry.ServicesRegistry;
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
public class SPHSimulatorService extends ASimulator
{

	private static Log _logger = LogFactory.getLog(SPHSimulatorService.class);

	public SPHSimulatorService()
	{
		_logger.info("New SPH Simulator service created");
	}

	@Autowired
	private ISolver sphSolver;

	@Autowired
	private SimulatorConfig simulatorConfig;

	@Override
	public void simulate(IAspectConfiguration aspectConfiguration, AspectNode aspectNode) throws GeppettoExecutionException
	{
		_logger.info("SPH Simulate method invoked");
		sphSolver.solve(aspectConfiguration, aspectNode);
		((IDynamicVisualTreeFeature) this.getFeature(GeppettoFeature.DYNAMIC_VISUALTREE_FEATURE)).updateVisualTree(aspectNode);
		advanceTimeStep(0.000005, aspectNode); // TODO Fix me, what's the correct timestep?
		// how to calculate it?
		getListener().stepped(aspectNode);
	}

	@Override
	public void initialize(List<IModel> models, ISimulatorCallbackListener listener) throws GeppettoInitializationException, GeppettoExecutionException
	{
		super.initialize(models, listener);
		// //TODO Refactor simulators to deal with more than one model!
		if(models.size() > 1)
		{
			throw new GeppettoInitializationException("More than one model in the SPH simulator is currently not supported");
		}
		sphSolver.initialize(models.get(0));
		setTimeStepUnit("s");
		this.addFeature(new SPHVariableWatchFeature(sphSolver));
		this.addFeature(new UpdateVisualizationTreeFeature(sphSolver));

	}

	@Override
	public String getName()
	{
		return simulatorConfig.getSimulatorName();
	}

	@Override
	public String getId()
	{
		// TODO Auto-generated method stub
		return simulatorConfig.getSimulatorID();
	}

	@Override
	public void registerGeppettoService() throws Exception
	{
		List<ModelFormat> modelFormats = new ArrayList<ModelFormat>(Arrays.asList(ServicesRegistry.registerModelFormat("SPH")));
		ServicesRegistry.registerSimulatorService(this, modelFormats);
	}

}
