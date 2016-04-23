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
import java.util.Map;

import javax.annotation.Resource;

import org.geppetto.core.common.GeppettoExecutionException;
import org.geppetto.core.common.GeppettoInitializationException;
import org.geppetto.core.data.model.IAspectConfiguration;
import org.geppetto.core.data.model.ResultsFormat;
import org.geppetto.core.data.model.local.LocalAspectConfiguration;
import org.geppetto.core.data.model.local.LocalSimulatorConfiguration;
import org.geppetto.core.simulation.ISimulatorCallbackListener;
import org.geppetto.model.DomainModel;
import org.geppetto.model.GeppettoFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author matteocantarelli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/app-config.xml" })
public class SiberneticWrapperServiceTest implements ISimulatorCallbackListener
{

	@Resource
	SiberneticWrapperService sibernetic = new SiberneticWrapperService();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testSibernetic() throws GeppettoInitializationException, GeppettoExecutionException, InterruptedException
	{
		DomainModel siberneticModel=GeppettoFactory.eINSTANCE.createDomainModel();
		siberneticModel.setDomainModel("/home/serg/Documents/git/openworm/geppetto/org.geppetto.simulator.sph/src/test/resources/demo1");//"/home/serg/git/openworm/Smoothed-Particle-Hydrodynamics/Release/");
		float timestep=5.0e-06f;
		float length=0.0001f;
		LocalSimulatorConfiguration simulatorConfiguration=new LocalSimulatorConfiguration(0l, "sibernetic", "", timestep, length, null);

		IAspectConfiguration config=new LocalAspectConfiguration(0l, "siberneticModel", null, null, simulatorConfiguration);
		sibernetic.initialize(siberneticModel,config,null,this,null);
		sibernetic.simulate();
		Thread.sleep(2000);
		
	}



	@Override
	public void endOfSteps(IAspectConfiguration arg0, Map<File, ResultsFormat> arg1) throws GeppettoExecutionException
	{
		System.out.println("The simulation was over");
	}

}
