package utilerias;

import java.util.LinkedList;

/**
 * Created by ayan on 11/6/16.
 */
public class List {
    public static String joinItems(LinkedList<String> ll, String s){
        if(ll == null){
            return null;
        }
        int i;
        String m="";
        for(i=0; i<ll.size(); i++){
            m += ll.get(i)+s;
        }
        return m.substring(0,m.lastIndexOf(s));
    }
}
