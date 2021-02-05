package module4;

import java.util.MissingResourceException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class ClassA extends java.util.logging.Logger {
    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    public ClassA(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        setLevel(Level.ALL);
    }

    private static void configureLogging() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        XMLFormatter formatter = new XMLFormatter();
        consoleHandler.setFormatter(formatter);
        Logger osjL = Logger.getLogger("org.stepic.java");
        osjL.setUseParentHandlers(false);
        osjL.addHandler(consoleHandler);

        Logger classA = Logger.getLogger("org.stepic.java.logging.ClassA");
        classA.setLevel(Level.ALL);

        Logger classB = Logger.getLogger("org.stepic.java.logging.ClassB");
        classB.setLevel(Level.WARNING);
    }



}
