package pers.cyh.rpc.model;

import java.io.Serializable;


public class RpcResponse implements Serializable{
    static private final long serialVersionUID = -4364536436151723421L;
    
    private Class<?> clazz;
	private byte[] exception;
	private String requestId;
	private Throwable errorMsg;
	private Object appResponse;

    /**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the exception
	 */
	public byte[] getException() {
		return exception;
	}

	/**
	 * @param exception the exception to set
	 */
	public void setException(byte[] exception) {
		this.exception = exception;
	}
    
    /**
	 * @return the requestID
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestID to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(Throwable errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @param appResponse the appResponse to set
	 */
	public void setAppResponse(Object appResponse) {
		this.appResponse = appResponse;
	}

	public Object getAppResponse() {
        return appResponse;
    }

    public Throwable getErrorMsg() {
        return errorMsg;
    }

    public boolean isError(){
        return errorMsg != null;
    }
    
    @Override
    public boolean equals(Object obj) 
    {
    	 if (this == obj)
             return true;
         if (obj == null || getClass() != obj.getClass())
             return false;
         RpcResponse other = (RpcResponse) obj;
         
         if(clazz==null)
         {
        	 if(other.getClazz()!=null)
        		 return false;
         }
         else if(other.getClazz()==null || !clazz.equals(other.getClazz()))
			 return false;
         if(exception ==null)
         {
        	 if (other.getException()!= null)
                 return false;
         }
         else
         {
             if(other.getException()== null || exception.length!=other.getException().length)
                 return false;
			 for (int i = 0; i < exception.length; i++) {
				if(exception[i]!=other.getException()[i])
					return false;
			 }
         }
         if(appResponse==null)
         {
			 return other.getAppResponse() == null;
         }
		return other.getAppResponse() != null && appResponse.equals(other.getAppResponse());
	}
}
