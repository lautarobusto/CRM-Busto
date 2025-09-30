package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Marca;

public class MarcaDao implements IMarcaDao {
    
    @Override
    public List<Marca> findAll() {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT * FROM marcas ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Marca marca = new Marca();
                marca.setId(rs.getInt("id"));
                marca.setNombre(rs.getString("nombre"));
                marcas.add(marca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marcas;
    }
}
