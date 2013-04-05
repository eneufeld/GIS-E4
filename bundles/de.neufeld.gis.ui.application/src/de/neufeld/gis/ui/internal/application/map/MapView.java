 
package de.neufeld.gis.ui.internal.application.map;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.graphics.IImageGraphics;
import org.eclipse.gef4.graphics.color.Color;
import org.eclipse.gef4.graphics.image.Image;
import org.eclipse.gef4.graphics.swt.SwtGraphics;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
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
	private DrawParameter drawParameter=new DrawParameter();
	@Inject
	public MapView(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		canvas = new Canvas(parent, SWT.NONE);
		canvas.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		drawParameter.setDrawSize(new Point(canvas.getSize().x, canvas.getSize().y));
		canvas.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent e) {
				drawParameter.setDrawSize(new Point(canvas.getSize().x, canvas.getSize().y));
			}
			
			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
		canvas.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseScrolled(MouseEvent e) {
				if(e.count>0)
					drawParameter.zoomIn(new Point(e.x, e.y));
				else
					drawParameter.zoomOut(new Point(e.x, e.y));
				canvas.redraw();
			}
		});
		canvas.addPaintListener(this);
	}
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		canvas.setFocus();
	}

	@Override
	public void paintControl(PaintEvent e) {
		SwtGraphics g = new SwtGraphics(e.gc);
		Image image=new Image(canvas.getSize().x, canvas.getSize().y,Integer.MAX_VALUE);
		for(GisMap map:projectService.getMaps()){
			//TODO Threads
			IImageGraphics ig=g.createImageGraphics(image);
			map.draw(ig, drawParameter);
			ig.cleanUp();
		}
		g.paint(image);
		g.cleanUp();
	}
	
	
}