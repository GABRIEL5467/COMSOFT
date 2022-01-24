package Models;

public class DMantenimiento {

    private int iddmante;
    private Mantenimiento mante;
    private Servicio servicio;
    private Double precio;
    private int cantidad;
    private Double total;
    private String estado;

    public int getIddmante() {
        return iddmante;
    }

    public void setIddmante(int iddmante) {
        this.iddmante = iddmante;
    }

    public Mantenimiento getMante() {
        return mante;
    }

    public void setMante(Mantenimiento mante) {
        this.mante = mante;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
