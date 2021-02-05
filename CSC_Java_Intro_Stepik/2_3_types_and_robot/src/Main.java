import module3.*;
import module4.*;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import static module4.TestStuff.moveRobot;


public class Main {
    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        XMLFormatter formatter = new XMLFormatter();
        consoleHandler.setFormatter(formatter);
        java.util.logging.Logger osjL = Logger.getLogger("org.stepic.java");
        osjL.setUseParentHandlers(false);
        osjL.addHandler(consoleHandler);

        ClassA classA = new ClassA("classALogger", null);
        classA.setLevel(Level.ALL);
        
        ClassB classB = new ClassB("classBLogger", null);
        classB.setLevel(Level.WARNING);

        //        moveRobot(new TestStuff.RobotConnectionMan(false), 1, 2);
    }

}
