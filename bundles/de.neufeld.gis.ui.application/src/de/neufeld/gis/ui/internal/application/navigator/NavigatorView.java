 
package de.neufeld.gis.ui.internal.application.navigator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.neufeld.gis.core.ProjectService;

public class NavigatorView {
	
	private TableViewer navigator;
	private ProjectService projectService;


	@Inject
	public NavigatorView() {
		//TODO Your code here
	}
	
	@PostConstruct
	public void postConstruct(Composite parentComposite,ProjectService projectService,EMenuService menuService) {
		this.projectService=projectService;
		navigator = new TableViewer(parentComposite);
		navigator.setContentProvider(ArrayContentProvider.getInstance());
		navigator.setLabelProvider(new LabelProvider());
		navigator.setInput(projectService.getMaps());
		menuService.registerContextMenu(navigator.getTable(), "de.neufeld.gis.ui.application.popupmenu.navigator");
		
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(navigator.getControl());
	}
	
	
	@PreDestroy
	public void preDestroy() {
		//TODO Your code here
	}
	
	
	@Focus
	public void onFocus() {
		navigator.getControl().setFocus();
	}

	public void refresh() {
		navigator.setInput(projectService.getMaps());
	}
	
	
}