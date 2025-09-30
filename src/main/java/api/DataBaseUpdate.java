package api;

import dao.DatabaseConnection;
import models.Articulo;
import models.Marca;
import models.Rubro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataBaseUpdate {

    public void updateArticulos() throws Exception {
        updateArticulos(null);
    }

    public void updateArticulos(JsonDownloader.DownloadListener listener) throws Exception {
        // Download JSON file if needed (doesn't exist or older than 5 days)
        JsonDownloader downloader = new JsonDownloader();
        downloader.downloadFile(listener);

        // Parse and insert into database
        JsonMapper mapper = new JsonMapper();
        List<Articulo> articulos = mapper.readArticulos();
        insertArticulos(articulos);
    }

    private void insertArticulos(List<Articulo> articulos) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            for (Articulo articulo : articulos) {
                // Insert or get Marca ID
                int marcaId = insertOrGetMarca(conn, articulo.getMarca());

                // Insert or get Rubro ID
                int rubroId = insertOrGetRubro(conn, articulo.getRubro());

                // Insert or update Articulo
                insertOrUpdateArticulo(conn, articulo, marcaId, rubroId);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private int insertOrGetMarca(Connection conn, Marca marca) throws SQLException {
        // Check if marca exists
        String selectSql = "SELECT id FROM marcas WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setString(1, marca.getNombre());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        // Insert new marca
        String insertSql = "INSERT INTO marcas (id, nombre) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, marca.getId());
            stmt.setString(2, marca.getNombre());
            stmt.executeUpdate();
            return marca.getId();
        }
    }

    private int insertOrGetRubro(Connection conn, Rubro rubro) throws SQLException {
        // Check if rubro exists
        String selectSql = "SELECT id FROM rubros WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setString(1, rubro.getNombre());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        // Insert new rubro
        String insertSql = "INSERT INTO rubros (id, nombre) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
            stmt.setInt(1, rubro.getId());
            stmt.setString(2, rubro.getNombre());
            stmt.executeUpdate();
            return rubro.getId();
        }
    }

    private void insertOrUpdateArticulo(Connection conn, Articulo articulo, int marcaId, int rubroId) throws SQLException {
        // Check if articulo exists
        String selectSql = "SELECT id FROM articulos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSql)) {
            stmt.setInt(1, articulo.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Update existing articulo
                String updateSql = "UPDATE articulos SET codigo = ?, precio_neto = ?, precio_iva = ?, " +
                                 "precio_costo = ?, nombre = ?, descripcion = ?, marca_id = ?, rubro_id = ? " +
                                 "WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setString(1, articulo.getCodigo());
                    updateStmt.setDouble(2, articulo.getPrecioNeto());
                    updateStmt.setDouble(3, articulo.getPrecioIva());
                    updateStmt.setDouble(4, articulo.getPrecioCosto());
                    updateStmt.setString(5, articulo.getNombre());
                    updateStmt.setString(6, articulo.getDescripcion());
                    updateStmt.setInt(7, marcaId);
                    updateStmt.setInt(8, rubroId);
                    updateStmt.setInt(9, articulo.getId());
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new articulo
                String insertSql = "INSERT INTO articulos (id, codigo, precio_neto, precio_iva, precio_costo, " +
                                 "nombre, descripcion, marca_id, rubro_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, articulo.getId());
                    insertStmt.setString(2, articulo.getCodigo());
                    insertStmt.setDouble(3, articulo.getPrecioNeto());
                    insertStmt.setDouble(4, articulo.getPrecioIva());
                    insertStmt.setDouble(5, articulo.getPrecioCosto());
                    insertStmt.setString(6, articulo.getNombre());
                    insertStmt.setString(7, articulo.getDescripcion());
                    insertStmt.setInt(8, marcaId);
                    insertStmt.setInt(9, rubroId);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}
