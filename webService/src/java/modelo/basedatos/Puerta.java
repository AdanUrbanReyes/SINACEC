package modelo.basedatos;
// Generated Nov 12, 2016 10:26:48 PM by Hibernate Tools 4.3.1



/**
 * Puerta generated by hbm2java
 */
public class Puerta  implements java.io.Serializable {


     private PuertaId id;
     private char tipo;

    public Puerta() {
    }

    public Puerta(PuertaId id, char tipo) {
       this.id = id;
       this.tipo = tipo;
    }
   
    public PuertaId getId() {
        return this.id;
    }
    
    public void setId(PuertaId id) {
        this.id = id;
    }
    public char getTipo() {
        return this.tipo;
    }
    
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }




}

