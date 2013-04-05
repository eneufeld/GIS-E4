package de.neufeld.gis.internal.core;

import org.eclipse.gef4.geometry.planar.Point;

import de.neufeld.gis.core.Projection;

public class SphericalMercatorProjection implements Projection {
	private final double M_PER_PIXEL_ZOOM_0=156543.034;
	
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

	@Override
	public Point getToWorldFactor(double zoom) {
		double numTiles=Math.pow(2, zoom);
		double m_per_pixel=M_PER_PIXEL_ZOOM_0/numTiles;
		return new Point(m_per_pixel,m_per_pixel);
	}

}
