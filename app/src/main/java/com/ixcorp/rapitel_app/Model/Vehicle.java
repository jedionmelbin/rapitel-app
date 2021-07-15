package com.ixcorp.rapitel_app.Model;

public class Vehicle {
    private Integer vehicleId;
    private String nameVehiculo;
    private String plate;
    private String color;

    private String numPedido;
    private String sCliente;
    private String sDireccion;
    private String sTelefono;

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getNameVehiculo() {
        return nameVehiculo;
    }

    public void setNameVehiculo(String nameVehiculo) {
        this.nameVehiculo = nameVehiculo;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public String getsCliente() {
        return sCliente;
    }

    public void setsCliente(String sCliente) {
        this.sCliente = sCliente;
    }

    public String getsDireccion() {
        return sDireccion;
    }

    public void setsDireccion(String sDireccion) {
        this.sDireccion = sDireccion;
    }

    public String getsTelefono() {
        return sTelefono;
    }

    public void setsTelefono(String sTelefono) {
        this.sTelefono = sTelefono;
    }
}
