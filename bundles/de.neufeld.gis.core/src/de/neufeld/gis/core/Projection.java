package de.neufeld.gis.core;

public interface Projection {
	String getName();
	int getEPSGCode();
	boolean isMetric();
}
