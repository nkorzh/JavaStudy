package module4;

import module3.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestStuff {

    public static class RobotConnectionMan implements RobotConnectionManager {
        private final boolean flag;

        public RobotConnectionMan(boolean flag) {
            this.flag = flag;
        }

        @Override
        public RobotConnection getConnection() {
            if (flag)
                throw new RobotConnectionException("Connection exception.");
            else
                System.out.println("Connection established");
            return new RobotConnectionCantMove();
        }
    }
    public static class RobotConnectionCantMove implements RobotConnection {
        @Override
        public void moveRobotTo(int x, int y) {
            System.out.println("Trying to move robot, success");
        }

        @Override
        public void close() {
            System.out.println("Closed " + this.getClass().getName());
            throw new RobotConnectionException("Close error, SHOULD BE IGNORED");
        }
    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        int attemptsAmount = 0;
        boolean movedSuccessfully = false;
        while (!movedSuccessfully && attemptsAmount < 3) {
            try (RobotConnection connection = robotConnectionManager.getConnection()) {
                connection.moveRobotTo(toX, toY);
                movedSuccessfully = true;
            } catch (RobotConnectionException ignored) {}
            attemptsAmount++;
        }
        if (!movedSuccessfully)
            throw new RobotConnectionException("");
    }


    public static void moveRobot(Robot robot, int toX, int toY) {
        Vec2 robotDir = new Vec2();
        Vec2 pathDir = new Vec2();

        while (robot.getX() != toX || robot.getY() != toY) {
            robotDir.update(robot.getDirection());
            pathDir.update(toX - robot.getX(), toY - robot.getY());
            double cos = robotDir.cosBetween(pathDir);
            double sin = robotDir.sinBetween(pathDir);

            while (cos <= 0) {
                if (sin >= 0)
                    robot.turnLeft();
                else
                    robot.turnRight();
                robotDir.update(robot.getDirection());
                cos = robotDir.cosBetween(pathDir);
                sin = robotDir.sinBetween(pathDir);
            }
            robot.stepForward();
        }
    }

    public static class MyPair {
        public int index;
        public String text;
        MyPair(int ind, String text) {
            this.index = ind;
            this.text = text;
        }
    }
    /**
     *
     * @param roles of characters
     * @param textLines text, divided by '\n'
     * @return text, grouped by roles
     */
    private static String printTextPerRole(String[] roles, String[] textLines) {
        ArrayList<ArrayList<MyPair>> roleText = new ArrayList<>(roles.length);

        for (int i = 0; i < roles.length; i++) {
            roleText.add(new ArrayList<MyPair>());
        }

        for (int textInd = 0; textInd < textLines.length; textInd++) {
            for (int roleInd = 0; roleInd < roles.length; roleInd++) {
                String roleName = roles[roleInd].concat(": ");
                if (textLines[textInd].startsWith(roleName)) {
                    roleText.get(roleInd).add(new MyPair(textInd + 1, textLines[textInd].substring(roleName.length())));
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int role = 0; role < roles.length; role++) {
            result.append(roles[role].concat(":\n"));
            for (MyPair linePair : roleText.get(role)) {
                result.append(Integer.toString(linePair.index).concat(") "));
                result.append(linePair.text.concat("\n"));
            }
            result.append("\n");
        }

        return result.toString();
    }

    /**
     * Merges two given sorted arrays into one
     *
     * @param a1 first sorted array
     * @param a2 second sorted array
     * @return new array containing all elements from a1 and a2, sorted
     */
    public static int[] mergeArrays(int[] a1, int[] a2) {
        int i = 0, j = 0;
        int[] res = new int[a1.length + a2.length];

        while (i + j < res.length) {
            if (i < a1.length) {
                if (j < a2.length)
                    res[i + j] = a1[i] <= a2[j] ? a1[i++] : a2[j++];
                else
                    res[i + j] = a1[i++];
            } else
                res[i + j] = a2[j++];
        }
        return res;
    }

    /**
     * Calculates factorial of given <code>value</code>.
     *
     * @param value positive number
     * @return factorial of <code>value</code>
     */
    public static BigInteger factorial(int value) {
        if (value == 1)
            return BigInteger.ONE;
        else
            return BigInteger.valueOf(value).multiply(factorial(value - 1));
    }
    /**
     * Checks if given <code>text</code> is a palindrome.
     *
     * @param text any string
     * @return <code>true</code> when <code>text</code> is a palindrome, <code>false</code> otherwise
     */
    public static boolean isPalindrome(String text) {
        Pattern p = Pattern.compile( "[^a-zA-Z0-9]");
        Matcher m = p.matcher(text);

        String clearText = m.replaceAll("");
        int halfLength = clearText.length() / 2;
        String firstHalf = clearText.substring(0, halfLength);
        String secondHalf = clearText.substring(clearText.length() % 2 == 0 ? halfLength : halfLength + 1);

        return firstHalf.equalsIgnoreCase(new StringBuilder(secondHalf).reverse().toString());
    }

    public static String stringTester() {
        int[] ar = new int[0];
        return Integer.toString(ar.length);
    }

    /**
     * Checks if given <code>value</code> is a power of two.
     *
     * @param value any number
     * @return <code>true</code> when <code>value</code> is power of two, <code>false</code> otherwise
     */
    public static boolean isPowerOfTwo(int value) {
        int abs_v = Math.abs(value);
        return value != 0 && (abs_v & (abs_v - 1)) == 0;
    }

    public static boolean booleanExpression(boolean a, boolean b, boolean c, boolean d) {
        return
                (!a && !b && c && d) ||
                        (!a && b && !c && d) ||
                        (!a && b && c && !d) ||
                        (a && !b && !c && d) ||
                        (a && !b && c && !d) ||
                        (a && b && !c && !d);
    }

    public static int leapYearCount(int year) {
        int fh_years = year / 400;
        int f_years = year / 4 - year / 100;

        return fh_years + f_years;
    }

    public static boolean doubleExpression(double a, double b, double c) {
        return doubleEqual(a + b, c, 1E-4);
    }

    public static boolean doubleEqual(double a, double b, double threshold) {
        return Math.abs(a - b) < Math.abs(threshold);
    }

}
