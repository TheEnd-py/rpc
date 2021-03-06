package pers.cyh.rpc.api;


public class RpcProvider {

    public RpcProvider() {}

    /**
     * init Provider
     */
    private void init(){
    }

    /**
     * set the interface which this provider want to expose as a service
     * @param serviceInterface
     */
    public RpcProvider serviceInterface(Class<?> serviceInterface){
        return this;
    }

    /**
     * set the version of the service
     * @param version
     */
    public RpcProvider version(String version){
        return this;
    }

    /**
     * set the instance which implements the service's interface
     * @param serviceInstance
     */
    public RpcProvider impl(Object serviceInstance){
        return this;
    }

    /**
     * set the timeout of the service
     * @param timeout
     */
    public RpcProvider timeout(int timeout){
        return this;
    }

    /**
     * set serialize type of this service
     * @param serializeType
     */
    public RpcProvider serializeType(String serializeType){
        return this;
    }

    /**
     * publish this service
     * if you want to publish your service , you need a registry server.
     * after all , you cannot write servers' ips in config file when you have 1 million server.
     * you can use ZooKeeper as your registry server to make your services found by your consumers.
     */
    public void publish() {
    }
}
