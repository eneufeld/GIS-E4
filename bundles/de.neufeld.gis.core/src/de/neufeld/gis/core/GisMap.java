package de.neufeld.gis.core;

import org.eclipse.gef4.graphics.IGraphics;

public final class GisMap {

	private final MapDataProvider mapDataProvider;
	private String name;
	private final Object dataProviderSpecific;

	public GisMap(String name, MapDataProvider mapDataProvider,Object dataProviderSpecific){
		this.mapDataProvider=mapDataProvider;
		this.name=name;
		this.dataProviderSpecific = dataProviderSpecific;
	}
	
	public String getName(){
		return name;
	}
	public Object getDataProviderSpecific() {
		return dataProviderSpecific;
	}

	@Override
	public String toString(){
		return name;
	}
	public void draw(IGraphics graphics,DrawParameter drawParameter) {
		mapDataProvider.draw(this,graphics,drawParameter);
	}

	public void search(SearchQuery searchQuery) {
		mapDataProvider.search(this,searchQuery);
	}
}
