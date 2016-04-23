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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.beans.SimulatorConfig;
import org.geppetto.core.common.GeppettoExecutionException;
import org.geppetto.core.common.GeppettoInitializationException;
import org.geppetto.core.data.model.IAspectConfiguration;
import org.geppetto.core.model.GeppettoModelAccess;
import org.geppetto.core.services.registry.ServicesRegistry;
import org.geppetto.core.simulation.ISimulatorCallbackListener;
import org.geppetto.core.simulator.AExternalProcessSimulator;
import org.geppetto.core.simulator.ExternalSimulatorConfig;
import org.geppetto.model.DomainModel;
import org.geppetto.model.ExperimentState;
import org.geppetto.model.ModelFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Wrapper class for sibernetic
 * 
 * @author Sergey Khayrulin (skhayrulin@openworm.org)
 *
 */

@Service
public class SiberneticWrapperService extends AExternalProcessSimulator
{

	protected File filePath = null;

	private String gResultFolder = "gresult";

	private String gResultFileName = "gresult"; // I think generation of file name should be dynamic
												// For different instance for example take Id of service or make static member
												// and add it in the end of file name
	
	private float simulationLenght = 0;
	
	private static Log logger = LogFactory.getLog(SiberneticWrapperService.class);

	private static String OS = System.getProperty("os.name").toLowerCase();

	@Autowired
	private SimulatorConfig siberneticSimulatorConfig;
	
	@Autowired
	private ExternalSimulatorConfig siberneticExternalSimulatorConfig;
	
	@Override
	public void initialize(DomainModel model, IAspectConfiguration aspectConfiguration, ExperimentState experimentState, ISimulatorCallbackListener listener, GeppettoModelAccess modelAccess) throws GeppettoInitializationException, GeppettoExecutionException
	{
		super.initialize(model, aspectConfiguration, experimentState, listener, geppettoModelAccess);


		/*MOdel file name*/

		this.originalFileName = (String) model.getDomainModel();//"/home/serg/git/openworm/geppetto/org.geppetto.simulator.sph/src/test/resources/demo1"
		this.createCommands(this.originalFileName, aspectConfiguration);
	}

	private boolean isWindows()
	{
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * Creates command to be executed by an external process
	 * 
	 * @param modelFileName
	 */
	public void createCommands(String modelFileName, IAspectConfiguration aspectConfiguration)
	{
		filePath = new File(modelFileName);
		logger.info("Creating command to run " + modelFileName);
		directoryToExecuteFrom = getSimulatorPath();//filePath.getParentFile().getAbsolutePath();
		outputFolder = directoryToExecuteFrom + gResultFolder; 
		if(isWindows())
		{
			// commands = new String[] { getSimulatorPath() + "mkdir.exe " + gResultFolder,
			// getSimulatorPath() + ".exe" + " -f " + siberneticModelConfig }; // without this " -f " + siberneticModelConfig it will run default demo1 rom configuration folder
		}
		else
		{
			commands = new String[] { "mkdir gresult", 
					getSimulatorPath() + "Release/Sibernetic" + " -f " + modelFileName 
					+ " timelimit=" + aspectConfiguration.getSimulatorConfiguration().getLength() 
					+ " timestep=" + aspectConfiguration.getSimulatorConfiguration().getTimestep() + " -gmode logstep=100"}; // option logstep is needed to indicate how often loging is needed by default it is 10
		
		}

		logger.info("Command to Execute: " + commands + " ...");
		logger.info("From directory : " + directoryToExecuteFrom);

	}

	@Override
	public String getName()
	{
		return this.siberneticSimulatorConfig.getSimulatorName();
	}

	@Override
	public String getId()
	{
		return this.siberneticSimulatorConfig.getSimulatorID();
	}

	@Override
	public String getSimulatorPath()
	{
		return this.siberneticExternalSimulatorConfig.getSimulatorPath();
	}
	
	@Override
	public void registerGeppettoService() throws Exception
	{
		List<ModelFormat> modelFormats = new ArrayList<ModelFormat>(Arrays.asList(ServicesRegistry.registerModelFormat("SIBERNETIC")));
		ServicesRegistry.registerSimulatorService(this, modelFormats);
	}
	
	@Override
	public void processDone(String[] processCommand) throws GeppettoExecutionException
	{

	}
	
}
