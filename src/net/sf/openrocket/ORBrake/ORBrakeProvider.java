
package net.sf.openrocket.ORBrake;

import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtensionProvider;


@Plugin
public class ORBrakeProvider extends AbstractSimulationExtensionProvider {
	
	public ORBrakeProvider() {
		super(ORBrake.class, "Flight", "OR Brake");
	}
	
}