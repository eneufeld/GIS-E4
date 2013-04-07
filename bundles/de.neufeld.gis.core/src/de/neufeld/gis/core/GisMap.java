package de.neufeld.gis.core;

import java.io.Serializable;

import org.eclipse.gef4.graphics.IGraphics;
import org.eclipse.gef4.graphics.image.Image;

public final class GisMap implements Serializable{

	/**
	 * SerializationID
	 */
	private static final long serialVersionUID = -128040233302892707L;
	private final MapDataProvider mapDataProvider;
	private String name;
	private final Serializable providerSpecificData;

	public GisMap(String name, MapDataProvider mapDataProvider,Serializable providerSpecificData){
		this.mapDataProvider=mapDataProvider;
		this.name=name;
		this.providerSpecificData = providerSpecificData;
	}
	
	public String getName(){
		return name;
	}
	public Serializable getProviderSpecificData() {
		return providerSpecificData;
	}

	@Override
	public String toString(){
		return name;
	}
	public Image draw(IGraphics graphics,DrawParameter drawParameter) {
		return mapDataProvider.draw(this,graphics,drawParameter);
	}

	public SearchResult search(SearchQuery searchQuery) {
		return mapDataProvider.search(this,searchQuery);
	}
}
