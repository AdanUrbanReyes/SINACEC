package service;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.ConexionBasedatos;
import utilerias.JSON;
import utilerias.Mysql;
/**
 *
 * @author ayan
 */
@WebService(serviceName = "Sinacec")
public class Sinacec {

    /**
     * Inserta el registro en la base de datos. Mediante reflexion 
     * obtiene el tipo de objeto
     * con el cual se trabajara. Toma en cuenta unicamente las clases
     * que estan almacenadas en el paquete definido por la variable statica
     * paqueteBasedatos de esta clase
     * Los fallos se pueden dan cuando no existe la clase recivida en
     * el paquete.
     * @param json cadena en formato JSON que representa un objeto de
     * cualquier tipo que EXISTA en el paquete.
     * ademas de corresponder con el nombre de la clase recivido
     * @param clase cadena que representa el nombre de clase a la que
     * pertenece el objeto recivido. La clase debe estar en el paquete.
     * @return cadena en formato JSON que representa una insercion
     * exitosa. En caso de fallo se regresa cadena JSON null.
     */
    @WebMethod(operationName = "crear")
    public boolean crear(@WebParam(name = "json") String json, @WebParam(name = "clase") String clase){
        Object objeto=JSON.aObjeto(json, clase);
        if(objeto==null){
            return false;
        }
        ConexionBasedatos conexionBasedatos=new ConexionBasedatos();
        conexionBasedatos.iniciaTransaccion();
        return (conexionBasedatos.insertar(objeto)) ? conexionBasedatos.terminaTransaccion() : conexionBasedatos.cancelarTransaccion();
    }
    
    /**
     * Obtiene los registros de la base de datos para cualquier tabla 
     * dentro de esta. Mediante reflexion obtiene el tipo de objeto
     * con el cual se trabajara. Toma en cuenta unicamente las clases
     * que estan almacenadas en el paquete definido por la variable statica
     * paqueteBasedatos de esta clase.
     * Los fallos se pueden dan cuando no existe la clase recivida en
     * el paquete.
     * @param json cadena en formato JSON que representa un objeto de
     * cualquier tipo que EXISTA en el paquete,
     * ademas de corresponder con el nombre de la clase recivido
     * @param clase cadena que representa el nombre de clase a la que
     * pertenece el objeto recivido. La clase debe estar en el paquete.
     * @param proyeccion
     * @return cadena en formato JSON que representa una lista del tipo
     * de objetos de acuerdo a el nombre de la clase recivida. En caso de fallo
     * o de no existir ningun registro con los datos recividos se regresa 
     * cadena JSON null.
     */
    @WebMethod(operationName = "leer")
    public String leer(@WebParam(name = "json") String json, @WebParam(name = "clase") String clase, @WebParam(name = "proyeccion") String proyeccion){
        Object objeto=JSON.aObjeto(json, clase);
        List<Object> registros = ConexionBasedatos.leer(clase,Mysql.generarFiltro(objeto, "AND",null), proyeccion);
        return (registros.isEmpty()) ? "null" : (registros.size() == 1) ? JSON.aJSON(registros.get(0), clase) : JSON.aJSON(registros,clase);
    }

    /**
     * Actualiza el registro en la base de datos. Mediante reflexion 
     * obtiene el tipo de objeto
     * con el cual se trabajara. Toma en cuenta unicamente las clases
     * que estan almacenadas en el paquete definido por la variable statica
     * paqueteBasedatos de esta clase.
     * Los fallos se pueden dan cuando no existe la clase recivida en
     * el paquete.
     * @param json cadena en formato JSON que representa un objeto de
     * cualquier tipo que EXISTA en el paquete,
     * ademas de corresponder con el nombre de la clase recivido
     * @param clase cadena que representa la el nombre de clase a la que
     * pertenece el objeto recivido. La clase debe estar en el paquete.
     * @return cadena en formato JSON que representa una insercion
     * exitosa. En caso de fallo se regresa cadena JSON null.
     */
    @WebMethod(operationName = "actualizar")
    public boolean actualizar(@WebParam(name = "json") String json, @WebParam(name = "clase") String clase){
        Object objeto=JSON.aObjeto(json, clase);
        if(objeto==null){
            return false;
        }
        ConexionBasedatos conexionBasedatos=new ConexionBasedatos();
        conexionBasedatos.iniciaTransaccion();
        return (conexionBasedatos.actualizar(objeto)) ? conexionBasedatos.terminaTransaccion() : conexionBasedatos.cancelarTransaccion();
    }

    /**
     * Elimina el registro en la base de datos. Mediante reflexion 
     * obtiene el tipo de objeto
     * con el cual se trabajara. Toma en cuenta unicamente las clases
     * que estan almacenadas en el paquete definido por la variable statica
     * paqueteBasedatos de esta clase.
     * Los fallos se pueden dan cuando no existe la clase recivida en
     * el paquet.
     * @param json cadena en formato JSON que representa un objeto de
     * cualquier tipo que EXISTA en el paquete,
     * ademas de corresponder con el nombre de la clase recivido
     * @param clase cadena que representa el nombre de clase a la que
     * pertenece el objeto recivido. La clase debe estar en el paquete.
     * @return cadena en formato JSON que representa una insercion
     * exitosa. En caso de fallo se regresa cadena JSON null.
     */
    @WebMethod(operationName = "eliminar")
    public boolean eliminar(@WebParam(name = "json") String json, @WebParam(name = "clase") String clase){
        Object objeto=JSON.aObjeto(json, clase);
        if(objeto==null){
            return false;
        }
        ConexionBasedatos conexionBasedatos=new ConexionBasedatos();
        conexionBasedatos.iniciaTransaccion();
        return (conexionBasedatos.eliminar(objeto)) ? conexionBasedatos.terminaTransaccion() : conexionBasedatos.cancelarTransaccion();
    }

    @WebMethod(operationName = "llamarProcedimientoSelect")
    public String llamarProcedimientoSelect(@WebParam(name = "procedimiento") String procedimiento, @WebParam(name = "parametros") String parametros, @WebParam(name = "clase") String clase){
        ConexionBasedatos conexionBasedatos = new ConexionBasedatos();
        conexionBasedatos.iniciaTransaccion();
        List<Object> registros = conexionBasedatos.llamarProcedimientoSelect(procedimiento, parametros.split(","));
        conexionBasedatos.terminaTransaccion();        
        return (registros.isEmpty()) ? "null" : (registros.size() == 1) ? JSON.aJSON(registros.get(0), clase) : JSON.aJSON(registros,clase);
    }

    @WebMethod(operationName = "llamarProcedimientoUpdate")
    public int llamarProcedimientoUpdate(@WebParam(name = "procedimiento") String procedimiento, @WebParam(name = "parametros") String parametros){
        ConexionBasedatos conexionBasedatos = new ConexionBasedatos();
        conexionBasedatos.iniciaTransaccion();
        int ra = conexionBasedatos.llamarProcedimientoUpdate(procedimiento, parametros.split(","));
        conexionBasedatos.terminaTransaccion();
        return ra;
    }
    
}
