package memes.util;

public class Point {
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
     * @param point Point to mutate (the original is changed)
     * @return The same point, mutated
     */
    public static Point translate(Point point, double x, double y) {
        point.x += x;
        point.y += y;
        return point;
    }

    public void translate(double x, double y) {
        translate(this, x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
