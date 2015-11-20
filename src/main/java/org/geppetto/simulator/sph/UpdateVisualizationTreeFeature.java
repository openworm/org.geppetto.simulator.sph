package org.geppetto.simulator.sph;

import org.geppetto.core.features.IDynamicVisualTreeFeature;
import org.geppetto.core.model.typesystem.AspectNode;
import org.geppetto.core.services.GeppettoFeature;
import org.geppetto.core.solver.ISolver;

public class UpdateVisualizationTreeFeature implements IDynamicVisualTreeFeature{

	private ISolver _sphSolver;
	private GeppettoFeature type = GeppettoFeature.DYNAMIC_VISUALTREE_FEATURE;
	
	public UpdateVisualizationTreeFeature(ISolver solver){
		this._sphSolver = solver;
	}

	@Override
	public GeppettoFeature getType() {
		return type;
	}

	@Override
	public boolean updateVisualTree(AspectNode aspectNode) {
		_sphSolver.updateVisualizationTree(aspectNode);
		return false;
	}

}
