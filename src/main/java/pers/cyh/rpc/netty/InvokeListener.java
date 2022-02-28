package pers.cyh.rpc.netty;

interface InvokeListener<T> {

    void success(T t);

    void failure(Throwable e);

}
