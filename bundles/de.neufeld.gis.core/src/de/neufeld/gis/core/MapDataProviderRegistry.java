package de.neufeld.gis.core;

import java.util.Collection;

public interface MapDataProviderRegistry {

	void registerMapDataProvider(MapDataProvider mapDataProvider);
	void unregisterMapDataProvider(MapDataProvider mapDataProvider);
	void registerMapDataUIProvider(MapDataUIProvider mapDataUIProvider);
	void unregisterMapDataUIProvider(MapDataUIProvider mapDataUIProvider);
	
	Collection<MapDataProvider> getProviders();
	
	MapDataProvider getProvider(String providerName);
	MapDataUIProvider getUIProvider(MapDataProvider mapDataProvider);
}
