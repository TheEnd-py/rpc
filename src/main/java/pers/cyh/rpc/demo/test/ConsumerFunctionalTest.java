package pers.cyh.rpc.demo.test;

import pers.cyh.rpc.demo.builder.ConsumerBuilder;
import pers.cyh.rpc.demo.util.ExceptionUtil;

import java.io.*;
import java.lang.reflect.Method;


public class ConsumerFunctionalTest extends ConsumerTest{
    public static void main(String[] args) {
        try {
            OutputStream outputStream = getFunctionalOutputStream();
            ConsumerBuilder consumerBuilder = new ConsumerBuilder();
            Method[] methods = consumerBuilder.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("test")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(method.getName()).append(":");
                    try {
                        method.invoke(consumerBuilder, null);
                        sb.append("pass").append("\r\n");
                    } catch (Exception e) {
                        sb.append(ExceptionUtil.getStackTrace(e)).append("\r\n");
                    }
                    outputStream.write(sb.toString().getBytes());
                }
            }
            outputStream.close();
        }catch (Exception e){
           //Do Nothing
        }

        // some thread may be not daemon
        System.exit(1);
    }
}
