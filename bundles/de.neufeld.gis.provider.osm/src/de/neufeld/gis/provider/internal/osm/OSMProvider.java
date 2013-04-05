package de.neufeld.gis.provider.internal.osm;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.eclipse.gef4.geometry.planar.Point;
import org.eclipse.gef4.geometry.planar.Polygon;
import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.gef4.graphics.IGraphics;
import org.eclipse.gef4.graphics.image.Image;
import org.eclipse.gef4.graphics.image.operations.ImageOperations;
import org.osgi.service.log.LogService;

import de.neufeld.gis.core.DrawParameter;
import de.neufeld.gis.core.GisMap;
import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.core.SearchQuery;

public class OSMProvider implements MapDataProvider {
	private String url = "http://tile.openstreetmap.org/$path$.png";
	private final double TILE_SIZE=256;
	private final double M_PER_PIXEL_ZOOM_0=156543.034;
	private final double M_PER_TILE_ZOOM_0=M_PER_PIXEL_ZOOM_0*TILE_SIZE;
	
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
		Point drawSize=drawParameter.getDrawSize();
		Rectangle visibleArea=drawParameter.getVisibleArea().getBounds();
		double numTiles=Math.pow(2, drawParameter.getZoomLevel());
		double tileWidth=M_PER_TILE_ZOOM_0/numTiles;
		double currentX=visibleArea.getX()/tileWidth;
		double currentY=visibleArea.getY()/tileWidth;
		int tilesX=(int)Math.floor(currentX);
		int tilesY=(int)Math.floor(currentY);
		
		double offSetX=(tilesX-currentX)*TILE_SIZE;
		double offSetY=(tilesY-currentY)*TILE_SIZE;
		
		int numTilesX=(int)Math.ceil(visibleArea.getWidth()/tileWidth);
		int numTilesY=(int)Math.ceil(visibleArea.getHeight()/tileWidth);
		for(int i=0;i<=numTilesX;i++){
			for(int j=0;j<=numTilesY;j++){
				String httpUrl=url.replace("$path$", (int)drawParameter.getZoomLevel()+"/"+(tilesX+i)+"/"+(tilesY+j));
				try {
					URL imageUrl = new URL(httpUrl);
					Image image = new Image(ImageIO.read(imageUrl));
					graphics.pushState().translate(offSetX+i*TILE_SIZE, offSetY+j*TILE_SIZE);
					graphics.paint(image);
					graphics.popState();
				} catch (IOException e) {
					logService.log(LogService.LOG_ERROR, e.getMessage(), e);
				}
			}
		}
		
	}

	
	
	@Override
	public void search(GisMap gisMap, SearchQuery searchQuery) {
		// TODO Auto-generated method stub

	}
}
