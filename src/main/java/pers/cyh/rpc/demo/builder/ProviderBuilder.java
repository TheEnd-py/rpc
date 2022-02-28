package pers.cyh.rpc.demo.builder;

import pers.cyh.rpc.api.RpcProvider;
import pers.cyh.rpc.demo.service.DemoTestService;
import pers.cyh.rpc.demo.service.DemoTestServiceImpl;


public class ProviderBuilder {
    public static void buildProvider(){
        publish();
    }

    private static void publish() {
        RpcProvider rpcProvider = null;
        try {
            rpcProvider = (RpcProvider) getProviderImplClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if(rpcProvider == null){
            System.out.println("Start Rpc Provider failed.");
            System.exit(1);
        }

        rpcProvider.serviceInterface(DemoTestService.class)
                .impl(new DemoTestServiceImpl())
                .version("1.0.0.api")
                .timeout(3000)
                .serializeType("java").publish();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static Class<?> getProviderImplClass(){
        try {
            return Class.forName("pers.cyh.rpc.api.impl.RpcProviderImpl");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
