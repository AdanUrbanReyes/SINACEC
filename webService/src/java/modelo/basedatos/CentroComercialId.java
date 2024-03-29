package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1

import utilerias.Mysql;




/**
 * CentroComercialId generated by hbm2java
 */
public class CentroComercialId  implements java.io.Serializable {


     private String nombre;
     private int direccion;

    public CentroComercialId() {
        direccion = Mysql.valorNoSeteadoInt;
    }

    public CentroComercialId(String nombre, int direccion) {
       this.nombre = nombre;
       this.direccion = direccion;
    }
   
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CentroComercialId) ) return false;
		 CentroComercialId castOther = ( CentroComercialId ) other; 
         
		 return ( (this.getNombre()==castOther.getNombre()) || ( this.getNombre()!=null && castOther.getNombre()!=null && this.getNombre().equals(castOther.getNombre()) ) )
 && (this.getDireccion()==castOther.getDireccion());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNombre() == null ? 0 : this.getNombre().hashCode() );
         result = 37 * result + this.getDireccion();
         return result;
   }   


}


