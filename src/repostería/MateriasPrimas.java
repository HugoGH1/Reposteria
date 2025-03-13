/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostería;

/**
 *
 * @author Carolina
 */
public class MateriasPrimas {
    public String Nombre;
    public int idCategoria;
    public int idUnidadMedida;
    public int Stock;
    public int idProveedor;

    public MateriasPrimas(String Nombre, int idCategoria, int idUnidadMedida, int Stock, int idProveedor) {
        this.Nombre = Nombre;
        this.idCategoria = idCategoria;
        this.idUnidadMedida = idUnidadMedida;
        this.Stock = Stock;
        this.idProveedor = idProveedor;
    }

    public MateriasPrimas() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getidUnidadMedida() {
        return idUnidadMedida;
    }

    public void setidUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    
}
