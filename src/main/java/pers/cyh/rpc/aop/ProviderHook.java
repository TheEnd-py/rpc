package pers.cyh.rpc.aop;

import pers.cyh.rpc.model.RpcRequest;


public interface ProviderHook {
    public void before(RpcRequest request);
    public void after(RpcRequest request);
}
