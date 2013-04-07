package de.neufeld.gis.core;

import org.eclipse.gef4.graphics.IGraphics;
import org.eclipse.gef4.graphics.image.Image;

public interface MapDataProvider {

	String getName();
	
	Image draw(GisMap gisMap, IGraphics graphics, DrawParameter drawParameter);
	SearchResult search(GisMap gisMap, SearchQuery searchQuery);

}
