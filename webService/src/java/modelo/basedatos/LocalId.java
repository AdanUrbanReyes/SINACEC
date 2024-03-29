package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1

import utilerias.Mysql;




/**
 * LocalId generated by hbm2java
 */
public class LocalId  implements java.io.Serializable {


     private String local;
     private String centroComercial;
     private int direccionCentroComercial;

    public LocalId() {
        direccionCentroComercial = Mysql.valorNoSeteadoInt;
    }

    public LocalId(String local, String centroComercial, int direccionCentroComercial) {
       this.local = local;
       this.centroComercial = centroComercial;
       this.direccionCentroComercial = direccionCentroComercial;
    }
   
    public String getLocal() {
        return this.local;
    }
    
    public void setLocal(String local) {
        this.local = local;
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
		 if ( !(other instanceof LocalId) ) return false;
		 LocalId castOther = ( LocalId ) other; 
         
		 return ( (this.getLocal()==castOther.getLocal()) || ( this.getLocal()!=null && castOther.getLocal()!=null && this.getLocal().equals(castOther.getLocal()) ) )
 && ( (this.getCentroComercial()==castOther.getCentroComercial()) || ( this.getCentroComercial()!=null && castOther.getCentroComercial()!=null && this.getCentroComercial().equals(castOther.getCentroComercial()) ) )
 && (this.getDireccionCentroComercial()==castOther.getDireccionCentroComercial());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLocal() == null ? 0 : this.getLocal().hashCode() );
         result = 37 * result + ( getCentroComercial() == null ? 0 : this.getCentroComercial().hashCode() );
         result = 37 * result + this.getDireccionCentroComercial();
         return result;
   }   


}


