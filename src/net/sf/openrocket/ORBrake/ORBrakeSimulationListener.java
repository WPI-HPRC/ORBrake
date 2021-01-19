package net.sf.openrocket.ORBrake;

import java.lang.Math;
import java.util.stream.IntStream; 
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.aerodynamics.FlightConditions;

public class ORBrakeSimulationListener extends AbstractSimulationListener {

//    double velocity;
//    double altitude;
//    double thrust;
    double setpoint = 4550/3.281; //desired altitude in feet
        
    // Input parameters for PID controller
	double Kp = 1; //proportional gain constant
    double Ki = 0; //integral gain constant
    double Kd = 0; //derivative gain constant
    double tau = 1; //low pass filter time constant
//	double min_inte = 1; //integral min limit
//	double max_inte = 5; //integral max limit
    double T = 1; //sample time in sec
    
    // Memory variables for PID controller
    double inte = 0; //integral term
    double prev_err = 0; //previous error
    double diff = 0; //differential term
    double prev_measure = 0; //previous measurement
    
    private static final double surfConst[][] = {	// Surface constants for presimulated airbrake extensions.
    		{-0.000000000, 0.000000000, -0.000000000, 0.0000000000, 0.000000000},	// 0  %
    		{-0.102912726, -0.000151700, 0.001042665, 0.0000041259, 0.000415087},	// 20 %
    		{-0.183309557, -0.000256191, 0.008246675, 0.0000120688, 0.000861634},	// 40 %
    		{-0.378713528, -0.000554695, 0.004619966, 0.0000177406, 0.001427697},	// 60 %    		
    		{-0.679742164, -0.000995227, 0.001080119, 0.0000233844, 0.002092339},	// 80 %
    		{-1.161195104, -0.001690272, -0.003398721, 0.0000376809, 0.002936851}	// 100%
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
//        velocity = conditions.getVelocity();
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
//    	status.getRocketVelocity().normalize();
//    	this.thrust = thrust;
//    	this.altitude = status.getRocketPosition().z;
        return thrust + airbrakeForce(status, thrust);
    }
    
    double airbrakeForce(SimulationStatus status, double thrust)
    {
    	double requiredDrag = requiredDrag(setpoint, status, thrust);
    	double surf = dragSurface(5, status.getRocketPosition().z, status.getRocketVelocity().length());
    	if (requiredDrag > surf) {
    		requiredDrag = surf;
    	} else if (requiredDrag < 0) {
    		requiredDrag = 0;
    	}
        return -requiredDrag;
    }
    
    double requiredDrag(double SP,SimulationStatus status, double thrust) //PID controller to get updated drag coefficient
    /**
     * SP = desired altitude setpoint
     * measure = actual altitude
     */
    {
    	// Initial conditions  	
    	double out = 0;
    	double measure = status.getRocketPosition().z;
    	double velocity = status.getRocketVelocity().length();
    	
    	if (thrust == 0)
    	{    		
    		// Error function
	    	double err = SP - measure;
	    	
	    	// Proportional term
	    	double prop = Kp*err;
	    	
	    	// Integral term
	    	inte += 0.5*Ki*T*(err+prev_err);
	    	
	    	// Anti-wind up (dynamic integral clamping)
	    	double min_inte; //integral min limit
	    	double max_inte; //integral max limit
	    	if (dragSurface(5, measure, velocity) > prop) {
	    		max_inte = dragSurface(5, measure, velocity) - prop;
	    	} else {
	    		max_inte = 0;
	    	}
	    	if (0 < prop) {
	    		min_inte = 0 - prop;
	    	} else {
	    		min_inte = 0;
	    	}
	    	if (inte > max_inte) {
	    		inte = max_inte;
	    	} else if (inte < min_inte) {
	    		inte = min_inte;
	    	}
	    	
	    	// Differential term
	    	diff = -( 2*Kd*(measure-prev_measure) + (2*tau-T)*diff ) / (2*tau+T);
	    	
	    	// Output 
	    	out = prop + inte + diff;
	    	
	    	// Update memory
	    	prev_err = err;
	    	prev_measure = measure;
	    }
    	
    	return out; //required drag
    }
    
//    double extensionFromDrag(double requiredDrag)
//    /**
//     * Computes the required extension to achieve a required drag.
//     * 
//     * @param requiredDrag	The desired drag from the control system.
//     * @return	The percentage deployment that will produce that drag.
//     */
//    {
//    	double[] drag = new double[6];
//
//    	// Compute drag for each known extension.
//    	IntStream.range(0, 5).forEachOrdered(n -> {
//    	    drag[n] = this.dragSurface(n);
//    	});
//    	
//    	// Interpolate to find desired extension
//    	double extension = 0;
//    	double term;
//    	
//    	for (int i = 0; i < 5; ++i)
//    	{
//    		term = i;
//    		for (int j = 0; j < 5; ++j)
//    		{
//        		if(j != i)
//        		{
//        			term *= (requiredDrag - drag[j]) / (drag[i] - drag[j]);
//        		}
//        	};
//        	
//        	extension += term;
//    	};
//    	extension *= 20;
//    	return extension;
//    }
    
    double dragSurface(int extNum, double altitude, double velocity)
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