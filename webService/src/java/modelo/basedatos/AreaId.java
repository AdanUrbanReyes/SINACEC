package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1

import utilerias.Mysql;




/**
 * AreaId generated by hbm2java
 */
public class AreaId  implements java.io.Serializable {


     private String nombre;
     private String centroComercial;
     private int direccionCentroComercial;

    public AreaId() {
        direccionCentroComercial = Mysql.valorNoSeteadoInt;
    }

    public AreaId(String nombre, String centroComercial, int direccionCentroComercial) {
       this.nombre = nombre;
       this.centroComercial = centroComercial;
       this.direccionCentroComercial = direccionCentroComercial;
    }
   
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCentroComercial() {
        return this.centroComercial;
    }
    
    public void setCentroComercial(String centroComercial) {
        this.centroComercial = centroComercial;
    }
    public int getDireccionCentroComercial() {
        return this.direccionCentroComercial;
    }
    
    public void setDireccionCentroComercial(int direccionCentroComercial) {
        this.direccionCentroComercial = direccionCentroComercial;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AreaId) ) return false;
		 AreaId castOther = ( AreaId ) other; 
         
		 return ( (this.getNombre()==castOther.getNombre()) || ( this.getNombre()!=null && castOther.getNombre()!=null && this.getNombre().equals(castOther.getNombre()) ) )
 && ( (this.getCentroComercial()==castOther.getCentroComercial()) || ( this.getCentroComercial()!=null && castOther.getCentroComercial()!=null && this.getCentroComercial().equals(castOther.getCentroComercial()) ) )
 && (this.getDireccionCentroComercial()==castOther.getDireccionCentroComercial());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNombre() == null ? 0 : this.getNombre().hashCode() );
         result = 37 * result + ( getCentroComercial() == null ? 0 : this.getCentroComercial().hashCode() );
         result = 37 * result + this.getDireccionCentroComercial();
         return result;
   }   


}


