/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostería;

/**
 *
 * @author Carolina
 */
public class Recetas {
    public int idPostre;
    public int idMateriasPrimas;
    public int Cantidad;
    public int Porciones;

    public Recetas(int idPostre, int idMateriasPrimas, int Cantidad, int Porciones) {
        this.idPostre = idPostre;
        this.idMateriasPrimas = idMateriasPrimas;
        this.Cantidad = Cantidad;
        this.Porciones = Porciones;
    }

    public Recetas() {
    }

    public int getIdPostre() {
        return idPostre;
    }

    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
    }

    public int getIdMateriasPrimas() {
        return idMateriasPrimas;
    }

    public void setIdMateriasPrimas(int idMateriasPrimas) {
        this.idMateriasPrimas = idMateriasPrimas;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public int getPorciones() {
        return Porciones;
    }

    public void setPorciones(int Porciones) {
        this.Porciones = Porciones;
    }
    
    
}
