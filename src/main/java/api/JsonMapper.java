package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Articulo;
import models.Marca;
import models.Rubro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonMapper {

    private final ObjectMapper objectMapper;

    public JsonMapper() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Articulo> readArticulos() throws IOException {
        Path filePath = Paths.get("src/main/resources/Articulos.json");
        String jsonContent = Files.readString(filePath);
        JsonNode rootNode = objectMapper.readTree(jsonContent);

        List<Articulo> articulos = new ArrayList<>();
        JsonNode dataArray = rootNode.get("Data");

        if (dataArray != null && dataArray.isArray()) {
            for (JsonNode item : dataArray) {
                Articulo articulo = mapToArticulo(item);
                articulos.add(articulo);
            }
        }

        return articulos;
    }

    private Articulo mapToArticulo(JsonNode item) {
        JsonNode articuloNode = item.get("Articulo");

        Articulo articulo = new Articulo();
        articulo.setId(articuloNode.get("IdArticulo").asInt());
        articulo.setCodigo(articuloNode.get("Codigo").asText());
        articulo.setNombre(articuloNode.get("Nombre").asText());
        articulo.setDescripcion(articuloNode.get("Descripcion").asText());

        // Map price from PreciosArticulo array (taking first price)
        JsonNode preciosArray = articuloNode.get("PreciosArticulo");
        if (preciosArray != null && preciosArray.isArray() && preciosArray.size() > 0) {
            JsonNode firstPrice = preciosArray.get(0);
            articulo.setPrecioNeto(firstPrice.get("PrecioNeto").asDouble());
            articulo.setPrecioIva(firstPrice.get("PrecioNeto").asDouble()*1.21);
            articulo.setPrecioCosto(firstPrice.get("PrecioNeto").asDouble()*1.21/2);
        }

        // Map Marca
        JsonNode marcaNode = articuloNode.get("Marca");
        if (marcaNode != null) {
            Marca marca = new Marca();
            marca.setId(marcaNode.get("Id").asInt());
            marca.setNombre(marcaNode.get("Nombre").asText());
            articulo.setMarca(marca);
        }

        // Map Rubro
        JsonNode rubroNode = articuloNode.get("Rubro");
        if (rubroNode != null) {
            Rubro rubro = new Rubro();
            rubro.setId(rubroNode.get("Id").asInt());
            rubro.setNombre(rubroNode.get("Nombre").asText());
            articulo.setRubro(rubro);
        }

        return articulo;
    }
}
