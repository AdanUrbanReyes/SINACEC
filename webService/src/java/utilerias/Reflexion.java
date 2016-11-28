package utilerias;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ayan
 */
public class Reflexion {
    public static void establecerValorAtributo(Object objeto, Object valor,Class<?> tipo){
        if(objeto != null){
            Class clase=objeto.getClass();
            Field[] atributos=clase.getDeclaredFields();
            for(Field atributo : atributos){
                atributo.setAccessible(true);
                try {
                    if(atributo.getType() == tipo){
                        atributo.set(objeto, valor);   
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Reflexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Reflexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static void establecerValorAtributo(Object objeto, Object valor,String nombreAtributo){
        if(objeto != null){
            Class clase=objeto.getClass();
            Field[] atributos=clase.getDeclaredFields();
            for(Field atributo : atributos){
                atributo.setAccessible(true);
                try {
                    if(atributo.getName().equals(atributo)){
                        atributo.set(objeto, valor);
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Reflexion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Reflexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
}
