package de.neufeld.gis.provider.osm.swt;

import java.util.Collections;
import java.util.List;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.provider.osm.OSMProvider;
import de.neufeld.gis.provider.osm.internal.swt.SetOSMServerPage;
import de.neufeld.gis.ui.CreateMapWizardPage;
import de.neufeld.gis.ui.SWTMapDataUIProvider;

public class OSMUIProvider implements SWTMapDataUIProvider {

	private MapDataProviderRegistry mapDataProviderRegistry;
	
	public void setMapDataProviderRegistry(MapDataProviderRegistry mapDataProviderRegistry){
		this.mapDataProviderRegistry=mapDataProviderRegistry;
	}
	public void unsetMapDataProviderRegistry(MapDataProviderRegistry mapDataProviderRegistry){
		this.mapDataProviderRegistry=null;
	}
	
	public void startup(){
		mapDataProviderRegistry.registerMapDataUIProvider(this);
	}
	
	@Override
	public List<CreateMapWizardPage> getCreateMapWizardPages() {
		return (List)Collections.singletonList(new SetOSMServerPage());
	}
	@Override
	public Class<? extends MapDataProvider> getProviderClass() {
		return OSMProvider.class;
	}

}
