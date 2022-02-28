package pers.cyh.rpc.model;

import java.io.Serializable;
import java.util.Map;


public class RpcRequest implements Serializable {
	private static final long serialVersionUID = 5606111910428846773L;
	private String requestId;
	private String className;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;
	private Map<String,Object> context;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	public Map<String, Object> getContext() {
		return context;
	}
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
	@Override
    public boolean equals(Object obj) 
    {
		if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        RpcRequest other = (RpcRequest) obj;
        if(className==null)
        {
			 if(other.getClassName()!=null)
				 return false;
        }
        else if(other.getClassName()==null || !className.equals(other.getClassName()))
       		 return false;
        
        if(methodName==null)
        {
			 if(other.getMethodName()!=null)
				 return false;
        }
        else if(other.getMethodName()==null || !methodName.equals(other.getMethodName()))
       		 return false;
        
        if(parameterTypes==null)
        {
			 if(other.getParameterTypes()!=null)
				 return false;
        }
        else
        {
			 if(other.getParameterTypes()==null || parameterTypes.length!=other.getParameterTypes().length)
				 return false;
			 for (int i = 0; i < parameterTypes.length; i++) {
				if(!parameterTypes[i].equals(other.getParameterTypes()[i]))
					return false;
			 }
        }
        
        if(parameters==null)
        {
			 if(other.getParameters()!=null)
				 return false;
        }
        else
        {
			 if(other.getParameters()==null || parameters.length!=other.getParameters().length)
				 return false;
			 for (int i = 0; i < parameters.length; i++) {
				if(!parameters[i].equals(other.getParameters()[i]))
					return false;
			 }
        }
        
        if(context==null)
        {
			return other.getContext() == null;
        }
		return other.getContext() != null && context.equals(other.getContext());
	}
}
