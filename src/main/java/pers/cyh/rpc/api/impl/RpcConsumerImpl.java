package pers.cyh.rpc.api.impl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


import pers.cyh.rpc.aop.ConsumerHook;
import pers.cyh.rpc.api.RpcConsumer;
import pers.cyh.rpc.async.ResponseCallbackListener;
import pers.cyh.rpc.context.RpcContext;
import pers.cyh.rpc.model.RpcRequest;
import pers.cyh.rpc.model.RpcResponse;
import pers.cyh.rpc.netty.RpcConnection;
import pers.cyh.rpc.netty.RpcNettyConnection;
import pers.cyh.rpc.tool.Tool;


public class RpcConsumerImpl extends RpcConsumer implements InvocationHandler {

	private static final AtomicLong callTimes = new AtomicLong(0L);
	private RpcConnection connection;
	private List<RpcConnection> connectionList;
	private Map<String,ResponseCallbackListener> asyncMethods;
	private Class<?> interfaceClass;
	
	private String version;
	
	private int timeout;
	
	private ConsumerHook hook;
	
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}
	public String getVersion() {
		return version;
	}
	public int getTimeout() {
		this.connection.setTimeOut(timeout);
		return timeout;
	}
	public ConsumerHook getHook() {
		return hook;
	}
	RpcConnection select()
	{
		int d=(int) (callTimes.getAndIncrement()%(connectionList.size()+1));
		if(d==0)
			return connection;
		else
		{
			return connectionList.get(d-1);
		}
	}
	public RpcConsumerImpl()
	{
//		String ip=System.getProperty("SIP");
		String ip="127.0.0.1";
		this.asyncMethods=new HashMap<String,ResponseCallbackListener>();
		this.connection=new RpcNettyConnection(ip,8888);
		this.connection.connect();
		connectionList =new ArrayList<RpcConnection>();
		int num=Runtime.getRuntime().availableProcessors()/3 -2;
		for (int i = 0; i < num; i++) {
			connectionList.add(new RpcNettyConnection(ip, 8888));
		}
		for (RpcConnection conn: connectionList)
		{
			conn.connect();
		}
		
	}
	public void destroy() throws Throwable {
		if (null != connection) {
			connection.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T proxy(Class<T> interfaceClass) throws Throwable {
		if (!interfaceClass.isInterface()) {
			throw new IllegalArgumentException(interfaceClass.getName()
					+ " is not an interface");
		}
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, this);
	}
	@Override
	public RpcConsumer interfaceClass(Class<?> interfaceClass) {
		this.interfaceClass=interfaceClass;
		return this;
	}

	@Override
	public RpcConsumer version(String version) {
		this.version=version;
		return this;
	}

	@Override
	public RpcConsumer clientTimeout(int clientTimeout) {
		this.timeout=clientTimeout;
		return this;
	}

	@Override
	public RpcConsumer hook(ConsumerHook hook) {
		this.hook=hook;
		return this;
	}

	@Override
	public Object instance() {
		try {
			return proxy(this.interfaceClass);
		}
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void asynCall(String methodName) {
		 asynCall(methodName, null);
	}

	@Override
	public <T extends ResponseCallbackListener> void asynCall(
			String methodName, T callbackListener) {
		
		this.asyncMethods.put(methodName, callbackListener);
		this.connection.setAsyncMethod(asyncMethods);
		
		for (RpcConnection conn: connectionList)
		{
			conn.setAsyncMethod(asyncMethods);
		}
	}

	@Override
	public void cancelAsyn(String methodName) {
		this.asyncMethods.remove(methodName);
		this.connection.setAsyncMethod(asyncMethods);
		for (RpcConnection conn: connectionList)
		{
			conn.setAsyncMethod(asyncMethods);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
//		List<String> parameterTypes = new LinkedList<String>();
//		for (Class<?> parameterType : method.getParameterTypes()) {
//			parameterTypes.add(parameterType.getName());
//		}
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        if(hook!=null)
        	hook.before(request);
		RpcResponse response = null;
		try
		{
			request.setContext(RpcContext.props);
			response = (RpcResponse) select().Send(request,asyncMethods.containsKey(request.getMethodName()));
			if(hook!=null)
            	hook.after(request);
			
			if(!asyncMethods.containsKey(request.getMethodName()) && response.getException()!=null)
			{
				
				Throwable e=(Throwable) Tool.deserialize(response.getException(),response.getClazz());
				throw e.getCause();
			}
		}
		catch (Throwable t)
		{	
//			t.printStackTrace();
//			throw new RuntimeException(t);
			throw t;
		}
//		finally
//		{
//			if(asyncMethods.containsKey(request.getMethodName())&&asyncMethods.get(request.getMethodName())!=null)
//			{
//				cancelAsyn(request.getMethodName());
//			}
//		}
		if(response==null)
		{
			return null;
		}
		else if (response.getErrorMsg() != null) 
		{
			throw response.getErrorMsg();
		} 
		else 
		{
			return response.getAppResponse();
		}
		
	}
}

