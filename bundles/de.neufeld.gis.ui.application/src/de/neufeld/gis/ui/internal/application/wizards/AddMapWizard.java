package de.neufeld.gis.ui.internal.application.wizards;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.ui.CreateMapWizardPage;
import de.neufeld.gis.ui.SWTMapDataUIProvider;

public class AddMapWizard extends Wizard {

	private MapDataProviderRegistry mapDataProviderRegistry;
	private AddMapSelectProviderPage selectProviderPage;

	public AddMapWizard(MapDataProviderRegistry mapDataProviderRegistry) {
		this.mapDataProviderRegistry = mapDataProviderRegistry;
		setForcePreviousAndNextButtons(true);
	}

	@Override
	public void addPages() {
		super.addPages();
		selectProviderPage = new AddMapSelectProviderPage(mapDataProviderRegistry);
		addPage(selectProviderPage);
	}
	List<CreateMapWizardPage> pages=null;
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		IWizardPage nextPage=null;
		if(page==selectProviderPage&&selectProviderPage.getMapDataProvider()==null){
			return null;
		}
		else if(page==selectProviderPage){
			pages=((SWTMapDataUIProvider) mapDataProviderRegistry.getUIProvider(getMapDataProvider())).getCreateMapWizardPages();
			nextPage=pages.get(0);
		}
		else{
			int index=pages.indexOf(page)+1;
			if(index==pages.size()){
				return null;
			}
			nextPage=pages.get(index);
		}
		nextPage.setWizard(this);
		return nextPage;
	}

	@Override
	public IWizardPage getPreviousPage(IWizardPage page) {
		if(page==selectProviderPage){
			return null;
		}
		else{
			List<CreateMapWizardPage> pages=((SWTMapDataUIProvider) mapDataProviderRegistry.getUIProvider(getMapDataProvider())).getCreateMapWizardPages();
			int index=pages.indexOf(page);
			if(index==0)
				return selectProviderPage;
			return pages.get(index-1);
		}
	}

	@Override
	public boolean canFinish() {
		if(pages!=null){
			for(int i=0;i<pages.size();i++){
				if(!pages.get(i).isPageComplete()){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	public String getMapName() {
		return selectProviderPage.getMapName();
	}

	public MapDataProvider getMapDataProvider() {
		return selectProviderPage.getMapDataProvider();
	}

}
