/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostería;

/**
 *
 * @author Carolina
 */
public class Postres {
    public int idPostre;
    public String Nombre;
    public float PrecioVenta;
    public int Cantidad;

    public Postres(int idPostre, String Nombre, float PrecioVenta, int Cantidad) {
        this.idPostre = idPostre;
        this.Nombre = Nombre;
        this.PrecioVenta = PrecioVenta;
        this.Cantidad = Cantidad;
    }

    public Postres(String Nombre) {
        this.Nombre = Nombre;
    }

    public Postres() {
    }

    public int getIdPostre() {
        return idPostre;
    }

    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public float getPrecioVenta() {
        return PrecioVenta;
    }

    public void setPrecioVenta(float PrecioVenta) {
        this.PrecioVenta = PrecioVenta;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    
    
}
