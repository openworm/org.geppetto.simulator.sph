package org.geppetto.simulator.sph;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.common.GeppettoExecutionException;
import org.geppetto.core.common.GeppettoInitializationException;
import org.geppetto.core.model.IModel;
import org.geppetto.core.model.state.StateTreeRoot;
import org.geppetto.core.simulation.IRunConfiguration;
import org.geppetto.core.simulation.ISimulatorCallbackListener;
import org.geppetto.core.simulator.ASimulator;
import org.geppetto.core.solver.ISolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SPHSimulatorService extends ASimulator {

	private static Log logger = LogFactory.getLog(SPHSimulatorService.class);
	
	@Autowired
	private ISolver sphSolver;


	@Override
	public void simulate(IRunConfiguration runConfiguration) throws GeppettoExecutionException
	{
		logger.info("SPH Simulate method invoked");
		StateTreeRoot results=sphSolver.solve(runConfiguration);
		getListener().stateTreeUpdated(results);
	}

	@Override
	public void initialize(IModel model, ISimulatorCallbackListener listener) throws GeppettoInitializationException
	{
		super.initialize(model, listener);
		sphSolver.initialize(model);
	}


}
