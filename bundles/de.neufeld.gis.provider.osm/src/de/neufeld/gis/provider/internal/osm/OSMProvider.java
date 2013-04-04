package de.neufeld.gis.provider.internal.osm;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.eclipse.gef4.graphics.IGraphics;
import org.eclipse.gef4.graphics.image.Image;
import org.osgi.service.log.LogService;

import de.neufeld.gis.core.DrawParameter;
import de.neufeld.gis.core.GisMap;
import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.core.SearchQuery;

public class OSMProvider implements MapDataProvider {

	private String url = "http://tile.openstreetmap.org/0/0/0.png";
	private LogService logService;
	private MapDataProviderRegistry mapDataProviderRegistry;

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public void unsetLogService(LogService logService) {
		this.logService = null;
	}

	public void setMapDataProviderRegistry(
			MapDataProviderRegistry mapDataProviderRegistry) {
		this.mapDataProviderRegistry = mapDataProviderRegistry;
	}

	public void unsetMapDataProviderRegistry(
			MapDataProviderRegistry mapDataProviderRegistry) {
		this.mapDataProviderRegistry = null;
	}

	public void startup() {
		mapDataProviderRegistry.registerMapDataProvider(this);
	}

	public void shutdown() {
		mapDataProviderRegistry.unregisterMapDataProvider(this);
	}

	@Override
	public void draw(GisMap gisMap, IGraphics graphics,
			DrawParameter drawParameter) {

		try {
			URL imageUrl = new URL(url);
			Image image = new Image(ImageIO.read(imageUrl));
			graphics.paint(image);
		} catch (IOException e) {
			logService.log(LogService.LOG_ERROR, e.getMessage(), e);
		}
	}

	@Override
	public void search(GisMap gisMap, SearchQuery searchQuery) {
		// TODO Auto-generated method stub

	}
}
