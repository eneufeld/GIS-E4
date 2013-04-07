package de.neufeld.gis.ui.internal.application.map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.gef4.geometry.euclidean.Vector;
import org.eclipse.gef4.geometry.planar.Dimension;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.graphics.IImageGraphics;
import org.eclipse.gef4.graphics.image.Image;
import org.eclipse.gef4.graphics.swt.SwtGraphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.neufeld.gis.core.DrawParameter;
import de.neufeld.gis.core.GisMap;
import de.neufeld.gis.core.ProjectService;

public class MapView implements PaintListener {
	private ProjectService projectService;
	private Canvas canvas;
	private DrawParameter drawParameter = new DrawParameter();

	@Inject
	public MapView(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		canvas = new Canvas(parent, SWT.NONE);
		canvas.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		drawParameter.setDrawSize(new Dimension(canvas.getSize().x, canvas
				.getSize().y));
		canvas.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				drawParameter.setDrawSize(new Dimension(canvas.getSize().x, canvas
						.getSize().y));
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
		MapMouseListener mouseListener=new MapMouseListener();
		canvas.addMouseWheelListener(mouseListener);
		canvas.addMouseMoveListener(mouseListener);
		canvas.addMouseListener(mouseListener);
		
		canvas.addPaintListener(this);
	}

	@PreDestroy
	public void preDestroy() {
		// TODO Your code here
	}

	@Focus
	public void onFocus() {
		canvas.setFocus();
	}

	@Override
	public void paintControl(PaintEvent e) {
		SwtGraphics g = new SwtGraphics(e.gc);
		Image image = new Image(canvas.getSize().x, canvas.getSize().y,
				Integer.MAX_VALUE);
		for (GisMap map : projectService.getMaps()) {
			// TODO Threads
			IImageGraphics ig = g.createImageGraphics(image);
			map.draw(ig, drawParameter);
			ig.cleanUp();
		}
		g.paint(image);
		g.cleanUp();
	}
	
	private class MapMouseListener implements MouseMoveListener,MouseListener,MouseWheelListener{
		private Point startPoint=null;
		
		@Override
		public void mouseScrolled(MouseEvent e) {
			if (e.count > 0)
				drawParameter.zoomIn(new Point(e.x, e.y));
			else
				drawParameter.zoomOut(new Point(e.x, e.y));
			canvas.redraw();
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDown(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseUp(MouseEvent e) {
			if(startPoint!=null){
				drawParameter.move(new Vector(new Point(e.x,e.y),startPoint));
				startPoint=null;
				canvas.redraw();
			}
		}

		
		@Override
		public void mouseMove(MouseEvent e) {
			if((e.stateMask&SWT.BUTTON1)!=0 && startPoint==null){
				startPoint=new Point(e.x, e.y);
			}
//			else if((e.stateMask&SWT.BUTTON1)!=0 && startPoint!=null){
//				canvas.
//			}
		}
		
	}

}