package pers.cyh.rpc.demo.service;


public class DemoException extends RuntimeException{
    private String flag = "demo";
    public DemoException(String message){
        super(message);
    }
    public DemoException(Exception e){
        super(e);
    }
    public String getFlag(){
        return flag;
    }
}
