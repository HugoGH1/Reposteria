/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repostería;

/**
 *
 * @author Carolina
 */
public class Pedidos {
    public int idCliente;
    public int idPostre;
    public int Cantidad;
    public float Costo;
    public String FechaEntrega;
    public int idTipoEntrega;

    public Pedidos(int idCliente, int idPostre, int Cantidad, float Costo, String FechaEntrega, int idTipoEntrega) {
        this.idCliente = idCliente;
        this.idPostre = idPostre;
        this.Cantidad = Cantidad;
        this.Costo = Costo;
        this.FechaEntrega = FechaEntrega;
        this.idTipoEntrega = idTipoEntrega;
    }

    public Pedidos() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPostre() {
        return idPostre;
    }

    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public float getCosto() {
        return Costo;
    }

    public void setCosto(float Costo) {
        this.Costo = Costo;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public int getIdTipoEntrega() {
        return idTipoEntrega;
    }

    public void setIdTipoEntrega(int idTipoEntrega) {
        this.idTipoEntrega = idTipoEntrega;
    }
    
    
}
