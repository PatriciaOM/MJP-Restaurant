/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.uitls;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author twiki
 */
public class Utils {
     /**
     * Utility function to convert from Iterable toList
     * @param iterable
     * @return 
     */
    public static <Sdaf> List<Sdaf> staticConvertIterableToList(Iterable<Sdaf> iterable) {
        List<Sdaf> ret = new ArrayList();
        for (Sdaf item : iterable){
            ret.add(item);
        }
        return ret;
    }
    
}
