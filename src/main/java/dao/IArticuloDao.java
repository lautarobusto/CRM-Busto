package dao;

import models.Articulo;
import models.Marca;
import java.util.List;


public interface IArticuloDao {
    List<Articulo> findByName(String nombre);
    List<Articulo> findByBrandAndName(Marca marca, String nombre);
}