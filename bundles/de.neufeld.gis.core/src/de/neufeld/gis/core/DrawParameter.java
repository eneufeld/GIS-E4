package de.neufeld.gis.core;

import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;

import de.neufeld.gis.internal.core.SphericalMercatorProjection;

public final class DrawParameter {

	private double zoomLevel=8;
	private Projection projection=new SphericalMercatorProjection();
	private Polygon visibleArea=new Polygon(21289852.624,13775786.992,21289852.624,13932330.026,21446395.658,13932330.026,21446395.658,13775786.992);
	private Point drawSize=new Point(256, 256);
	
	public double getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(double zoomLevel) {
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
		Point toWorldfactor=projection.getToWorldFactor(getZoomLevel());
		Point topLeftCorner=visibleArea.getPoints()[0];
		this.visibleArea=new Polygon(topLeftCorner,drawSize.getScaled(toWorldfactor.x(), 0).translate(topLeftCorner),drawSize.getScaled(toWorldfactor.x()).translate(topLeftCorner),drawSize.getScaled(0,toWorldfactor.x()).translate(topLeftCorner));
		this.drawSize = drawSize;
	}
	public void zoomIn(Point point) {
		zoomLevel++;
		recalculate(+1,point);
	}
	public void zoomOut(Point point) {
		zoomLevel--;
		recalculate(-1,point);
	}
	private void recalculate(double zoomChange,Point newCenter){
		Point toWorldfactor=projection.getToWorldFactor(getZoomLevel());
		Point translate=new Point(newCenter.x()-drawSize.x()/2, newCenter.y()-drawSize.y()/2);
		visibleArea=visibleArea.getScaled(Math.pow(2, -zoomChange));
		translate=translate.getScaled(toWorldfactor.x());
		Point topLeftCorner=visibleArea.getPoints()[0];
		topLeftCorner.translate(translate);
		this.visibleArea=new Polygon(topLeftCorner,drawSize.getScaled(toWorldfactor.x(), 0).translate(topLeftCorner),drawSize.getScaled(toWorldfactor.x()).translate(topLeftCorner),drawSize.getScaled(0,toWorldfactor.x()).translate(topLeftCorner));
	}
}
