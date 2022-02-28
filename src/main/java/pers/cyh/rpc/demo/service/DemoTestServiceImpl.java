package pers.cyh.rpc.demo.service;

import pers.cyh.rpc.context.RpcContext;

import java.util.HashMap;
import java.util.Map;


public class DemoTestServiceImpl implements DemoTestService {
    @Override
    public Map<String, Object> getMap() {
        Map<String,Object> newMap = new HashMap<String,Object>();
        newMap.put("demo","rpc");
        if(RpcContext.getProps() != null );
        newMap.putAll(RpcContext.getProps());
        return newMap;
    }

    @Override
    public String getString() {
        return "this is a rpc framework";
    }

    @Override
    public DemoDO getDO() {
        return new DemoDO();
    }

    @Override
    public boolean longTimeMethod() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Integer throwException() throws DemoException {
        throw new DemoException("just a exception");
    }
}
