package org.geppetto.simulator.sph;


import java.util.List;

import org.geppetto.core.simulator.AVariableWatchFeature;
import org.geppetto.core.solver.ISolver;
import org.springframework.beans.factory.annotation.Autowired;

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
