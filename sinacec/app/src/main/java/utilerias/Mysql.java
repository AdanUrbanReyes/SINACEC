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
}
