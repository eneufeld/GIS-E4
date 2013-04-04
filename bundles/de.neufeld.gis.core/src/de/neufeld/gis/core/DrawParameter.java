package de.neufeld.gis.core;

import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;

import de.neufeld.gis.internal.core.SphericalMercatorProjection;

public final class DrawParameter {

	private int zoomLevel=8;
	private Projection projection=new SphericalMercatorProjection();
	private Polygon visibleArea=new Polygon(0,0,0,20000,20000,20000,20000,0);
	private Point drawSize=new Point(100, 100);
	
	public int getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	public Projection getProjection() {
		return projection;
	}
	public void setProjection(Projection projection) {
		this.projection = projection;
	}
	public Polygon getVisibleArea() {
		return visibleArea;
	}
	public void setVisibleArea(Polygon visibleArea) {
		this.visibleArea = visibleArea;
	}
	public Point getDrawSize() {
		return drawSize;
	}
	public void setDrawSize(Point drawSize) {
		this.drawSize = drawSize;
	}
}
