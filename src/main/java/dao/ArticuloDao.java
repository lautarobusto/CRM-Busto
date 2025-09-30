package dao;

import models.Rubro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Articulo;
import models.Marca;

public class ArticuloDao implements IArticuloDao {
    
    @Override
    public List<Articulo> findByName(String nombre) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.*, m.id as marca_id, m.nombre as marca_nombre, " +
                    "r.id as rubro_id, r.nombre as rubro_nombre " +
                    "FROM articulos a " +
                    "LEFT JOIN marcas m ON a.marca_id = m.id " +
                    "LEFT JOIN rubros r ON a.rubro_id = r.id " +
                    "WHERE LOWER(a.nombre) LIKE LOWER(?) " +
                    "ORDER BY a.nombre";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                articulos.add(mapResultSetToArticulo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }
    
    @Override
    public List<Articulo> findByBrandAndName(Marca marca, String nombre) {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT a.*, m.id as marca_id, m.nombre as marca_nombre, " +
                    "r.id as rubro_id, r.nombre as rubro_nombre " +
                    "FROM articulos a " +
                    "LEFT JOIN marcas m ON a.marca_id = m.id " +
                    "LEFT JOIN rubros r ON a.rubro_id = r.id " +
                    "WHERE a.marca_id = ? AND LOWER(a.nombre) LIKE LOWER(?) " +
                    "ORDER BY a.nombre";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, marca.getId());
            stmt.setString(2, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                articulos.add(mapResultSetToArticulo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }
    
    // Helper method to map ResultSet to Articulo
    private Articulo mapResultSetToArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setId(rs.getInt("id"));
        articulo.setCodigo(rs.getString("codigo"));
        articulo.setPrecioNeto(rs.getDouble("precio_neto"));
        articulo.setPrecioIva(rs.getDouble("precio_iva"));
        articulo.setPrecioCosto(rs.getDouble("precio_costo"));
        articulo.setNombre(rs.getString("nombre"));
        articulo.setDescripcion(rs.getString("descripcion"));

        // Map Marca
        Marca marca = new Marca();
        marca.setId(rs.getInt("marca_id"));
        marca.setNombre(rs.getString("marca_nombre"));
        articulo.setMarca(marca);

        // Map Rubro
        Rubro rubro = new Rubro();
        rubro.setId(rs.getInt("rubro_id"));
        rubro.setNombre(rs.getString("rubro_nombre"));
        articulo.setRubro(rubro);

        return articulo;
    }

    public ResultSet getProductosByMarca(String marcaNombre) throws SQLException {
        String sql = "SELECT a.nombre as Nombre " +
                    "FROM articulos a " +
                    "JOIN marcas m ON a.marca_id = m.id " +
                    "WHERE m.nombre = ? " +
                    "ORDER BY a.nombre";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, marcaNombre);
        return stmt.executeQuery();
    }

    public Articulo getProductoByCodigo(String codigo) throws SQLException {
        String sql = "SELECT a.*, m.id as marca_id, m.nombre as marca_nombre, " +
                    "r.id as rubro_id, r.nombre as rubro_nombre " +
                    "FROM articulos a " +
                    "LEFT JOIN marcas m ON a.marca_id = m.id " +
                    "LEFT JOIN rubros r ON a.rubro_id = r.id " +
                    "WHERE a.codigo = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToArticulo(rs);
            }
        }
        return null;
    }

    public Articulo getProductoByNombre(String nombre) throws SQLException {
        String sql = "SELECT a.*, m.id as marca_id, m.nombre as marca_nombre, " +
                    "r.id as rubro_id, r.nombre as rubro_nombre " +
                    "FROM articulos a " +
                    "LEFT JOIN marcas m ON a.marca_id = m.id " +
                    "LEFT JOIN rubros r ON a.rubro_id = r.id " +
                    "WHERE a.nombre = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToArticulo(rs);
            }
        }
        return null;
    }
}