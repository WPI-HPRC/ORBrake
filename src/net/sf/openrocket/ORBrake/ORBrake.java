package net.sf.openrocket.ORBrake;

import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtension;

public class ORBrake extends AbstractSimulationExtension 
{
    @Override
    public String getName() 
    {
        return "HPRC - ORBrake";
    }

    @Override
    public String getDescription() 
    {
        return "Controls vehicle air brakes to dynamically adjust vehicle drag.";
    }

    @Override
    public void initialize(SimulationConditions conditions) throws SimulationException
    {
        conditions.getSimulationListenerList().add(new ORBrakeSimulationListener());
    }
}