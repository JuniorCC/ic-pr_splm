package ic.unicamp.splm.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ic.unicamp.splm.core.data.graph.Edge;
import ic.unicamp.splm.core.data.graph.Vertex;
import ic.unicamp.splm.core.data.graph.objs.feature.Feature;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureMode;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureType;
import ic.unicamp.splm.core.data.hash.HashValue;
import ic.unicamp.splm.core.data.types.EdgeType;
import ic.unicamp.splm.core.data.types.HashObjectType;
import ic.unicamp.splm.core.data.types.VertexEnum;
import ic.unicamp.splm.core.util.dir.GraphDir;
import ic.unicamp.splm.core.util.dir.HashMapDir;
import ic.unicamp.splm.core.util.id.IDGenerator;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import lombok.Data;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.json.JSONExporter;
import org.jgrapht.nio.json.JSONImporter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Hashtable;

import static ic.unicamp.splm.core.util.msg.WarnMsgTag.*;

@Data
public class DataManager {

  DefaultDirectedGraph<Vertex, Edge> graph;
  Hashtable<String, HashValue> hashtable;
  Gson gson;

  public DataManager() {
    graph = new DefaultDirectedGraph<>(Edge.class);
    hashtable = new Hashtable<>();
    gson = new GsonBuilder().setPrettyPrinting().create();
    ;
  }

  public void addRootFeature(
      String name,
      HashObjectType hashObjectType,
      FeatureType featureType,
      FeatureMode featureMode) {

    String id = IDGenerator.generateBranchID(name);
    if (hashtable.containsKey(id)) {
      SplMgrLogger.warn(WARN_0__FEATURE_ROOT_ALREADY_EXITS, true);
      return;
    }
    __createFeatureVertex(id, name, featureType, featureMode, hashObjectType);
  }

  public void addFeature(
      String base,
      String name,
      HashObjectType hashObjectType,
      FeatureType featureType,
      FeatureMode featureMode) {
    String id_base = IDGenerator.generateBranchID(base);
    String id = IDGenerator.generateBranchID(name);
    if (!hashtable.containsKey(id_base)) {
      String msg = String.format(WARN_1__FEATURE_PARENT_DOES_NOT_EXITS, name);
      SplMgrLogger.warn(msg, true);
      return;
    }
    if (hashtable.containsKey(id)) {
      String msg = String.format(WARN_2__FEATURE_ALREADY_EXITS, name);
      SplMgrLogger.warn(msg, true);
      return;
    }
    Vertex new_vertex = __createFeatureVertex(id, name, featureType, featureMode, hashObjectType);
    __addFeatureEdge(id_base, new_vertex);
  }

  public void loadData() {
    loadHashMapData();
    loadGraphData();
  }

  private void loadGraphData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __loadGraphData();
    } else {
      SplMgrLogger.warn(WARN_4__OBJ_DIR_GRAPH_FILE_NOT_EXITS, true);
    }
  }

  private void __loadGraphData() {
    JSONImporter<Vertex, Edge> importer = new JSONImporter<>();
    importer.importGraph(graph, GraphDir.get_splm_obj_graph_file__as_file());
  }

  private void loadHashMapData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __loadHashMapData();
    } else {
      SplMgrLogger.warn(WARN_4__OBJ_DIR_HASH_MAP_FILE_NOT_EXITS, true);
    }
  }

  private void __loadHashMapData() {
    try {
      /* Type type = new TypeToken<Hashtable<String, HashValue>>() {}.getType();
      String json = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
      Hashtable<String, HashValue> clonedMap = gson.fromJson(type, new FileReader(filePath));*/
      FileReader fileReader =
          new FileReader(String.valueOf(HashMapDir.get_splm_obj_hash_map_file__as_path()));
      Type type = new TypeToken<Hashtable<String, HashValue>>() {}.getType();
      hashtable = gson.fromJson(fileReader, type);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveData() {
    saveHashMapData();
    saveGraphData();
  }

  private void saveGraphData() {
    if (GraphDir.exists_splm_obj_graph_file()) {
      __saveGraphData();
    } else {
      GraphDir.create_splm_obj_graph_file_with_msg();
      __saveGraphData();
    }
  }

  private void __saveGraphData() {
    // GraphExporter
    JSONExporter<Vertex, Edge> jsonExporter = new JSONExporter<>();
    jsonExporter.exportGraph(graph, GraphDir.get_splm_obj_graph_file__as_file());
    /*exporter.setExportEdgeWeights(true);
    jsonExporter.setEdgeAttributeProvider(e -> {
      Map<String, Attribute> attribs = new HashMap<>();
      attribs.put("label", new DefaultAttribute<String>(e.getLabel(), AttributeType.STRING));
      return attribs;
    });

    FileWriter fileWriter = new FileWriter(String.valueOf(GraphDir.get_splm_obj_graph_file__as_path()));
    gson.toJson(graph, fileWriter);*/
  }

  private void saveHashMapData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __saveHashMapData();
    } else {
      HashMapDir.create_splm_obj_hash_map_file_with_msg();
      __saveHashMapData();
    }
  }

  private void __saveHashMapData() {
    try {
      FileWriter fileWriter =
          new FileWriter(String.valueOf(HashMapDir.get_splm_obj_hash_map_file__as_path()));
      gson.toJson(hashtable, fileWriter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void __addFeatureEdge(String id_base, Vertex new_vertex) {
    __addEdge(id_base, new_vertex, EdgeType.FEATURE);
  }

  private void __addBranchEdge(String id_base, Vertex new_vertex) {
    __addEdge(id_base, new_vertex, EdgeType.BRANCH);
  }

  private void __addEdge(String id_base, Vertex new_vertex, EdgeType edgeType) {
    Vertex base_vertex = __retrieveVertexById(id_base);
    Edge edge = Edge.builder().type(edgeType).build();
    graph.addEdge(base_vertex, new_vertex, edge);
  }

  private Vertex __createFeatureVertex(
      String id,
      String name,
      FeatureType featureType,
      FeatureMode featureMode,
      HashObjectType hashObjectType) {
    Feature feature = Feature.builder().mode(featureMode).type(featureType).name(name).build();
    HashValue hashValue = HashValue.builder().type(hashObjectType).object(feature).build();
    Vertex new_vertex = Vertex.builder().id(id).type(VertexEnum.FEATURE).build();
    hashtable.put(id, hashValue);
    graph.addVertex(new_vertex);
    return new_vertex;
  }

  private Vertex __retrieveVertexById(String id) {
    for (Vertex node : this.graph.vertexSet()) {
      if (node.getId().equals(id)) {
        return node;
      }
    }
    return null;
  }
}
