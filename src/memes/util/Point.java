package memes.util;

import java.io.Serializable;

public class Point implements Serializable {

    public static final Point EMPTY = new Point(0, 0);

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public double getX() {
        return x;
    }

    public int getIntX() {
        return (int) x;
    }

    public double getY() {
        return y;
    }

    public int getIntY() {
        return (int) y;
    }

    /**
     * @return This, but mutated
     */
    public Point translate(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * @return This, but mutated
     */
    public Point translate(Direction d, float distance) {
        this.add(d.toPoint().multiply(distance));
        return this;
    }

    /**
     * @return This, but mutated
     */
    public Point add(Point p) {
        translate(p.x, p.y);
        return this;
    }

    /**
     * @return This, but mutated
     */
    public Point multiply(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public Direction toDirection() {
        if (x != 0)
            switch ((int) Math.signum(x)) {
                case -1:
                    return Direction.WEST;
                case 1:
                    return Direction.EAST;
                default:
                    return null;
            }
        else if (y != 0)
            switch ((int) Math.signum(y)) {
                case -1:
                    return Direction.NORTH;
                case 1:
                    return Direction.SOUTH;
                default:
                    return null;
            }
        return null;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
