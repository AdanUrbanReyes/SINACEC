package utilerias;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ayan
 */
public class JSON {
    private static String paqueteBasedatos="modelo.basedatos";
    public static String formatoFecha = "yyyy-MM-dd";
    public static String formatoTiempo = "hh:mm:ss a";
    
    /**
     * Trata de convertir el string json a un objeto del tipo
     * de la clase recivido. Esta clase deve existir dentro del
     * paquete definido en la variable estatica paqueteBasedatos
     * de esta clase.
     * @param json cadena que representa un objeto en formato json
     * @param nombreClase nombre de la clase en el que sera almacenado
     * el objeto json debe existir en el paquete definido en la variable
     * statica paqueteBasedatos de esta clase
     * @return objeto del tipo de clase recivida, que representa el mapeo
     * de la cadena json recivida. O null en caso de de una excepcion.
     * Algunos errores posibles podrian ser:
     * No existe la clase dentro del paquete.
     */
    public static Object aObjeto(String json,String nombreClase){
        Gson gson= (nombreClase.equals("Oferta")) ? new GsonBuilder().setDateFormat(formatoFecha).create() : new GsonBuilder().setDateFormat(formatoTiempo).create();
        Object objeto=null;
        try {
            objeto=gson.fromJson(json, Class.forName(paqueteBasedatos+"."+nombreClase));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return objeto;
    }

    public static Object[] aArrayObjeto(String json,String nombreClase){
        Gson gson= (nombreClase.equals("Oferta")) ? new GsonBuilder().setDateFormat(formatoFecha).create() : new GsonBuilder().setDateFormat(formatoTiempo).create();
        Object []objeto = new Object[0];
        try {
            objeto = gson.fromJson(json, ((Object[])Array.newInstance(Class.forName(paqueteBasedatos+"."+nombreClase),0)).getClass());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
            return objeto;
        }
        return objeto;
    }

    public static String aJSON(Object objeto,String nombreClase){
        Gson gson= (nombreClase.equals("Oferta")) ? new GsonBuilder().setDateFormat(formatoFecha).create() : new GsonBuilder().setDateFormat(formatoTiempo).create();
        return gson.toJson(objeto);
    }
}
