package pers.cyh.rpc.demo.builder;

import pers.cyh.rpc.api.RpcConsumer;
import pers.cyh.rpc.async.ResponseFuture;
import pers.cyh.rpc.context.RpcContext;
import pers.cyh.rpc.demo.service.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;


public class ConsumerBuilder {
    private static RpcConsumer consumer;
    private static DemoTestService apiService;

    static {
        try {
            consumer = (RpcConsumer) getConsumerImplClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if(consumer == null){
            System.out.println("Start rpc consumer failed");
            System.exit(1);
        }
        apiService = (DemoTestService) consumer
                .interfaceClass(DemoTestService.class)
                .version("1.0.0.api")
                .clientTimeout(3000)
                .hook(new DemoConsumerHook()).instance();

    }

    public boolean pressureTest() {
        try {
            DemoDO result = apiService.getDO();
            if (result == null)
                return false;
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    @Test
    public void testNormalApiCall() {
        Assert.assertNotNull(apiService.getMap());
        Assert.assertEquals("this is a rpc framework", apiService.getString());
        Assert.assertEquals(new DemoDO(), apiService.getDO());
    }

    @Test
    public void testNormalSpringCall() {
        Assert.assertNotNull(apiService.getMap());
        Assert.assertEquals("this is a rpc framework", apiService.getString());
        Assert.assertEquals(new DemoDO(), apiService.getDO());
    }

    /**
     * test timeout property,at init this service,we set the client timeout 3000ms
     * so we should break up before Provider finish running(Provider will sleep 5000ms beyond clientTimeout)
     */
    @Test
    public void testTimeoutCall() {
        long beginTime = System.currentTimeMillis();
        try {
            boolean result = apiService.longTimeMethod();
        } catch (Exception e) {
            long period = System.currentTimeMillis() - beginTime;
            Assert.assertTrue(period < 3100);
        }
    }

    /**
     * when provider throws an exception when process the request,
     * the rpc framework must pass the exception to the consumer
     */
    @Test
    public void testCatchException() {
        try {
            Integer result = apiService.throwException();
            Assert.fail();
        } catch (DemoException e) {
            Assert.assertEquals("demo", e.getFlag());
            e.printStackTrace();
        }
    }

    /**
     * set the method {@code getString} of {@link DemoTestService} asynchronous
     * and get response with the tool {@link ResponseFuture} based on ThreadLocal
     */
    @Test
    public void testFutureCall() {
        consumer.asynCall("getString");
        String nullValue = apiService.getString();
        Assert.assertEquals(null, nullValue);

        try {
            String result = (String) ResponseFuture.getResponse(3000);
            Assert.assertEquals("this is a rpc framework", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.cancelAsyn("getString");
        }
    }

    /**
     * set the method {@code getDO} of {@link DemoTestService} asynchronous
     * and pass a listener to framework,when response is back
     * we want our listener be called by framework
     */
    @Test
    public void testCallback() {
        DemoServiceListener listener = new DemoServiceListener();
        consumer.asynCall("getDO", listener);
        DemoDO nullDo = apiService.getDO();
        Assert.assertEquals(null, nullDo);
        try {
            DemoDO resultDo = (DemoDO) listener.getResponse();
        } catch (InterruptedException e) {
        } finally {
            consumer.cancelAsyn("getDO");
        }
    }

    /**
     * use {@link RpcContext} to pass a key-value structure to Provider
     * {@function getMap()} will pass this context to Consumer
     */
    @Test
    public void testRpcContext() {
        RpcContext.addProp("context", "please pass me!");
        Map<String, Object> resultMap = apiService.getMap();
        Assert.assertTrue(resultMap.containsKey("context"));
        Assert.assertTrue(resultMap.containsValue("please pass me!"));
    }

    /**
     * I can run a hook before I try to call a remote service.
     */
    @Test
    public void testConsumerHook() {
        Map<String, Object> resultMap = apiService.getMap();
        Assert.assertTrue(resultMap.containsKey("hook key"));
        Assert.assertTrue(resultMap.containsValue("this is pass by hook"));
    }

    private static Class<?> getConsumerImplClass(){
        try {
            return Class.forName("pers.cyh.rpc.api.impl.RpcConsumerImpl");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
