package de.neufeld.gis.provider.osm;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import javax.imageio.ImageIO;

import org.eclipse.gef4.geometry.planar.Rectangle;
import org.eclipse.gef4.graphics.IGraphics;
import org.eclipse.gef4.graphics.image.Image;
import org.osgi.service.log.LogService;

import de.neufeld.gis.core.DrawParameter;
import de.neufeld.gis.core.GisMap;
import de.neufeld.gis.core.MapDataProvider;
import de.neufeld.gis.core.MapDataProviderRegistry;
import de.neufeld.gis.core.SearchQuery;
import de.neufeld.gis.core.SearchResult;

public class OSMProvider implements MapDataProvider {

	public static final String NAME = "OSM_Provider";
	private final double TILE_SIZE = 256;
	private final double M_PER_PIXEL_ZOOM_0 = 156543.034;
	private final double M_PER_TILE_ZOOM_0 = M_PER_PIXEL_ZOOM_0 * TILE_SIZE;

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
	public Image draw(GisMap gisMap, IGraphics graphics,
			DrawParameter drawParameter) {

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		return forkJoinPool.invoke(new ImageTask(gisMap, drawParameter,
				graphics));
	}

	private class ImageTask extends RecursiveTask<Image> {
		private DrawParameter drawParameter;
		private IGraphics graphics;
		private GisMap gisMap;

		private ImageTask(GisMap gisMap, DrawParameter drawParameter,
				IGraphics graphics) {
			this.drawParameter = drawParameter;
			this.graphics = graphics;
			this.gisMap = gisMap;
		}

		@Override
		protected Image compute() {
			Rectangle visibleArea = drawParameter.getVisibleArea().getBounds();
			double numTiles = Math.pow(2, drawParameter.getZoomLevel());
			double tileWidth = M_PER_TILE_ZOOM_0 / numTiles;
			double currentX = visibleArea.getX() / tileWidth;
			double currentY = visibleArea.getY() / tileWidth;
			int tilesX = (int) Math.floor(currentX);
			int tilesY = (int) Math.floor(currentY);

			double offSetX = (tilesX - currentX) * TILE_SIZE;
			double offSetY = (tilesY - currentY) * TILE_SIZE;

			int numTilesX = (int) Math.ceil(visibleArea.getWidth() / tileWidth);
			int numTilesY = (int) Math
					.ceil(visibleArea.getHeight() / tileWidth);

			List<RecursiveTask<ImageOffsetPair>> forks = new LinkedList<>();

			for (int i = 0; i <= numTilesX; i++) {
				for (int j = 0; j <= numTilesY; j++) {
					ImageRetrieveTask task = new ImageRetrieveTask(
							(String) gisMap.getProviderSpecificData(),
							(int) drawParameter.getZoomLevel(), offSetX,
							offSetY, tilesX, tilesY, i, j);
					forks.add(task);
					task.fork();
				}
			}
			for (RecursiveTask<ImageOffsetPair> task : forks) {
				ImageOffsetPair pair = task.join();
				graphics.pushState().translate(pair.offsetX, pair.offsetY);

				graphics.paint(pair.image);
				graphics.popState();
			}

			return null;
		}

	}

	private class ImageRetrieveTask extends RecursiveTask<ImageOffsetPair> {
		final int zoomLevel;
		final double offsetX;
		final double offsetY;
		final int tileX;
		final int tileY;
		final int moveX;
		final int moveY;
		private String url;

		public ImageRetrieveTask(String url, int zoomLevel, double offsetX,
				double offsetY, int tileX, int tileY, int moveX, int moveY) {
			this.zoomLevel = zoomLevel;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			this.tileX = tileX;
			this.tileY = tileY;
			this.moveX = moveX;
			this.moveY = moveY;
			this.url = url;
		}

		@Override
		protected ImageOffsetPair compute() {

			ImageOffsetPair result = new ImageOffsetPair();
			try {
				if (url.endsWith("/"))
					url = url.substring(0, url.length() - 2);
				String httpUrl = url + "/" + zoomLevel + "/" + (tileX + moveX)
						+ "/" + (tileY + moveY) + ".png";
				URL imageUrl = new URL(httpUrl);
				Image image = new Image(ImageIO.read(imageUrl));
				result.image = image;
				result.offsetX = offsetX + moveX * TILE_SIZE;
				result.offsetY = offsetY + moveY * TILE_SIZE;
			} catch (IOException e) {
				logService.log(LogService.LOG_ERROR, e.getMessage(), e);
			}
			return result;
		}

	}

	private class ImageOffsetPair {
		Image image;
		double offsetX;
		double offsetY;
	}

	@Override
	public SearchResult search(GisMap gisMap, SearchQuery searchQuery) {
		return null;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
