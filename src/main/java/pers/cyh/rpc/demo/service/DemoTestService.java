package pers.cyh.rpc.demo.service;

import java.util.Map;


public interface DemoTestService {
    public Map<String,Object> getMap();
    public String getString();
    public DemoDO getDO();
    public boolean longTimeMethod();
    public Integer throwException() throws DemoException;
}
