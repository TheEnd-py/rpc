package pers.cyh.rpc.demo.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class ConsumerTest {
    protected static OutputStream getFunctionalOutputStream() throws FileNotFoundException {
        File file = new File("function.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return fileOutputStream;
    }

    protected static OutputStream getPerformanceOutputStream() throws FileNotFoundException {
        File file = new File("performance.log");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return fileOutputStream;
    }
}
