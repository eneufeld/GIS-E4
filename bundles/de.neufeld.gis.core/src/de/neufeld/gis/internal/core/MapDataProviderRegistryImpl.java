package de.neufeld.gis.internal.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.core.MapDataUIProvider;

public class MapDataProviderRegistryImpl implements MapDataProviderRegistry {

	private Map<String,MapDataProvider> dataProviders=new HashMap<>();
	private Map<Class<? extends MapDataProvider>,MapDataUIProvider> uiDataProviders=new HashMap<>();
	
	@Override
	public void registerMapDataProvider(MapDataProvider mapDataProvider) {
		dataProviders.put(mapDataProvider.getName(),mapDataProvider);
	}

	@Override
	public void unregisterMapDataProvider(MapDataProvider mapDataProvider) {
		dataProviders.remove(mapDataProvider.getName());
	}

	@Override
	public Collection<MapDataProvider> getProviders() {
		return Collections.unmodifiableCollection(dataProviders.values());
	}

	@Override
	public MapDataProvider getProvider(String providerName) {
		return dataProviders.get(providerName);
	}

	@Override
	public void registerMapDataUIProvider(MapDataUIProvider mapDataUIProvider) {
		uiDataProviders.put(mapDataUIProvider.getProviderClass(), mapDataUIProvider);
	}

	@Override
	public void unregisterMapDataUIProvider(MapDataUIProvider mapDataUIProvider) {
		uiDataProviders.remove(mapDataUIProvider.getProviderClass());
	}

	@Override
	public MapDataUIProvider getUIProvider(MapDataProvider mapDataProvider) {
		return uiDataProviders.get(mapDataProvider.getClass());
	}

}
