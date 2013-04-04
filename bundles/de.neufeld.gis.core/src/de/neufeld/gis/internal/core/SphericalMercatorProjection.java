package de.neufeld.gis.internal.core;

import de.neufeld.gis.core.Projection;

public class SphericalMercatorProjection implements Projection {

	@Override
	public String getName() {
		return "SpericalMercator";
	}

	@Override
	public int getEPSGCode() {
		return 3857;
	}

	@Override
	public boolean isMetric() {
		return true;
	}

}
