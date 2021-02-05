package module3;

public class Vec2 {
    private int x = 0;
    private int y = 0;

    public Vec2(int x, int y) {
        update(x, y);
    }

    public Vec2(Direction dir) {
        update(dir);
    }

    public Vec2() {}

    public void update(Direction dir) {
        switch (dir) {
            case UP -> update(0,1);
            case RIGHT -> update(1, 0);
            case DOWN -> update(0, -1);
            case LEFT -> update(-1, 0);
        }
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getLen() {
        return Math.sqrt(x * x + y * y);
    }

    public double dotVec(Vec2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public double cosBetween(Vec2 v) {
        return dotVec(v) / (getLen() * v.getLen());
    }

    public double sinBetween(Vec2 v) {
        return (x * v.getY() - y * v.getX()) / (getLen() * v.getLen());
    }
}
