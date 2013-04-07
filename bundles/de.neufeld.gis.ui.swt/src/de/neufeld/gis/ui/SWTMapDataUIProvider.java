package de.neufeld.gis.ui;

import java.util.List;

import de.neufeld.gis.core.MapDataUIProvider;

public interface SWTMapDataUIProvider extends MapDataUIProvider {

	List<CreateMapWizardPage> getCreateMapWizardPages();
}
