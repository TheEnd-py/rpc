package pers.cyh.rpc.demo.service;

import java.io.Serializable;


public class DemoChildrenDO implements Serializable{
    static private final long serialVersionUID = -4364586436171722421L;
    private int childNum;
    private Long longValue;
    private char[] chars;

    public DemoChildrenDO() {
        chars = new char[]{'r', 'p', 'c'};
        childNum = 7;
        longValue = new Long(3000);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DemoChildrenDO other = (DemoChildrenDO) obj;

        if( childNum != other.getChildNum())
            return false;

        if (longValue == null) {
            if (other.getLongValue()!= null)
                return false;
        } else{
            if(other.getLongValue() == null)
                return false;
            else if (!longValue.equals(other.getLongValue()))
                return false;
        }

        char[] otherChars = other.getChars();
        for(int i = 0 ; i < chars.length ; i++){
            if(chars[i] != otherChars[i])
                return false;
        }

        return true;
    }

    public Long getLongValue() {
        return longValue;
    }

    public char[] getChars() {
        return chars;
    }

    public int getChildNum() {
        return childNum;
    }
}
