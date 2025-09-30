package models;

public class Articulo {

    private int Id;
    private String Codigo;
    private double PrecioNeto;
    private double PrecioIva;
    private double PrecioCosto;
    private String Nombre;
    private String Descripcion;
    private Marca Marca;
    private Rubro Rubro;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public double getPrecioNeto() {
        return PrecioNeto;
    }

    public void setPrecioNeto(double PrecioNeto) {
        this.PrecioNeto = PrecioNeto;
    }

    public double getPrecioIva() {
        return PrecioIva;
    }

    public void setPrecioIva(double PrecioIva) {
        this.PrecioIva = PrecioIva;
    }

    public double getPrecioCosto() {
        return PrecioCosto;
    }

    public void setPrecioCosto(double PrecioCosto) {
        this.PrecioCosto = PrecioCosto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Marca getMarca() {
        return Marca;
    }

    public void setMarca(Marca Marca) {
        this.Marca = Marca;
    }

    public Rubro getRubro() {
        return Rubro;
    }

    public void setRubro(Rubro Rubro) {
        this.Rubro = Rubro;
    }

    @Override
    public String toString() {
        return "Articulo{" + "Id=" + Id + ", Codigo=" + Codigo + ", PrecioNeto=" + PrecioNeto + ", PrecioIva=" + PrecioIva + ", PrecioCosto=" + PrecioCosto + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", Marca=" + Marca + ", Rubro=" + Rubro + '}';
    }
    
    
    
    
    

}
