
package net.sf.openrocket.ORBrake;

import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtensionProvider;


@Plugin
public class ORBrakeProvider extends AbstractSimulationExtensionProvider {
	/**
	 * Lists the plugin in the simulation options.
	 */
	
	public ORBrakeProvider() {
		super(ORBrake.class, "WPI", "OR Brake");
	}
	
}