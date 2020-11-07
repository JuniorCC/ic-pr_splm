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
import ic.unicamp.splm.core.data.types.VertexType;
import ic.unicamp.splm.core.util.dir.GraphDir;
import ic.unicamp.splm.core.util.dir.HashMapDir;
import ic.unicamp.splm.core.util.id.IDGenerator;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;
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
  }

  public void addRootFeature(
      String name,
      HashObjectType hashObjectType,
      FeatureType featureType,
      FeatureMode featureMode) {

    String id = IDGenerator.generateFeatureID(name);
    if (hashtable.containsKey(id)) {
      String msg = String.format(WARN_0__FEATURE_ROOT_ALREADY_EXITS, name);
      SplMgrLogger.warn(msg, true);
      return;
    }
    __createFeatureVertex(id, name, featureType, featureMode, hashObjectType);
    String msg = String.format(INFO_3__ADDED_ROOT_FEATURE, name);
    SplMgrLogger.info(msg, true);
  }

  public void addFeature(
      String base,
      String name,
      HashObjectType hashObjectType,
      FeatureType featureType,
      FeatureMode featureMode) {

    String id_base = IDGenerator.generateFeatureID(base);
    String id = IDGenerator.generateFeatureID(name);
    if (!hashtable.containsKey(id_base)) {
      String msg = String.format(WARN_1__FEATURE_PARENT_DOES_NOT_EXITS, base);
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

    String msg = String.format(INFO_3__ADDED_FEATURE, name);
    SplMgrLogger.info(msg, true);
  }

  public void loadData() {
    loadHashMapData();
    loadGraphData();
  }

  private void loadGraphData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __loadGraphData();
      SplMgrLogger.info(INFO_3__LOADED_GRAPH, true);
    } else {
      SplMgrLogger.warn(WARN_4__OBJ_DIR_GRAPH_FILE_NOT_EXITS, true);
    }
  }

  /*  private void __loadGraphData() {
  JSONImporter<Vertex, Edge> importer = new JSONImporter<>();

  importer.addVertexAttributeConsumer((p, atr_value) -> {
    Vertex vertex = p.getFirst();

    String atr = p.getSecond();
    switch (atr) {
      case "v_id":
        vertex.setId(atr_value.getValue());
        break;
      case "v_type":
        switch (atr_value.getValue()) {
          case "BRANCH":
            vertex.setType(VertexType.BRANCH);
            break;
          case "FEATURE":
            vertex.setType(VertexType.FEATURE);
            break;
          case "PRODUCT":
            vertex.setType(VertexType.PRODUCT);
            break;
          case "MAPPING":
            vertex.setType(VertexType.MAPPING);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + atr_value.getValue());
        }
        break;
    }
   });
  importer.addEdgeAttributeConsumer((p, atr_value) -> {
    Edge edge = p.getFirst();
    String atr = p.getSecond();
    switch (atr) {
      case "e_id":
        edge.setId(atr_value.getValue());
        break;
      case "e_type":
        switch (atr_value.getValue()) {
          case "BRANCH":
            edge.setType(EdgeType.BRANCH);
            break;
          case "FEATURE":
            edge.setType(EdgeType.FEATURE);
            break;
          case "PRODUCT":
            edge.setType(EdgeType.PRODUCT);
            break;
          case "MAPPING":
            edge.setType(EdgeType.MAPPING);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + atr_value.getValue());
        }
        break;
    }
  });
  //Function<String, V> stringVFunction = ;
  Function<String,Vertex> function = id -> Vertex.builder().id(id).type(null).build();
  importer.setVertexFactory(function);
  */
  /*Consumer<Edge> consumer = new Consumer<Edge>() {
    @Override
    public void accept(Edge edge) {

    }
  };
  importer.addEdgeConsumer(consumer);*/
  /*
    //importer.
    File graphFile = GraphDir.get_splm_obj_graph_file__as_file();
    DefaultDirectedGraph<Vertex, Edge> graph2 = new DefaultDirectedGraph<>(Edge.class);;
    importer.importGraph(graph2, graphFile);
  }*/
  private void __loadGraphData() {
    DOTImporter<Vertex, Edge> importer = new DOTImporter<>();
    Function<String, Vertex> importer_v_func =
        (id) -> {
          Vertex vertex = new Vertex();
          vertex.setId(id);
          return vertex;
        };
    importer.setVertexFactory(importer_v_func);
    importer.addVertexAttributeConsumer(
        (p, atr_value) -> {
          Vertex vertex = p.getFirst();
          String atr = p.getSecond();
          switch (atr) {
            case "v_id":
              vertex.setId(atr_value.getValue());
              break;
            case "v_type":
              switch (atr_value.getValue()) {
                case "BRANCH":
                  vertex.setType(VertexType.BRANCH);
                  break;
                case "FEATURE":
                  vertex.setType(VertexType.FEATURE);
                  break;
                case "PRODUCT":
                  vertex.setType(VertexType.PRODUCT);
                  break;
                case "MAPPING":
                  vertex.setType(VertexType.MAPPING);
                  break;
                default:
                  break;
              }
              break;
          }
        });
    importer.addEdgeAttributeConsumer(
        (p, atr_value) -> {
          Edge edge = p.getFirst();
          String atr = p.getSecond();
          switch (atr) {
            case "e_id":
              edge.setId(atr_value.getValue());
              break;
            case "e_type":
              switch (atr_value.getValue()) {
                case "BRANCH":
                  edge.setType(EdgeType.BRANCH);
                  break;
                case "FEATURE":
                  edge.setType(EdgeType.FEATURE);
                  break;
                case "PRODUCT":
                  edge.setType(EdgeType.PRODUCT);
                  break;
                case "MAPPING":
                  edge.setType(EdgeType.MAPPING);
                  break;
                default:
                  break;
              }
              break;
          }
        });

    File graphFile = GraphDir.get_splm_obj_graph_file__as_file();
    DefaultDirectedGraph<Vertex, Edge> graph2 = new DefaultDirectedGraph<>(Edge.class);
    ;
    importer.importGraph(graph2, graphFile);

    graph = graph2;
  }

  private void loadHashMapData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __loadHashMapData();
      SplMgrLogger.info(INFO_3__LOADED_HASHMAP, true);
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

  /*  private void __saveGraphData() {
  // GraphExporter
  JSONExporter<Vertex, Edge> jsonExporter = new JSONExporter<>();

  jsonExporter.setVertexAttributeProvider(v -> {
    Map<String, Attribute> m = new HashMap<>();
    if (v.getId() != null) {
      m.put("v_id", DefaultAttribute.createAttribute(v.getId()));
    }
    if (v.getType() != null) {
      m.put("v_type", DefaultAttribute.createAttribute(v.getType().toString()));
    }
    return m;
  });
  jsonExporter.setEdgeAttributeProvider(v -> {
    Map<String, Attribute> m = new HashMap<>();
    if (v.getId() != null) {
      m.put("e_id", DefaultAttribute.createAttribute(v.getId()));
    }
    if (v.getType() != null) {
      m.put("e_type", DefaultAttribute.createAttribute(v.getType().toString()));
    }
    return m;
  });
  File graphFile = GraphDir.get_splm_obj_graph_file__as_file();

  jsonExporter.exportGraph(graph, graphFile);

  */
  /*exporter.setExportEdgeWeights(true);
  jsonExporter.setEdgeAttributeProvider(e -> {
    Map<String, Attribute> attribs = new HashMap<>();
    attribs.put("label", new DefaultAttribute<String>(e.getLabel(), AttributeType.STRING));
    return attribs;
  });

  FileWriter fileWriter = new FileWriter(String.valueOf(GraphDir.get_splm_obj_graph_file__as_path()));
  gson.toJson(graph, fileWriter);*/
  /*
  }*/
  private void __saveGraphData() {
    DOTExporter<Vertex, Edge> exporter = new DOTExporter<>();

    Function<Vertex, String> exporter_v_func =
        (vertex) -> {
          System.out.println(vertex);
          return vertex.getId();
        };
    exporter.setVertexIdProvider(exporter_v_func);
    exporter.setVertexAttributeProvider(
        v -> {
          Map<String, Attribute> m = new HashMap<>();
          if (v.getId() != null) {
            m.put("v_id", DefaultAttribute.createAttribute(v.getId()));
          }
          if (v.getType() != null) {
            m.put("v_type", DefaultAttribute.createAttribute(v.getType().toString()));
          }
          return m;
        });
    exporter.setEdgeAttributeProvider(
        e -> {
          Map<String, Attribute> m = new HashMap<>();
          if (e.getId() != null) {
            m.put("e_id", DefaultAttribute.createAttribute(e.getId()));
          }
          if (e.getType() != null) {
            m.put("e_type", DefaultAttribute.createAttribute(e.getType().toString()));
          }
          return m;
        });
    File graphFile = GraphDir.get_splm_obj_graph_file__as_file();
    exporter.exportGraph(graph, graphFile);
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
      String json = "";
      json = gson.toJson(hashtable);
      gson.toJson(hashtable, fileWriter);
      fileWriter.flush();
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
    String edge_id = String.format("(%s->%s)", id_base, new_vertex.getId());
    Edge edge = new Edge();
    edge.setId(edge_id);
    edge.setType(edgeType);
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
    Vertex new_vertex = new Vertex();
    new_vertex.setId(id);
    new_vertex.setType(VertexType.FEATURE);

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

  public void showRawFM() {
    __showFM(true);
  }

  public void showFM() {
    __showFM(false);
  }

  public void __showFM(boolean raw) {
    Set<Vertex> vertices =
        graph.vertexSet().stream()
            .filter(vertex -> vertex.getType().equals(VertexType.FEATURE))
            .collect(Collectors.toSet());
    // Graph<Vertex, Edge> subgraph = new AsSubgraph<>(graph, vertices, edges); function is not
    // working
    Graph<Vertex, Edge> subgraph2 = new AsSubgraph<>(graph, vertices);
    Set<Edge> edges_to_remove =
        graph.edgeSet().stream()
            .filter(edge -> !edge.getType().equals(EdgeType.FEATURE))
            .collect(Collectors.toSet());
    subgraph2.removeAllEdges(edges_to_remove);

    List<Vertex> vertexList =
        subgraph2.vertexSet().stream()
            .sorted(Comparator.comparingInt(subgraph2::outDegreeOf))
            .collect(Collectors.toList());
    vertexList.forEach(
        vertex -> {
          Set<Edge> outgoing_Edges = subgraph2.outgoingEdgesOf(vertex);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(String.format("%S ->", vertex.getId()));
          for (Edge item : outgoing_Edges) {
            stringBuilder.append(" ").append(subgraph2.getEdgeTarget(item).getId()).append(",");
          }
          SplMgrLogger.info(stringBuilder.toString(), false);
        });
  }

  public void clearData() {
    hashtable = new Hashtable<>();
    graph = new DefaultDirectedGraph<>(Edge.class);
    ;
  }
}
