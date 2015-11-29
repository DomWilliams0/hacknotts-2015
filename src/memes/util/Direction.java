package memes.util;

import java.util.Arrays;
import java.util.function.BinaryOperator;

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

    public Point toPoint() {
        switch (this) {
            case NORTH:
                return new Point(0, -1);
            case NORTH_EAST:
                return new Point(1, -1);
            case EAST:
                return new Point(1, 0);
            case SOUTH_EAST:
                return new Point(1, 1);
            case SOUTH:
                return new Point(0, 1);
            case SOUTH_WEST:
                return new Point(-1, 1);
            case WEST:
                return new Point(-1, 0);
            case NORTH_WEST:
                return new Point(-1, -1);
            default:
                return Point.EMPTY;
        }
    }

    public Direction combine(Direction... direction) {
        return Arrays.stream(direction)
                .map(Direction::toPoint)
                .reduce(Point::add)
                .orElse(Point.EMPTY)
                .toDirection();
    }

    public Direction toRightAngle() {
        return Direction.values()[ordinal() + (ordinal() % 2)];
    }

}
