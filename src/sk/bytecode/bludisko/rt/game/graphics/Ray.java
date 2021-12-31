package sk.bytecode.bludisko.rt.game.graphics;

import sk.bytecode.bludisko.rt.game.map.Map;
import sk.bytecode.bludisko.rt.game.math.MathUtils;
import sk.bytecode.bludisko.rt.game.math.Vector2;
import sk.bytecode.bludisko.rt.game.util.Config;

public final class Ray {

    private final Map map;

    private final Vector2 currentPosition;
    private final Vector2 startingPosition;
    private final Vector2 direction;

    private int currentSide = 0;
    private Vector2 hitCoords = new Vector2();

    public Ray(Map map, Vector2 startingPosition, Vector2 direction) {
        this.map = map;
        this.startingPosition = startingPosition;
        this.currentPosition = startingPosition.cpy();
        this.direction = direction;
    }

    public float cast2() {
        int currentTileX = (int) this.startingPosition.x;
        int currentTileY = (int) this.startingPosition.y;

        int tileStepX;
        int tileStepY;

        Vector2 tileLength = new Vector2();
        Vector2 distance = new Vector2();

        var angle = direction.angleRad();
        var tang = Math.tan(angle);
        var tangv2 = direction.y / direction.x;

        //tileLength.x = (float)
        return 0;

    }

    public float cast() {
        int currentTileX = (int) this.startingPosition.x;
        int currentTileY = (int) this.startingPosition.y;

        int tileStepX;
        int tileStepY;

        Vector2 tileLength = new Vector2();
        Vector2 distance = new Vector2();

        // tileLength.x = Math.abs(1 / direction.x);
        // tileLength.y = Math.abs(1 / direction.y);

        tileLength.x = (float) Math.sqrt(1 + (direction.y * direction.y) / (direction.x * direction.x));
        tileLength.y = (float) Math.sqrt(1 + (direction.x * direction.x) / (direction.y * direction.y));

        // First step

        if(direction.x < 0) {
            tileStepX = -1;
            distance.x = (startingPosition.x - currentTileX) * tileLength.x;
        } else {
            tileStepX = 1;
            distance.x = (currentTileX + 1 - startingPosition.x) * tileLength.x;
        }
        if(direction.y < 0) {
            tileStepY = -1;
            distance.y = (startingPosition.y - currentTileY) * tileLength.y;
        } else {
            tileStepY = 1;
            distance.y = (currentTileY + 1 - startingPosition.y) * tileLength.y;
        }

        // Next hit

        boolean hit = false;
        int side = 0;

        while(!hit /*&& distance.len2() < Config.Display.RENDER_DISTANCE_SQ*/) {
            if(distance.x < distance.y) {
                distance.x += tileLength.x;
                currentTileX += tileStepX;
                side = 0;
            } else {
                distance.y += tileLength.y;
                currentTileY += tileStepY;
                side = 1;
            }
            currentPosition.set(startingPosition.cpy().add(distance));

            //if(map.getTile(currentTileX, currentTileY) > 0) {
                //hit = true;
                //hitCoords.set(currentTileX, currentTileY);
            //}

            var block = map.getBlock(currentTileX, currentTileY);
            var hitDistance = block.rayHitDistance(this);

            if(hitDistance >= 0) {
                hit = true;
            }
        }
        distance.sub(tileLength);

        if(!hit) {
            return Float.POSITIVE_INFINITY;
        }
        //return distance.len();
        //if(side == 0) {
            //return distance.x;
        //}
        return distance.y;
        //return Float.POSITIVE_INFINITY;
    }

    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public Vector2 getDirection() {
        return this.direction;
    }

    public int getCurrentSide() {
        return currentSide;
    }

    public Vector2 getHitCoords() {
        return hitCoords;
    }

    public float[] cast(Vector2 position, Vector2 direction, Vector2 plane, int[][] map, int rayCount, int x) {

        // MARK: - Setup

        float screenX = 2f * x / rayCount - 1f;
        // this goes to Camera class, nothing to do with a single ray
        Vector2 rayDir = new Vector2(
                direction.x + plane.x * screenX,     // this goes to Camera class, nothing to do with a single ray
                direction.y + plane.y * screenX
        );

        int mapX = (int)position.x;
        int mapY = (int)position.y;

        Vector2 squareSideDistance = new Vector2();
        Vector2 squareSideDifference = new Vector2();
        squareSideDifference.x = Math.abs(1 / rayDir.x);
        squareSideDifference.y = Math.abs(1 / rayDir.y);

        //Vector2 squareSideDifference = new Vector2(rayDir.len() / rayDir.x, rayDir.len() / rayDir.y);
        //Vector2 nextSqSD = rayDir.cpy().scl(rayDir.len());
        //assert squareSideDifference.equals(nextSqSD);

        Vector2 step = new Vector2();

        boolean hit = false;
        int side = 0;

        // MARK: - First step

        if(rayDir.x < 0f) {
            step.x = -1;
            squareSideDistance.x = (position.x - mapX) * squareSideDifference.x;
        } else {
            step.x = 1;
            squareSideDistance.x = (mapX + 1f - position.x) * squareSideDifference.x;
        }

        if(rayDir.y < 0f) {
            step.y = -1;
            squareSideDistance.y = (position.y - mapY) * squareSideDifference.y;
        } else {
            step.y = 1;
            squareSideDistance.y = (mapY + 1f - position.y) * squareSideDifference.y;
        }

        // MARK: - Next wall hit

        while(!hit) {
            if(squareSideDistance.x < squareSideDistance.y) {
                squareSideDistance.x += squareSideDifference.x;
                mapX += step.x;
                side = 0;

            } else {
                squareSideDistance.y += squareSideDifference.y;
                mapY += step.y;
                side = 1;

            }
            if(map[mapX][mapY] > 0) hit = true;

        }

        // MARK: - Distance

        float perpendicularSideDistance;
        if(side == 0) {
            perpendicularSideDistance = squareSideDistance.x - squareSideDifference.x;
        } else {
            perpendicularSideDistance = squareSideDistance.y - squareSideDifference.y;
        }

        return new float[] {perpendicularSideDistance, side, mapX, mapY};
    }

}