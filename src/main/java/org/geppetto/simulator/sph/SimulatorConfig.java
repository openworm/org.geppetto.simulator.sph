package org.geppetto.simulator.sph;

public class SimulatorConfig {

	private int simulatorCapacity;
	
	private String simulatorName;
	
	public void setSimulatorName(String simulatorName){
		this.simulatorName = simulatorName;
	}
	
	public String getSimulatorName(){
		return this.simulatorName;
	}
	
	public void setSimulatorCapacity(int simulatorCapacity){
		this.simulatorCapacity = simulatorCapacity;
	}
	
	public int getSimulatorCapacity(){
		return this.simulatorCapacity;
	}
}
