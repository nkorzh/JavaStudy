package module3;

/**
 * Robot class for testing the moveRobot function.
 */
public class Robot {
        private int x = 0;
        private int y = 0;

        private Direction direction = Direction.UP;

        public void moveRobot(int x, int y) {}

        public Robot(int x, int y, Direction newDir) {
            this.x = x;
            this.y = y;
            direction = newDir;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void turnLeft() {
            direction = switch (direction) {
                case UP -> Direction.LEFT;
                case LEFT -> Direction.DOWN;
                case DOWN -> Direction.RIGHT;
                case RIGHT -> Direction.UP;
            };
        }

        public void turnRight() {
            direction = switch (direction) {
                case DOWN -> Direction.LEFT;
                case RIGHT -> Direction.DOWN;
                case UP -> Direction.RIGHT;
                case LEFT -> Direction.UP;
            };
        }

        public void stepForward() {
            Vec2 lookDir = new Vec2(getDirection());
            x += lookDir.getX();
            y += lookDir.getY();
        }
}
