package sk.bytecode.bludisko.rt.game.blocks.technical;

import sk.bytecode.bludisko.rt.game.blocks.Block;
import sk.bytecode.bludisko.rt.game.graphics.RayAction;
import sk.bytecode.bludisko.rt.game.graphics.Ray;
import sk.bytecode.bludisko.rt.game.graphics.Texture;
import sk.bytecode.bludisko.rt.game.graphics.TextureManager;
import sk.bytecode.bludisko.rt.game.math.MathUtils;
import sk.bytecode.bludisko.rt.game.math.Vector2;

public class HalfWall extends Block {

    private final Vector2 coordinates;

    public HalfWall(Vector2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Texture getTexture(float side) {
        return TextureManager.getGenerated(5);
    }

    @Override
    public float getHeight() {
        return 2f;
    }

    @Override
    public boolean hasPriority() {
        return false;
    }

    @Override
    public Vector2 getCoordinates() {
        return coordinates;
    }

    public RayAction hitAction(Ray ray) {
        var angle = ray.getDirection().angleRad();
        var distance = Math.abs((float)(0.5d / Math.sin(angle)));

        ray.setTileSize(new Vector2(0.5f, 0.5f));
        var position = ray.getPosition();

        if((position.y % 1) - 0.5f < MathUtils.FLOAT_ROUNDING_ERROR && (position.y % 1) - 0.5f >= 0) {
            ray.setTileSize(new Vector2(1f, 1f));
            return RayAction.ADD;
        }

        return RayAction.SKIP;
    }

}