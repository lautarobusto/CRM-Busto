/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package api;

import java.io.IOException;
import java.util.List;
import models.Articulo;

/**
 *
 * @author lauta
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            // Show database location
            String userHome = System.getProperty("user.home");
            String dbPath = userHome + java.io.File.separator + ".consultorimperdiel" + java.io.File.separator + "ConsultorImperdielDB.sqlite";
            System.out.println("Database location: " + dbPath);

            // Check if database file exists
            java.io.File dbFile = new java.io.File(dbPath);
            System.out.println("Database file exists: " + dbFile.exists());
            if (dbFile.exists()) {
                System.out.println("Database file size: " + dbFile.length() + " bytes");
            }

            DataBaseUpdate dataBaseUpdate = new DataBaseUpdate();
            //dataBaseUpdate.updateArticulos();

            // Query first article after processing
            System.out.println("\nQuerying first article from database:");
            dao.DatabaseConnection dbConn = dao.DatabaseConnection.getInstance();
            try (java.sql.Connection conn = dbConn.getConnection()) {
                String sql = "SELECT a.id, a.codigo, a.nombre, a.precio_neto, m.nombre as marca_nombre, r.nombre as rubro_nombre " +
                           "FROM articulos a " +
                           "JOIN marcas m ON a.marca_id = m.id " +
                           "JOIN rubros r ON a.rubro_id = r.id " +
                           "LIMIT 1";

                try (java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
                    java.sql.ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        System.out.println("First article found:");
                        System.out.println("  ID: " + rs.getInt("id"));
                        System.out.println("  Código: " + rs.getString("codigo"));
                        System.out.println("  Nombre: " + rs.getString("nombre"));
                        System.out.println("  Precio Neto: " + rs.getDouble("precio_neto"));
                        System.out.println("  Marca: " + rs.getString("marca_nombre"));
                        System.out.println("  Rubro: " + rs.getString("rubro_nombre"));
                    } else {
                        System.out.println("No articles found in database!");
                    }
                }

                // Count total records
                String countSql = "SELECT COUNT(*) as total FROM articulos";
                try (java.sql.PreparedStatement countStmt = conn.prepareStatement(countSql)) {
                    java.sql.ResultSet countRs = countStmt.executeQuery();
                    if (countRs.next()) {
                        System.out.println("Total articles in database: " + countRs.getInt("total"));
                    }
                }
            }

        } catch (Exception ex) {
            System.getLogger(NewMain.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            ex.printStackTrace();
        }


    }
    
}
