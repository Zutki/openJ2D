package minecraft2d;

// TODO:
// FIX THIS

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

/**
 * The Logger class exists to provide more detailed print statements by prepending time and class name
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class Logger extends PrintStream {

    PrintStream consoleStream;

    public Logger(OutputStream out, PrintStream consoleStream) {
        super(out);
        this.consoleStream = consoleStream;
    }

    public String getTime() {
        LocalTime currentTime = java.time.LocalTime.now();
        return String.format("%d:%d:%d",
                currentTime.getHour(),
                currentTime.getMinute(),
                currentTime.getSecond());
    }
    private String logPrepend() {
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        return String.format("[%s %s]: ", getTime(), className);
    }

    public void println() {
        consoleStream.println(logPrepend());
    }
    public void println(Object v) {
        consoleStream.println(logPrepend() + v);
    }
}
