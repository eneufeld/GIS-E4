package de.neufeld.gis.internal.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.neufeld.gis.core.GisMap;
import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.ProjectService;

public class ProjectServiceImpl implements ProjectService {

	private Map<String, GisMap> maps=new LinkedHashMap<>();
	
	@Override
	public Set<GisMap> getMaps() {
		Set<GisMap> result=new LinkedHashSet<>(maps.size());
		for(GisMap map:maps.values()){
			result.add(map);
		}
		return Collections.unmodifiableSet(result);
	}

	@Override
	public void createMap(String mapName, MapDataProvider dataProvider,Serializable providerSpecificData) {
		if(maps.containsKey(mapName)){
			throw new IllegalArgumentException("A Map with the name "+mapName+ " already exists.");
		}
		GisMap map=new GisMap(mapName,dataProvider,providerSpecificData);
		maps.put(mapName, map);
	}

	@Override
	public GisMap removeMap(String mapName) {
		return maps.get(mapName);
	}

}
