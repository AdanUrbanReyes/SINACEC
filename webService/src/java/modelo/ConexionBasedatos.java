package modelo;

import hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Con ayuda de esta clase  se podran ejecutar
 * (INSERT,SELECT,UPDATE,DELETE) => (CREATE,READ,UPDATE,DELETE) => CRUD
 * antes de poder ejecutar cualquier sentencia de tipo INSERT, UPDATE o DELETE
 * debe iniciarse una transaccion asi mismo al terminar de ejectuar la o las
 * sentencias debe cerrarse la transaccion
 * @author ayan
 */
public class ConexionBasedatos{
    private Session sesion=null;
    
    public ConexionBasedatos(){
        sesion=HibernateUtil.getSessionFactory().openSession();
    }
    
    /**
     * Se trata de iniciar una transaccion, si exite una transaccion sin
     * finalizar, la llamada a este metodo no tiene sentido.
     * Porque  no iniciaria ninguna transaccion se quedaria con la que existe
     */
    public void iniciaTransaccion(){
        sesion = (sesion.isOpen()) ? sesion : HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
    }
    
    /**
     * Trata de terminal una transaccion
     * Posibles Excepciones
     * org.hibernate.StaleStateException: indica que se trato de modificar un registro que no existia
     * org.hibernate.TransactionException: indica que se trato de eliminar un registro que no existia
     * org.hibernate.exception.ConstraintViolationException: indica que se trato de insertar un registro que ya existia (clave primaria duplicada)
     * @return true si se pudo cerrar la transaccion; es decir si se pudieron
     * guardar todos los cambios. En caso contrario false
     */
    public boolean terminaTransaccion(){
        boolean estatus=true;
        try{
            sesion.getTransaction().commit();
        }catch(Exception e){
            if(sesion.getTransaction()!=null){
                sesion.getTransaction().rollback();
            }
            estatus=false;
            System.out.println("ERROR en la clase " + this.getClass().getName()+" en el metodo terminaTransaccion: " + e.toString());
        }finally{
            sesion.flush();
            sesion.close();
        }
        return estatus;
    }
    
    public boolean cancelarTransaccion(){
        boolean estatus=true;
        try{
            if(sesion.getTransaction()!=null){
                sesion.getTransaction().rollback();
            }
        }catch(Exception e){
            estatus=false;
            System.out.println("ERROR en la clase " + this.getClass().getName()+" en el metodo cancelarTransaccion: " + e.toString());
        }finally{
            sesion.flush();
            sesion.close();
        }
        return estatus;
    }
    
    public boolean insertar(Object objeto){
        try{
            sesion.save(objeto);
        }catch(Exception he){
            System.out.println("Error en la clase " + this.getClass().getName()+" en el metodo insertar: "+ he.toString());
            return false;
        }
        return true;
    }
    
    /**
     * Ejecuta una sentencia tipo hibernate, regresando el resulado de la 
     * consulta en una lista de objetos del tipo recivido.
     * Unico metodo que no requiere aver iniciado una transaccion previamente.
     * Una posible excepcion del metodo es:
     * org.hibernate.exception.SQLGrammarException: indica que el el nombre de la clase y/o el filtro son invalidos
     * @param clase nombre de la clase que hace referencia a una tabla de 
     * la base datos
     * @param filtro condicion que debe cumplir cada uno de los resultados 
     * debueltos en la ejecucion de la consulta este filtro NO DEBE CONTENER 
     * LA PARALABRA RECERVADA WHERE. Si el filtro es null o cadena vacia
     * se omite el filtro (se selecciona todas las tuplas de la tabla)
     * @param proyeccion Aqui se indican los parametros a ser proyectados, tomar encuenta que si el parametro esta envuelto en otro parametro por ejemplo
     * id.nombre
     * @return lista de objetos del tipo clase
    */
    public static List<Object> leer(String clase, String filtro, String proyeccion){
        System.out.println("filtro = "+filtro);
        filtro = (filtro==null || filtro.isEmpty()) ? "" : " WHERE "+filtro;
        proyeccion = (proyeccion == null || proyeccion.isEmpty()) ? "" : "SELECT "+proyeccion+" ";
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        List<Object> tuplas=new java.util.LinkedList<Object>();
        try{
            tuplas=sesion.createQuery(proyeccion+"FROM "+clase+" "+filtro).list();
        }catch(Exception he){
            System.out.print("ERROR en la clase " + ConexionBasedatos.class.getName()+" en el metodo leer: " + he.toString());
        }finally{
            sesion.close();
        }
        return tuplas;
    }
    
    public boolean actualizar(Object objeto){
        try{
            sesion.update(objeto);
        }catch(Exception e){
            System.out.println("Error en la clase " + this.getClass().getName()+" en el metodo actualizar: "+ e.toString());
            return false;
        }
        return true;
    }    
    
    public boolean eliminar(Object objeto){
        try{
            sesion.delete(objeto);
        }catch(Exception e){
            System.out.println("Error en la clase " + this.getClass().getName()+" en el metodo eliminar: "+ e.toString());
            return false;
        }
        return true;        
        
    }
    
    public List<Object> llamarProcedimientoSelect(String procedure, String []parametros){        
        Query query = sesion.getNamedQuery(procedure);
        List<Object> tuplas=new java.util.LinkedList<Object>();
        int i;
        try{
            for(i=0;i<parametros.length;i++){
                System.out.print(""+i+" parametro = "+parametros[i]);
                query.setParameter(i, parametros[i]);
            }
            tuplas = query.list();
        }catch(Exception e){
            System.out.println("Error al ejectuar procedure "+e.toString());
        }
        return tuplas;
    }

    public int llamarProcedimientoUpdate(String procedure, String []parametros){        
        Query query = sesion.getNamedQuery(procedure);
        int i=-1;
        try{
            for(i=0;i<parametros.length;i++){
                System.out.print(""+i+" parametro = "+parametros[i]);
                query.setParameter(i, parametros[i]);
            }
            i = query.executeUpdate();
        }catch(Exception e){
            System.out.println("Error al ejectuar procedure "+e.toString());
        }
        return i;
    }
    
}
