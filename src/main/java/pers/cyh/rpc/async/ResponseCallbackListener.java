package pers.cyh.rpc.async;


public interface ResponseCallbackListener {
    void onResponse(Object response);
    void onTimeout();
    void onException(Exception e);
}
