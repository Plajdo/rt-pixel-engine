package sk.bytecode.bludisko.rt.game.blocks.game;

import sk.bytecode.bludisko.rt.game.blocks.Block;
import sk.bytecode.bludisko.rt.game.graphics.*;
import sk.bytecode.bludisko.rt.game.math.MathUtils;
import sk.bytecode.bludisko.rt.game.math.Vector2;

public class Portal extends Block {

    public enum Color {
        BLUE,
        ORANGE
    }

    private final Vector2 coordinates;
    private final Side side;
    private final Portal.Color color;
    private Portal otherPortal;

    /**
     * Constructs a new portal.
     * @param side Side of the portal entrance/exit
     * @param color {@link Portal.Color Color} of the portal frame (aesthetic only)
     * @param coordinates Coordinates of this portal
     */
    public Portal(Side side, Portal.Color color, Vector2 coordinates) {
        this.coordinates = coordinates;
        this.color = color;
        this.side = side;
    }

    @Override
    public Texture getTexture(Side side) {
        if(side == this.side) {
            if(this.color == Color.ORANGE) {
                return TextureManager.getTexture(5);
            } else {
                return TextureManager.getTexture(4);
            }
        }
        return TextureManager.getTexture(2);
    }

    @Override
    public float getHeight() {
        return 4f;
    }

    @Override
    public Vector2 getCoordinates() {
        return coordinates;
    }

    @Override
    public RayAction hitAction(Ray ray) {
        var rayPosition = ray.getPosition();
        var hitSide = getSide(rayPosition);
        if(hitSide == side && otherPortal != null) {
            var thisExit = this.getExitRotation();
            var otherExit = otherPortal.getExitRotation();
            var exitRotation = thisExit.angleRad() - otherExit.angleRad();

            rayPosition.set(MathUtils.decimalPart(rayPosition));
            rayPosition.rotateRad(exitRotation);
            rayPosition.add(otherPortal.getCoordinates());
            rayPosition.add(otherPortal.getExitOffset());

            ray.updateDirection(ray.getDirection().cpy().rotateRad(exitRotation));

            return RayAction.TELEPORT;
        } else {
            return RayAction.STOP;
        }
    }

    private Vector2 getExitOffset() {
        return switch(side) {
            case NONE -> null;
            case NORTH -> null;
            case EAST -> new Vector2(0f, 1f);
            case SOUTH -> new Vector2(1f, 0f);
            case WEST -> null;
        };
    }

    private Vector2 getExitRotation() {
        return switch(side) {
            case NONE -> null;
            case NORTH -> null;
            case EAST -> new Vector2(0f, 1f);
            case SOUTH -> new Vector2(1f, 0f);
            case WEST -> null;
        };
    }

    /**
     * Link two portals together.
     * @param otherPortal Paired portal
     */
    public void setOtherPortal(Portal otherPortal) {
        this.otherPortal = otherPortal;
    }
}
