package utilerias;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mysql {
    public static SimpleDateFormat formatoFechaTiempo = new SimpleDateFormat(JSON.formatoFecha+" "+JSON.formatoTiempo);
    public static SimpleDateFormat formatoFecha = new SimpleDateFormat(JSON.formatoFecha);
    public static SimpleDateFormat formatoTiempo = new SimpleDateFormat(JSON.formatoTiempo);
    public static String valorNoSeteadoString = null;
    public static char valorNoSeteadoChar = '\u0000';
    public static int valorNoSeteadoInt = Integer.MIN_VALUE;
    public static double valorNoSeteadoDouble = Double.MIN_VALUE;
    public static Date valorNoSeteadoDate=null;
    public static String banderaValorNoSeteado = "unset";

    public static String aMySQL(Field atributo,Object valor){
        if(atributo == null || valor == null){
            return null;
        }
        if(atributo.getType() == String.class){
            return ( ((String)valor).equals(valorNoSeteadoString) ) ? Mysql.banderaValorNoSeteado :  "'"+(String)valor+"'";
        }
        if(atributo.getType() == char.class){
            return ( ((char)valor) == valorNoSeteadoChar  ) ?  Mysql.banderaValorNoSeteado : "'"+(char)valor+"'";
        }
        if(atributo.getType() == int.class){
            return ( ((int)valor) == valorNoSeteadoInt ) ? Mysql.banderaValorNoSeteado :  ""+(int)valor;
        }
        if(atributo.getType() == Integer.class){
            return ( ((Integer)valor) == valorNoSeteadoInt ) ? Mysql.banderaValorNoSeteado :  ""+(Integer)valor;
        }        
        if(atributo.getType() == double.class ){
            return ( ((double)valor) == valorNoSeteadoDouble ) ? Mysql.banderaValorNoSeteado : ""+(double)valor;
        }
        if(atributo.getType() == Date.class){
                return ( ((Date)valor).equals(valorNoSeteadoDate) ) ? Mysql.banderaValorNoSeteado : "'" + formatoFechaTiempo.format(valor) + "'";
        }
        return null;
    }
    
    public static String aMySQLLike(Field atributo,Object valor){
        if(atributo == null || valor == null){
            return null;
        }
        if(atributo.getType() == String.class){
            return ( ((String)valor).equals(valorNoSeteadoString) ) ? Mysql.banderaValorNoSeteado :  "'%"+(String)valor+"%'";
        }
        if(atributo.getType() == Date.class){
                return ( ((Date)valor).equals(valorNoSeteadoDate) ) ? Mysql.banderaValorNoSeteado : "'%" + formatoFechaTiempo.format(valor) + "%'";
        }
        return null;
    }
        
    public static String generarFiltro(Object objeto,String separador,String nombreAtributoAcarreado){
        if(objeto == null){
            return null;
        }
        String filtro="";
        String nombreAtributo=null;
        String valorAtributoMySQL=null;
        Class clase=objeto.getClass();
        Field[] atributos=clase.getDeclaredFields();
        for(Field atributo : atributos){
            atributo.setAccessible(true);
            nombreAtributo=atributo.getName();
            try {
                valorAtributoMySQL = aMySQL(atributo, atributo.get(objeto));
                if(valorAtributoMySQL == null){
                    if(nombreAtributo.equals("id")){//Una estupides deberia ser mas genirico esto pero esque si llamo sin preguntar se queda ciclado nunca para la recursividad :S
                        nombreAtributo = (nombreAtributoAcarreado == null || nombreAtributoAcarreado.equals("")) ? nombreAtributo : nombreAtributoAcarreado+"."+nombreAtributo;                        
                        valorAtributoMySQL = generarFiltro(atributo.get(objeto), separador, nombreAtributo);//aqui la variable valorAtributoMySQL se utiliza como auxiliar
                        filtro = (valorAtributoMySQL == null) ? "" : valorAtributoMySQL + " " + separador+" ";
                    }
                }else{
                    if(!valorAtributoMySQL.equals(Mysql.banderaValorNoSeteado)){
                        nombreAtributo = (nombreAtributoAcarreado == null || nombreAtributoAcarreado.equals("")) ? nombreAtributo : nombreAtributoAcarreado+"."+nombreAtributo;
                        filtro += nombreAtributo + " = " + valorAtributoMySQL + " " + separador+" ";
                    }
                }
            } catch (IllegalArgumentException ex) {
               ex.printStackTrace();
               return null;
            } catch (IllegalAccessException ex) {
               ex.printStackTrace();
               return null;
            }
        }
        return (filtro.equals("")) ? null : filtro.substring(0, filtro.lastIndexOf(separador));
    }
 
    public static String generarFiltroLike(Object objeto,String separador,String nombreAtributoAcarreado){
        if(objeto == null){
            return null;
        }
        String filtro="";
        String nombreAtributo=null;
        String valorAtributoMySQL=null;
        Class clase=objeto.getClass();
        Field[] atributos=clase.getDeclaredFields();
        for(Field atributo : atributos){
            atributo.setAccessible(true);
            nombreAtributo=atributo.getName();
            try {
                valorAtributoMySQL = aMySQLLike(atributo, atributo.get(objeto));
                if(valorAtributoMySQL == null){
                    if(nombreAtributo.equals("id")){//Una estupides deberia ser mas genirico esto pero esque si llamo sin preguntar se queda ciclado nunca para la recursividad :S
                        nombreAtributo = (nombreAtributoAcarreado == null || nombreAtributoAcarreado.equals("")) ? nombreAtributo : nombreAtributoAcarreado+"."+nombreAtributo;                        
                        valorAtributoMySQL = generarFiltroLike(atributo.get(objeto), separador, nombreAtributo);//aqui la variable valorAtributoMySQL se utiliza como auxiliar
                        filtro = (valorAtributoMySQL == null) ? "" : valorAtributoMySQL + " " + separador+" ";
                    }
                }else{
                    if(!valorAtributoMySQL.equals(Mysql.banderaValorNoSeteado)){
                        nombreAtributo = (nombreAtributoAcarreado == null || nombreAtributoAcarreado.equals("")) ? nombreAtributo : nombreAtributoAcarreado+"."+nombreAtributo;
                        filtro += nombreAtributo + " LIKE " + valorAtributoMySQL + " " + separador+" ";
                    }
                }
            } catch (IllegalArgumentException ex) {
               ex.printStackTrace();
               return null;
            } catch (IllegalAccessException ex) {
               ex.printStackTrace();
               return null;
            }
        }
        return (filtro.equals("")) ? null : filtro.substring(0, filtro.lastIndexOf(separador));
    }
}
