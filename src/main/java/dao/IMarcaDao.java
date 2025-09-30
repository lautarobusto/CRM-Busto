package dao;

import models.Marca;
import java.util.List;

// IMarcaDao.java
public interface IMarcaDao {
    List<Marca> findAll();
}