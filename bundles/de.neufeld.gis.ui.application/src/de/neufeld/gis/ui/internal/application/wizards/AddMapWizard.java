package de.neufeld.gis.ui.internal.application.wizards;

import org.eclipse.jface.wizard.Wizard;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;

public class AddMapWizard extends Wizard {

	private MapDataProviderRegistry mapDataProviderRegistry;
	private AddMapSelectProviderPage selectProviderPage;

	public AddMapWizard(MapDataProviderRegistry mapDataProviderRegistry) {
		this.mapDataProviderRegistry = mapDataProviderRegistry;
	}

	@Override
	public void addPages() {
		super.addPages();
		selectProviderPage = new AddMapSelectProviderPage(mapDataProviderRegistry);
		addPage(selectProviderPage);
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
