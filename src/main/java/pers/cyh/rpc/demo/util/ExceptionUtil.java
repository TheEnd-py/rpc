package pers.cyh.rpc.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable) {
        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);

        throwable.printStackTrace(out);
        out.flush();

        return buffer.toString();
    }
}
