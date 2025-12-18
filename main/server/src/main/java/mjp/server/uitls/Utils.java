/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mjp.server.uitls;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public static <Type> List<Type> staticConvertIterableToList(Iterable<Type> iterable) {
        List<Type> ret = new ArrayList();
        for (Type item : iterable){
            ret.add(item);
        }
        return ret;
    } 
   
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encrypt(String password) {
        return encoder.encode(password);
    }

    public static boolean checkEncrypted(String password, String hash) {
        return encoder.matches(password, hash);
    }
}
