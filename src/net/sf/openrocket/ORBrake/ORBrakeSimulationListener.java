package net.sf.openrocket.ORBrake;

import java.lang.Math;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.aerodynamics.FlightConditions;

public class ORBrakeSimulationListener extends AbstractSimulationListener {

    private double velocity;
    private double drag_coef = 1.3;
    private double final density = 1.225;

	public ORBrakeSimulationListener() {
		super();
	}
	
	@Override
    public FlightConditions postFlightConditions(SimulationStatus status, FlightConditions conditions)
    {
        velocity = conditions.getVelocity();
        return null; 
    }

    @Override
    public double postSimpleThrustCalculation(SimulationStatus status, double thrust) throws SimulationException
    {
        // if (status.getSimulationTime() )
        return thrust + AirbrakeForce(velocity);
    }
    
    private double AirbrakeForce(double vel)
    {
        return drag_coef * density * Math.pow(vel, 2) / 2;
    }

}