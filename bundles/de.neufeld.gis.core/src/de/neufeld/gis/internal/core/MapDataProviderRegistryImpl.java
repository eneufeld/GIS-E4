package de.neufeld.gis.internal.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;

public class MapDataProviderRegistryImpl implements MapDataProviderRegistry {

	private Set<MapDataProvider> dataProviders=new HashSet<>();
	
	@Override
	public void registerMapDataProvider(MapDataProvider mapDataProvider) {
		dataProviders.add(mapDataProvider);
	}

	@Override
	public void unregisterMapDataProvider(MapDataProvider mapDataProvider) {
		dataProviders.remove(mapDataProvider);
	}

	@Override
	public Set<MapDataProvider> getProviders() {
		return Collections.unmodifiableSet(dataProviders);
	}

}
