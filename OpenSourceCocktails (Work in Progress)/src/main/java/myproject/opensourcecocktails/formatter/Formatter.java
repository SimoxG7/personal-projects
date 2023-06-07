package myproject.opensourcecocktails.formatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Formatter {

  public static void JSONtoCSV(String source, String destination) {
    try {
      JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/" + source));
      JsonNode firstObject = jsonTree.elements().next();
      CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
      firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
      CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
      CsvMapper csvMapper = new CsvMapper();
      csvMapper.writerFor(JsonNode.class)
          .with(csvSchema)
          .with(SerializationFeature.INDENT_OUTPUT)
          .writeValue(new File("src/main/resources/" + destination), jsonTree);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void CSVtoJSON(String source, String destination) {
    try {
      File input = new File("src/main/resources/" + source);
      CsvSchema csv = CsvSchema.emptySchema().withHeader();
      CsvMapper csvMapper = new CsvMapper();
      MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv).readValues(input);
      List<Map<?, ?>> list = mappingIterator.readAll();
      new ObjectMapper()
          .configure(SerializationFeature.INDENT_OUTPUT, true)
          .writeValue(new File("src/main/resources/" + destination), list);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}












