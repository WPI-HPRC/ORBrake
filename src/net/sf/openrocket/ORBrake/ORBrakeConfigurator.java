package net.sf.openrocket.ORBrake;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.gui.SpinnerEditor;
import net.sf.openrocket.gui.adaptors.DoubleModel;
import net.sf.openrocket.gui.components.BasicSlider;
import net.sf.openrocket.gui.components.UnitSelector;
import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import net.sf.openrocket.unit.UnitGroup;

@Plugin
public class ORBrakeConfigurator extends AbstractSwingSimulationExtensionConfigurator<ORBrake> {
	
	public ORBrakeConfigurator() {
		super(ORBrake.class);
	}
	
	@Override
	protected JComponent getConfigurationComponent(ORBrake extension, Simulation simulation, JPanel panel) {
		 panel.add(new JLabel("Setpoint:"));
		 
		 panel.add(new JLabel("Proportional Gain:"));

		 panel.add(new JLabel("Integral Gain:"));

		 panel.add(new JLabel("Differential Gain:"));
		 
		
		// DoubleModel m = new DoubleModel(extension, "Multiplier", UnitGroup.UNITS_RELATIVE, 0);
		
		// JSpinner spin = new JSpinner(m.getSpinnerModel());
		// spin.setEditor(new SpinnerEditor(spin));
		// panel.add(spin, "w 65lp!");
		
		// UnitSelector unit = new UnitSelector(m);
		// panel.add(unit, "w 25");
		
		// BasicSlider slider = new BasicSlider(m.getSliderModel(0, 3));
		// panel.add(slider, "w 75lp, wrap");
		
		return panel;
	}
	
}