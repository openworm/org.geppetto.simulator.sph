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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.beans.SimulatorConfig;
import org.geppetto.core.simulator.AExternalProcessSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Wrapper class for sibernetic
 * 
 * @author Sergey Khayrulin (skhayrulin@openworm.org)
 *
 */

@Service
public class SiberneticWrapperService extends AExternalProcessSimulator {

	protected File filePath = null;
	
	private String gResultFolder = "gResult";
	
	private String gResultFileName = "gResult"; // I think generation of file name should be dynamic
												// For different instance for example take Id of service or make static member 
												// and add it in the end of file name
	private String siberneticModelConfig = "";
	
	private static Log logger = LogFactory.getLog(SiberneticWrapperService.class);
	
	@Autowired
	private SimulatorConfig siberneticSimulatorConfig;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Creates command to be executed by an external process
	 * 
	 * @param originalFileName
	 * @param aspect
	 */
	public void createCommands(String originalFileName)
	{
		filePath = new File(originalFileName);

		logger.info("Creating command to run " + originalFileName);
		directoryToExecuteFrom = filePath.getParentFile().getAbsolutePath(); // In this case all needed files like opencl program should be near
		outputFolder = directoryToExecuteFrom + gResultFolder;

		if(false/*Utilities.isWindows()*/) //I need Utilities class but it in other bundle
		{
			//commands = new String[] { getSimulatorPath() + "mkdir.exe " + gResultFolder,  + ".exe" + " -f " + siberneticModelConfig }; // without this " -f " + siberneticModelConfig it will run default demo1 rom configuration folder
		}
		else
		{
			//commands = new String[] { getSimulatorPath() + "mkdir " + gResultFolder, getSimulatorPath() + filePath.getAbsolutePath() + " -f " + siberneticModelConfig }; // without this " -f " + siberneticModelConfig it will run default demo1 rom configuration folder
		}

		logger.info("Command to Execute: " + commands + " ...");
		logger.info("From directory : " + directoryToExecuteFrom);

	}

	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerGeppettoService() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSimulatorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
