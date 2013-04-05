package de.neufeld.gis.core;

import org.eclipse.gef4.geometry.planar.Point;

public interface Projection {
	double EQUATOR_IN_M=40075016.686;
	String getName();
	int getEPSGCode();
	boolean isMetric();
	Point getToWorldFactor(double zoom);
}
