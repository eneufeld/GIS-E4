package de.neufeld.gis.core;

import org.eclipse.gef4.graphics.IGraphics;

public interface MapDataProvider {

	void draw(GisMap gisMap, IGraphics graphics, DrawParameter drawParameter);

	void search(GisMap gisMap, SearchQuery searchQuery);	

}
