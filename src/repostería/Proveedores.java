/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostería;

/**
 *
 * @author Carolina
 */
public class Proveedores {
    public String Nombre;
    public String NumeroTelefonico;
    public String Calle;
    public String NumeroExterior;
    public String Colonia;
    public String CodigoPostal;

    public Proveedores(String Nombre, String NumeroTelefonico, String Calle, String NumeroExterior, 
            String Colonia, String CodigoPostal) {
        this.Nombre = Nombre;
        this.NumeroTelefonico = NumeroTelefonico;
        this.Calle = Calle;
        this.NumeroExterior = NumeroExterior;
        this.Colonia = Colonia;
        this.CodigoPostal = CodigoPostal;
    }

    public Proveedores() {
    }

    public Proveedores(String Nombre) {
        this.Nombre = Nombre;
    }
    

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNumeroTelefonico() {
        return NumeroTelefonico;
    }

    public void setNumeroTelefonico(String NumeroTelefonico) {
        this.NumeroTelefonico = NumeroTelefonico;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String Colonia) {
        this.Colonia = Colonia;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }
    
    
}
