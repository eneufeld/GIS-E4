 
package de.neufeld.gis.ui.internal.application.handlers;

import java.io.Serializable;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.core.ProjectService;
import de.neufeld.gis.ui.internal.application.navigator.NavigatorView;
import de.neufeld.gis.ui.internal.application.wizards.AddMapWizard;

public class AddMapHandler {
	@Execute
	public void execute(ProjectService projectService,MapDataProviderRegistry mapDataProviderRegistry,@Named(IServiceConstants.ACTIVE_PART) MPart navigator,@Named(IServiceConstants.ACTIVE_SHELL)Shell shell) {
		AddMapWizard addMapWizard=new AddMapWizard(mapDataProviderRegistry);
		WizardDialog dialog=new WizardDialog(shell, addMapWizard);
		if(Window.OK==dialog.open()){
			String mapName=addMapWizard.getMapName();
			MapDataProvider mapDataProvider=addMapWizard.getMapDataProvider();
			Serializable providerSpecificData=addMapWizard.getProviderSpecific();
			projectService.createMap(mapName, mapDataProvider,providerSpecificData);
			((NavigatorView)navigator.getObject()).refresh();
		}
	}
		
}