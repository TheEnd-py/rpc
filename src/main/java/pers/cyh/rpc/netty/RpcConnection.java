package pers.cyh.rpc.netty;

import java.util.List;
import java.util.Map;

import pers.cyh.rpc.async.ResponseCallbackListener;
import pers.cyh.rpc.model.RpcRequest;

/**
 * 描述与服务器的连接
 */
public interface RpcConnection {
	void init();
	void connect();
	void connect(String host,int port);
	Object Send(RpcRequest request,boolean async);
	void close();
	boolean isConnected();
	boolean isClosed();
	boolean containsFuture(String key);
	InvokeFuture<Object> removeFuture(String key);
	void setResult(Object ret);
	void setTimeOut(long timeout);
	void setAsyncMethod(Map<String, ResponseCallbackListener> map);
	List<InvokeFuture<Object>> getFutures(String method);
}
