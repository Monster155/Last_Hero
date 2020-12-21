package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GameServer.Utils.TMObjectsUtils;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.MAP_SCALE;

public class Level extends Actor {
    private TiledMapTileLayer layer;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;

    public Level(Batch batch, World world) {
        tiledMap = new TmxMapLoader().load("map/MapForLastHero.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap, MAP_SCALE, batch);
//        layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
//        MapObjects objects = tiledMap.getLayers().get(8).getObjects();
//        TiledObjectUtil.buildBuildingsBodies(tiledMap, world, unitScale, "o_walls");
//        TiledObjectUtil.buildBuildingsBodies(tiledMap, world, unitScale, "o_trees");
        TMObjectsUtils.buildBuildingsBodies(tiledMap, world, MAP_SCALE, "o_walls");
        TMObjectsUtils.buildBuildingsBodies(tiledMap, world, MAP_SCALE, "o_trees");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
//        renderer.renderTileLayer(layer);
//        renderer.render();
        batch.begin();
    }

    @Override
    public void act(float delta) {
        renderer.setView((OrthographicCamera) getStage().getCamera());
    }
}
