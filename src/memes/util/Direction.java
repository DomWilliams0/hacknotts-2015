package memes.util;

public enum Direction {

    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public int getID() {
        return ordinal();
    }

    public Point translate(Point from, float amount) {
        float dx = 0, dy = 0;
        switch (this) {
            case NORTH:
                dy = -1;
                break;
            case NORTH_EAST:
                dx = 1;
                dy = -1;
                break;
            case EAST:
                dx = 1;
                break;
            case SOUTH_EAST:
                dx = 1;
                dy = 1;
                break;
            case SOUTH:
                dy = 1;
                break;
            case SOUTH_WEST:
                dx = -1;
                dy = 1;
                break;
            case WEST:
                dx = -1;
                break;
            case NORTH_WEST:
                dx = -1;
                dy = -1;
                break;
        }

        dx *= amount;
        dy *= amount;

        Point ret = new Point(from);
        ret.translate(dx, dy);
        return ret;
    }


    public Direction combine(Direction... direction) {
        // todo fuck me
        return this;
    }

    public Direction toRightAngle() {
        return Direction.values()[ordinal() + (ordinal() % 2)];
    }

}
