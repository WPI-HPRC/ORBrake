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
    		{-0.046526224, 0.000020669, -0.000299962, -0.0000001251, 0.000010806},	// 20 %
    		{-0.093052324, 0.000041339, -0.000599922, -0.0000002503, 0.000021611},	// 40 %
    		{-0.139578485, 0.000062008, -0.000899883, -0.0000003754, 0.000032417},	// 60 %    		
    		{-0.186104574, 0.000082677, -0.001199843, -0.0000005006, 0.000043222},	// 80 %
    		{-0.232630682, 0.000103346, -0.001499803, -0.0000006257, 0.000054028}	// 100%
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
    
    // START OF PSEUDOCODE

    // Define variables

    	// Error function variables
    	double err; //error
    	double SP; //setpoint
    	double measure; //measurement 

    	// PID controller gain constants
    	double Kp;
    	double Ki;
    	double Kd;

    	// Low pass filter time constant
    	double tau;

    	// Output limits
    	double min;
    	double max;

    	// Sample time in seconds
    	double T;

    	double prop; //proportional

    	// Memory
    	double inte; //integral
    	double prev_err; //previous error
    	double diff; //differential
    	double prev_measure; //previous measurement

    	// Output
    	double out;

    public PID_initial(variables)
    {
    	inte = 0;
    	prev_err = 0;
    	diff = 0;
    	prev_measure = 0;
    	out = 0;
    }
    public PID_update(variables)
    {	
    	err = setpoint - measure;
    	prop = Kp*err;
    	inte = 0.5*Ki*T*(err+prev_err) + inte;
    	// [clamp integrator here]
    	diff = ( 2*Kd*(measure-prev_measure) + (2*tau-T)*diff ) / (2*tau+T);
    	out = prop + inte + diff;
    }
    
    // END OF PSEUDOCODE
    
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