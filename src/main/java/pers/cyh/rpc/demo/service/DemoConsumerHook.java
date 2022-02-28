package pers.cyh.rpc.demo.service;

import pers.cyh.rpc.aop.ConsumerHook;
import pers.cyh.rpc.context.RpcContext;
import pers.cyh.rpc.model.RpcRequest;


public class DemoConsumerHook implements ConsumerHook {
    @Override
    public void before(RpcRequest request) {
        RpcContext.addProp("hook key","this is pass by hook");
    }

    @Override
    public void after(RpcRequest request) {
        System.out.println("I have finished Rpc calling.");
    }
}
