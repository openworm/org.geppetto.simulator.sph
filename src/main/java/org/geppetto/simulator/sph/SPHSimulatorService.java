package org.geppetto.simulator.sph;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geppetto.core.model.IModel;
import org.geppetto.core.simulation.ITimeConfiguration;
import org.geppetto.core.simulator.AParallelSimulator;
import org.geppetto.core.solver.ISolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SPHSimulatorService extends AParallelSimulator {

	private static Log logger = LogFactory.getLog(SPHSimulatorService.class);
	
	private ITimeConfiguration _timeConfiguration;
	
	@Autowired
	private ISolver sphSolver;
	
	@Override
	public void startSimulatorCycle() {
		logger.debug("SPH Simulator cycle started");
		clearQueue();		
	}

	@Override
	public void simulate(IModel model, ITimeConfiguration timeConfiguration) {
		enqueue(model, sphSolver);
		_timeConfiguration=timeConfiguration;
		
	}

	@Override
	public void endSimulatorCycle() {
		logger.debug("cycle finished");
		List<List<IModel>> results=sphSolver.solve(getQueue(sphSolver), _timeConfiguration);
		for(List<IModel> models:results)
		{
			getListener().resultReady(models);
		}
	}

}
