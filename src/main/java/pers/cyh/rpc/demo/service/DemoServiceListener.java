package pers.cyh.rpc.demo.service;


import pers.cyh.rpc.async.ResponseCallbackListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class DemoServiceListener implements ResponseCallbackListener {
    private CountDownLatch latch = new CountDownLatch(1);
    private Object response;

    public Object getResponse() throws InterruptedException {
        latch.await(10, TimeUnit.SECONDS);
        if(response == null)
            throw new RuntimeException("The response doesn't come back.");
        return response;
    }
    @Override
    public void onResponse(Object response) {
        System.out.println("This method is call when response arrived");
        this.response = response;
        latch.countDown();
    }

    @Override
    public void onTimeout() {
        throw new RuntimeException("This call has taken time more than timeout value");
    }

    @Override
    public void onException(Exception e) {
        throw new RuntimeException(e);
    }
}
