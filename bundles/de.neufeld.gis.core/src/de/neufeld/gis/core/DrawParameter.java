package de.neufeld.gis.core;

import org.eclipse.gef4.geometry.euclidean.Vector;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Rectangle;

import de.neufeld.gis.internal.core.SphericalMercatorProjection;

public final class DrawParameter {

	private double zoomLevel = 8;
	private Projection projection = new SphericalMercatorProjection();

	private Rectangle visibleArea = null;
	private Dimension drawSize = new Dimension(256, 256);
	private Point center = new Point(128, 128);

	public DrawParameter() {
		double initialWidthHeight = projection.getToWorldFactor(getZoomLevel())
				.x() * 256d;
		visibleArea = new Rectangle(21289852.624, 13775786.992,
				initialWidthHeight, initialWidthHeight);
	}

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

	public Rectangle getVisibleArea() {
		return visibleArea;
	}

	public Dimension getDrawSize() {
		return drawSize;
	}

	public void setDrawSize(Dimension drawSize) {
		Point toWorldfactor = projection.getToWorldFactor(getZoomLevel());
		visibleArea.setSize(drawSize.getScaled(toWorldfactor.x(),
				toWorldfactor.y()));
		center = new Point(drawSize.getWidth() / 2, drawSize.getHeight() / 2);
		this.drawSize = drawSize;
	}

	public void zoomIn(Point point) {
		recalculate(0.5, point);
		zoomLevel++;
	}

	public void zoomOut(Point point) {
		recalculate(2, point);
		zoomLevel--;
	}

	private void recalculate(double scaleFactor, Point newCenter) {
		Vector vector = new Vector(center, newCenter);
		
		move(vector);
		
		visibleArea = visibleArea.scale(scaleFactor);
	}

	public void move(Vector vector) {
		Point toWorldfactor = projection.getToWorldFactor(getZoomLevel());
		vector = vector.getMultiplied(toWorldfactor.x());
		visibleArea.translate(vector.toPoint());

	}
}
