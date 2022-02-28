package pers.cyh.rpc.demo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DemoDO implements Serializable{
    static private final long serialVersionUID = -4364536336161728421L;
    private int num;
    private String str;
    private List<String> list;
    private DemoChildrenDO child;

    public DemoDO() {
        num = 3;
        str = "rpc";

        list = new ArrayList<String>();
        list.add("rpc-list");

        child = new DemoChildrenDO();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        DemoDO other = (DemoDO) obj;

        if(num != other.getNum())
            return false;

        if (str == null) {
            if (other.getStr()!= null)
                return false;
        }
        else if(other.getStr() == null || !str.equals(other.getStr()))
            return false;

        if (list == null) {
            if (other.getList() != null)
                return false;
        }
        else if(other.getList() == null || !list.equals(other.getList()))
            return false;

        if (child == null) {
            return other.getChild() == null;
        }
        return other.getChild() != null && child.equals(other.getChild());
    }

    public int getNum() {
        return num;
    }

    public String getStr() {
        return str;
    }

    public List<String> getList() {
        return list;
    }

    public DemoChildrenDO getChild() {
        return child;
    }
    public static void main(String[] args){
        DemoDO demoDO = new DemoDO();
        System.out.println(demoDO.equals(new DemoDO()));
    }
}
