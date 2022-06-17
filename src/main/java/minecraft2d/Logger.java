package minecraft2d;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

/**
 * The Logger class exists to provide more detailed print statements by prepending time and class name
 * @author Zutki
 * @version 0.0.1-ALPHA
 */
public class Logger extends PrintStream {

    private final PrintStream CONSOLE_STREAM = System.out;

    public Logger(OutputStream out) {
        super(out);
    }

    public String getTime() {
        LocalTime currentTime = java.time.LocalTime.now();
        return String.format("%d:%d:%d",
                currentTime.getHour(),
                currentTime.getMinute(),
                currentTime.getSecond());
    }
    private String logPrepend() {
        // get tbe class name by getting the highest element in the stack trace
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[stackTrace.length-1].getClassName();

        return String.format("[%s %s]: ", getTime(), className);
    }

    public PrintStream getDefaultStream() {
        return CONSOLE_STREAM;
    }

    public void println() {
        CONSOLE_STREAM.println(logPrepend());
    }
    public void println(String v) { CONSOLE_STREAM.println(logPrepend() + v); }
    public void println(boolean v) { CONSOLE_STREAM.println(logPrepend() + v); }
    public void println(int v) { CONSOLE_STREAM.println(logPrepend() + v); }
    public void println(float v) { CONSOLE_STREAM.println(logPrepend() + v); }
    public void println(double v) { CONSOLE_STREAM.println(logPrepend() + v); }
    public void println(long v) { CONSOLE_STREAM.println(logPrepend() + v); }
}
