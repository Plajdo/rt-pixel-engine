package sk.bytecode.bludisko.rt.game.blocks.technical;

import sk.bytecode.bludisko.rt.game.blocks.Block;
import sk.bytecode.bludisko.rt.game.graphics.*;
import sk.bytecode.bludisko.rt.game.math.MathUtils;
import sk.bytecode.bludisko.rt.game.math.Vector2;

public class TunnelWall extends Block {

    private final Vector2 coordinates;
    private final Side side;

    public TunnelWall(Side side, Vector2 coordinates) {
        this.coordinates = coordinates;
        this.side = side;
    }

    @Override
    public Texture getTexture(float side) {
        return TextureManager.getGenerated(7);
    }

    @Override
    public float getHeight() {
        return 2;
    }

    @Override
    public boolean hasPriority() {
        return true;
    }

    @Override
    public Vector2 getCoordinates() {
        return coordinates;
    }

    @Override
    public RayAction hitAction(Ray ray) {
        var position = MathUtils.decimalPart(ray.getPosition());

        if(this.side == Side.EASTWEST && position.x == 0) {
            return RayAction.ADD;
        } else if(this.side == Side.NORTHSOUTH && position.y == 0) {
            return RayAction.ADD;
        }
        return RayAction.SKIP;
    }

}