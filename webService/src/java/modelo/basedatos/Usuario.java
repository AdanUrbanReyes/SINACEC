package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1



/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private String cuenta;
     private String clave;
     private String nombres;
     private String primerApellido;
     private String segundoApellido;
     private byte[] imagen;
     private char tipo;

    public Usuario() {
    }

	
    public Usuario(String cuenta, String clave, String nombres, String primerApellido, byte[] imagen, char tipo) {
        this.cuenta = cuenta;
        this.clave = clave;
        this.nombres = nombres;
        this.primerApellido = primerApellido;
        this.imagen = imagen;
        this.tipo = tipo;
    }
    public Usuario(String cuenta, String clave, String nombres, String primerApellido, String segundoApellido, byte[] imagen, char tipo) {
       this.cuenta = cuenta;
       this.clave = clave;
       this.nombres = nombres;
       this.primerApellido = primerApellido;
       this.segundoApellido = segundoApellido;
       this.imagen = imagen;
       this.tipo = tipo;
    }
   
    public String getCuenta() {
        return this.cuenta;
    }
    
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    public String getClave() {
        return this.clave;
    }
    
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getNombres() {
        return this.nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getPrimerApellido() {
        return this.primerApellido;
    }
    
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }
    public String getSegundoApellido() {
        return this.segundoApellido;
    }
    
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
    public byte[] getImagen() {
        return this.imagen;
    }
    
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
    public char getTipo() {
        return this.tipo;
    }
    
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }




}

