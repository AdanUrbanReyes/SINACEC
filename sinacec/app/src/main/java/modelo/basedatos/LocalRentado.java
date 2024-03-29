package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * LocalRentado generated by hbm2java
 */
public class LocalRentado  implements java.io.Serializable {


     private LocalRentadoId id;
     private String locatario;
     private String nombre;
     private String giro;
     private Date horarioApertura;
     private Date horarioCierre;
     private String telefono;
     private String sitioWeb;
     private byte[] imagen;

    public LocalRentado() {
    }

	
    public LocalRentado(LocalRentadoId id, String locatario, String nombre, String giro, Date horarioApertura, Date horarioCierre, byte[] imagen) {
        this.id = id;
        this.locatario = locatario;
        this.nombre = nombre;
        this.giro = giro;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.imagen = imagen;
    }
    public LocalRentado(LocalRentadoId id, String locatario, String nombre, String giro, Date horarioApertura, Date horarioCierre, String telefono, String sitioWeb, byte[] imagen) {
       this.id = id;
       this.locatario = locatario;
       this.nombre = nombre;
       this.giro = giro;
       this.horarioApertura = horarioApertura;
       this.horarioCierre = horarioCierre;
       this.telefono = telefono;
       this.sitioWeb = sitioWeb;
       this.imagen = imagen;
    }
   
    public LocalRentadoId getId() {
        return this.id;
    }
    
    public void setId(LocalRentadoId id) {
        this.id = id;
    }
    public String getLocatario() {
        return this.locatario;
    }
    
    public void setLocatario(String locatario) {
        this.locatario = locatario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getGiro() {
        return this.giro;
    }
    
    public void setGiro(String giro) {
        this.giro = giro;
    }
    public Date getHorarioApertura() {
        return this.horarioApertura;
    }
    
    public void setHorarioApertura(Date horarioApertura) {
        this.horarioApertura = horarioApertura;
    }
    public Date getHorarioCierre() {
        return this.horarioCierre;
    }
    
    public void setHorarioCierre(Date horarioCierre) {
        this.horarioCierre = horarioCierre;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getSitioWeb() {
        return this.sitioWeb;
    }
    
    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    public byte[] getImagen() {
        return this.imagen;
    }
    
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }




}


