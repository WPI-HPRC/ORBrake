package net.sf.openrocket.ORBrake;

import java.lang.Math;
import java.util.stream.IntStream; 
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.aerodynamics.FlightConditions;

public class ORBrakeSimulationListener extends AbstractSimulationListener {

    double velocity;
    double altitude;
    
    private static final double surfConst[][] = {	// Surface constants for presimulated airbrake extensions.
    		{-0.000000000, 0.000000000, -0.000000000, 0.0000000000, 0.000000000},	// 0  %
    		{-0.023136854, 0.000010395, 0.000071445, -0.0000000862, 0.000008669},	// 20 %
    		{-0.041211681, 0.000017555, 0.000565077, -0.0000002520, 0.000017995},	// 40 %
    		{-0.085142430, 0.000038009, 0.000316569, -0.0000003705, 0.000029817},	// 60 %    		
    		{-0.152819731, 0.000068195, 0.000074012, -0.0000004884, 0.000043697},	// 80 %
    		{-0.261060050, 0.000115821, -0.000232887, -0.0000007869, 0.000061334}	// 100%
    };

	public ORBrakeSimulationListener() {
		super();
	}
	
	@Override
    public FlightConditions postFlightConditions(SimulationStatus status, FlightConditions conditions)
    /**
     * Called immediately after the flight conditions of the current time 
     * step so that relevant ones can be extracted.
     * 
     * @param	status		Object that contains simulation status details.
     * @param	conditions	Object that contains flight condition details.
     * @return	null.
     */
    {
        velocity = conditions.getVelocity();
        return null; 
    }

    @Override
    public double postSimpleThrustCalculation(SimulationStatus status, double thrust) // throws SimulationException
    /**
     * Influences the thrust after it is computed at each time step 
     * but before it is applied to the vehicle.
     * 
     * @param status	Object that contains simulation status details.
     * @param thrust	The computed motor thrust.
     * @return	The modified thrust to be actually applied.
     */
    {
        // if (status.getSimulationTime() )
        return thrust + airbrakeForce();
    }
    
    double airbrakeForce()
    {
    	double requiredDrag = 3;
        return requiredDrag;
    	//return drag_coef * density * Math.pow(vel, 2) / 2;
    }
    
    double extensionFromDrag(double requiredDrag)
    /**
     * Computes the required extension to achieve a required drag.
     * 
     * @param requiredDrag	The desired drag from the control system.
     * @return	The percentage deployment that will produce that drag.
     */
    {
    	double[] drag = new double[6];

    	// Compute drag for each known extension.
    	IntStream.range(0, 5).forEachOrdered(n -> {
    	    drag[n] = this.dragSurface(n);
    	});
    	
    	// Interpolate to find desired extension
    	double extension = 0;
    	double term;
    	
    	for (int i = 0; i < 5; ++i)
    	{
    		term = i;
    		for (int j = 0; j < 5; ++j)
    		{
        		if(j != i)
        		{
        			term *= (requiredDrag - drag[j]) / (drag[i] - drag[j]);
        		}
        	};
        	
        	extension += term;
    	};
    	extension *= 20;
    	return extension;
    }
    
    double dragSurface(int extNum)
    /**
     * Finds the drag force at one of the 6 drag surfaces given the current 
     * velocity and altitude.
     * 
     * @param	extNum	The index of the extension percentage.  Each increment 
     * 					represents a 20% increase.
     * @return	The drag given altitude, velocity, and extension.
     */
    {
    	return surfConst[extNum][0] + surfConst[extNum][1]*altitude + surfConst[extNum][2]*velocity + 
    			surfConst[extNum][3]*altitude*velocity + surfConst[extNum][4]*Math.pow(velocity, 2);
    }

}