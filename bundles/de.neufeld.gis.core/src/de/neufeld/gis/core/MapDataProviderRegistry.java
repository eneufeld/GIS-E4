package de.neufeld.gis.core;

import java.util.Set;

public interface MapDataProviderRegistry {

	void registerMapDataProvider(MapDataProvider mapDataProvider);
	void unregisterMapDataProvider(MapDataProvider mapDataProvider);
	
	Set<MapDataProvider> getProviders();
}
