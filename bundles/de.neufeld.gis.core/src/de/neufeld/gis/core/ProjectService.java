package de.neufeld.gis.core;

import java.util.Set;

public interface ProjectService {

	/**
	 * This returns a unmodifiable set which represents the current project.
	 * @return the unmodifiable {@link Set} of the current maps 
	 */
	Set<GisMap> getMaps();
	/**
	 * Creates a new {@link GisMap} and adds it to the list of maps.
	 * @param mapName
	 * @param dataProvider
	 */
	void createMap(String mapName, MapDataProvider dataProvider);
	GisMap removeMap(String mapName);
}
