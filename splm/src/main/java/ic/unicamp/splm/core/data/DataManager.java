package ic.unicamp.splm.core.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ic.unicamp.splm.core.data.graph.Edge;
import ic.unicamp.splm.core.data.graph.Vertex;
import ic.unicamp.splm.core.data.graph.objs.branch.Branch;
import ic.unicamp.splm.core.data.graph.objs.feature.Feature;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureMode;
import ic.unicamp.splm.core.data.graph.objs.feature.FeatureType;
import ic.unicamp.splm.core.data.graph.objs.map.Mapping;
import ic.unicamp.splm.core.data.graph.objs.map.MappingType;
import ic.unicamp.splm.core.data.graph.objs.product.Product;
import ic.unicamp.splm.core.data.hash.HashValue;
import ic.unicamp.splm.core.data.types.EdgeType;
import ic.unicamp.splm.core.data.types.HashObjectType;
import ic.unicamp.splm.core.data.types.VertexType;
import ic.unicamp.splm.core.util.dir.GraphDir;
import ic.unicamp.splm.core.util.dir.HashMapDir;
import ic.unicamp.splm.core.util.id.IDGenerator;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import ic.unicamp.splm.core.vc.git.GitMgr;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.Graph;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_1__DATA_INCONSISTENCY_BETWEEN_HASHMAP_AND_GRAPH;
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

  public void addRootFeature(String name, FeatureType featureType, FeatureMode featureMode) {

    String id_root_feature = IDGenerator.generateFeatureID(name);

    if (hashtable.containsKey(id_root_feature)) {
      String msg = String.format(WARN_0__FEATURE_ROOT_ALREADY_EXITS, name);
      SplMgrLogger.warn(msg, true);
      return;
    }
    __createFeatureVertex(id_root_feature, name, featureType, featureMode);
    String msg = String.format(INFO_3__ADDED_ROOT_FEATURE, name);
    SplMgrLogger.info(msg, true);
  }

  public void addFeature(
      String base, String name, FeatureType featureType, FeatureMode featureMode) {

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

    Vertex new_vertex = __createFeatureVertex(id, name, featureType, featureMode);
    Vertex base_vertex = __retrieveVertexById(id_base);
    __addFeatureEdge(base_vertex, new_vertex);

    // or property
    if (featureType.equals(FeatureType.OR)) {
      Feature parent = (Feature) hashtable.get(id_base).getObject();
      parent.setOrParent(true);
    }

    String msg = String.format(INFO_3__ADDED_FEATURE, name);
    SplMgrLogger.info(msg, true);
  }

  public void loadData() {
    loadHashMapData();
    loadGraphData();
  }

  private void __addFeatureEdge(Vertex base_vertex, Vertex new_vertex) {

    __addEdge(base_vertex, new_vertex, EdgeType.FEATURE);
  }

  private void __addBranchEdge(Vertex base_vertex, Vertex new_vertex) {
    __addEdge(base_vertex, new_vertex, EdgeType.BRANCH);
  }

  private void __addMappingEdge(Vertex base_vertex, Vertex new_vertex) {
    __addEdge(base_vertex, new_vertex, EdgeType.MAPPING);
  }

  private void __addEdge(Vertex base_vertex, Vertex new_vertex, EdgeType edgeType) {

    String edge_id = String.format("(%s->%s)", base_vertex.getId(), new_vertex.getId());
    Edge edge = new Edge();
    edge.setId(edge_id);
    edge.setType(edgeType);
    graph.addEdge(base_vertex, new_vertex, edge);
  }

  private void loadGraphData() {
    if (HashMapDir.exists_splm_obj_hash_map_file()) {
      __loadGraphData();
      SplMgrLogger.info(INFO_3__LOADED_GRAPH, true);
    } else {
      SplMgrLogger.warn(WARN_4__OBJ_DIR_GRAPH_FILE_NOT_EXITS, true);
    }
  }

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
      gson.toJson(hashtable, fileWriter);
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Vertex __createFeatureVertex(
      String id, String name, FeatureType featureType, FeatureMode featureMode) {
    Feature feature =
        Feature.builder().mode(featureMode).type(featureType).name(name).orParent(false).build();
    HashValue hashValue = HashValue.builder().type(HashObjectType.FEATURE).object(feature).build();
    Vertex new_vertex = new Vertex();
    new_vertex.setId(id);
    new_vertex.setType(VertexType.FEATURE);

    hashtable.put(id, hashValue);
    graph.addVertex(new_vertex);
    return new_vertex;
  }

  private Vertex __createBranchVertex(String id, String name) {
    Branch branch = Branch.builder().name(name).build();
    HashValue hashValue = HashValue.builder().type(HashObjectType.BRANCH).object(branch).build();
    Vertex new_vertex = new Vertex();
    new_vertex.setId(id);
    new_vertex.setType(VertexType.BRANCH);

    hashtable.put(id, hashValue);
    graph.addVertex(new_vertex);
    return new_vertex;
  }

  private Vertex __createMappingVertex(String id, String name) {
    Mapping mapping = Mapping.builder().name(name).type(MappingType.Feature_Branch).build();
    HashValue hashValue = HashValue.builder().type(HashObjectType.MAPPING).object(mapping).build();
    Vertex new_vertex = new Vertex();
    new_vertex.setId(id);
    new_vertex.setType(VertexType.MAPPING);

    hashtable.put(id, hashValue);
    graph.addVertex(new_vertex);
    return new_vertex;
  }

  private Vertex __createProductVertex(String id, String name) {
    Product product = Product.builder().name(name).build();
    HashValue hashValue = HashValue.builder().type(HashObjectType.PRODUCT).object(product).build();
    Vertex new_vertex = new Vertex();
    new_vertex.setId(id);
    new_vertex.setType(VertexType.PRODUCT);

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

  public void __showFM(boolean showData) {
    Graph<Vertex, Edge> fm_subgraph = reduceGraphToFMGraph();
    __showSubGraph(fm_subgraph, showData, HashObjectType.FEATURE);
  }

  @NotNull
  private Graph<Vertex, Edge> reduceGraphToFMGraph() {
    Set<Vertex> vertices =
        graph.vertexSet().stream()
            .filter(vertex -> vertex.getType().equals(VertexType.FEATURE))
            .collect(Collectors.toSet());

    Graph<Vertex, Edge> fm_subgraph = new AsSubgraph<>(graph, vertices);
    Set<Edge> edges_to_remove =
        graph.edgeSet().stream()
            .filter(edge -> !edge.getType().equals(EdgeType.FEATURE))
            .collect(Collectors.toSet());
    fm_subgraph.removeAllEdges(edges_to_remove);
    return fm_subgraph;
  }

  @NotNull
  private Graph<Vertex, Edge> reduceGraphToBrGraph() {
    Set<Vertex> vertices =
        graph.vertexSet().stream()
            .filter(vertex -> vertex.getType().equals(VertexType.BRANCH))
            .collect(Collectors.toSet());

    Graph<Vertex, Edge> fm_subgraph = new AsSubgraph<>(graph, vertices);
    Set<Edge> edges_to_remove =
        graph.edgeSet().stream()
            .filter(edge -> !edge.getType().equals(EdgeType.BRANCH))
            .collect(Collectors.toSet());
    fm_subgraph.removeAllEdges(edges_to_remove);
    return fm_subgraph;
  }

  public void clearData() {
    hashtable = new Hashtable<>();
    graph = new DefaultDirectedGraph<>(Edge.class);
    ;
  }

  private void __genMapAndBrVertexes(Graph<Vertex, Edge> subgraph) {
    BreadthFirstIterator<Vertex, Edge> bfs = new BreadthFirstIterator<>(subgraph);
    while (bfs.hasNext()) {
      Vertex vertex = bfs.next();
      Vertex parentVertex = bfs.getParent(vertex);

      Feature parentFeature = __retrieveFeature(parentVertex);
      Feature feature = __retrieveFeature(vertex);
      __createMapAndBrVertexes(parentVertex, parentFeature, vertex, feature);
    }
  }

  private Feature __retrieveFeature(Vertex vertex) {
    if (vertex != null) {
      if (vertex.getType() == VertexType.FEATURE) {
        HashValue hashValue = hashtable.get(vertex.getId());
        if (hashValue.getType() == HashObjectType.FEATURE) {
          return (Feature) hashValue.getObject();
        } else {
          SplMgrLogger.error(ERR_1__DATA_INCONSISTENCY_BETWEEN_HASHMAP_AND_GRAPH, true);
        }
      } else {
        SplMgrLogger.warn(WARN_1__SUBGRAPH_MALFORMED, true);
      }
    }
    return null;
  }

  private void __createMapAndBrVertexes(
      Vertex parentVertex, Feature parentFeature, Vertex vertex, Feature feature) {
    String name = feature.getName();

    String id_map = IDGenerator.generateMappingID(name);
    String id_branch = IDGenerator.generateBranchID(name);


    Vertex map_vertex = __createMappingVertex(id_map, name);
    Vertex br_vertex = __createBranchVertex(id_branch, id_branch);

    __addMappingEdge(map_vertex, vertex);
    __addMappingEdge(map_vertex, br_vertex);

    if (feature.isOrParent()) {
      String id_vBranch = IDGenerator.generateVBranchID(name);
      Vertex vbr_vertex = __createBranchVertex(id_vBranch, id_vBranch);
      __addMappingEdge(map_vertex, vbr_vertex);

      __addBranchEdge(br_vertex, vbr_vertex);//adding edge between branches
    }

    if (parentVertex != null) {
      String parentName = parentFeature.getName();
      Vertex p_vertex;
      if (parentFeature.isOrParent()) {
        String parent_id_vBranch = IDGenerator.generateVBranchID(parentName);
        p_vertex = __retrieveVertexById(parent_id_vBranch);
      } else {
        String parent_id_branch = IDGenerator.generateBranchID(parentName);
        p_vertex = __retrieveVertexById(parent_id_branch);
      }
      __addBranchEdge(p_vertex, br_vertex);
    }
  }

  public void fulfillGraph() {
    Graph<Vertex, Edge> fm_subgraph = reduceGraphToFMGraph();
    __genMapAndBrVertexes(fm_subgraph);
  }

  public void showBrModel() {
    Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();
    __showSubGraph(br_subgraph, false, HashObjectType.BRANCH);
  }

  public void showRawBrModel() {
    Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();
    __showSubGraph(br_subgraph, true, HashObjectType.BRANCH);
  }

  private void __showSubGraph(
      Graph<Vertex, Edge> br_subgraph, boolean showData, HashObjectType hashObjectType) {
    List<Vertex> vertexList =
        br_subgraph.vertexSet().stream()
            .sorted(Comparator.comparingInt(br_subgraph::outDegreeOf))
            .collect(Collectors.toList());
    vertexList.forEach(
        vertex -> {
          Set<Edge> outgoing_Edges = br_subgraph.outgoingEdgesOf(vertex);
          StringBuilder stringBuilder = new StringBuilder();
          if (showData) {
            __showRawData(stringBuilder, vertex.getId(), hashObjectType);
          }
          stringBuilder.append(String.format("%S ->", vertex.getId()));
          for (Edge item : outgoing_Edges) {
            stringBuilder.append(" ").append(br_subgraph.getEdgeTarget(item).getId()).append(",");
          }
          SplMgrLogger.info(stringBuilder.toString(), false);
        });
  }

  private void __showRawData(
      StringBuilder stringBuilder, String id, HashObjectType hashObjectType) {
    HashValue hashValue = hashtable.get(id);
    stringBuilder.append(String.format("%S:", id));
    stringBuilder.append("\n");
    switch (hashObjectType) {
      case FEATURE:
        {
          Feature feature = (Feature) hashValue.getObject();
          stringBuilder.append(String.format("Name %S", feature.getName()));
          stringBuilder.append(String.format("Type %S:", feature.getType()));
          stringBuilder.append(String.format("Mode %S:", feature.getMode()));
          stringBuilder.append("\n");
          break;
        }
      case MAPPING:
        {
          Mapping feature = (Mapping) hashValue.getObject();
          stringBuilder.append(String.format("Name %S", feature.getName()));
          stringBuilder.append("\n");
          break;
        }
      case PRODUCT:
        {
          Product feature = (Product) hashValue.getObject();
          stringBuilder.append(String.format("Name %S", feature.getName()));
          stringBuilder.append("\n");
          break;
        }
      case BRANCH:
        {
          Branch feature = (Branch) hashValue.getObject();
          stringBuilder.append(String.format("Name %S", feature.getName()));
          stringBuilder.append("\n");
          break;
        }
    }
  }

  public void genGitBranches(GitMgr gitMgr) {
    Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();
    __generateGitBranches(br_subgraph, gitMgr);
  }

  private void __generateGitBranches(Graph<Vertex, Edge> br_subgraph, GitMgr gitMgr) {
    BreadthFirstIterator<Vertex, Edge> bfs = new BreadthFirstIterator<>(br_subgraph);
    while (bfs.hasNext()) {
      Vertex vertex = bfs.next();
      Vertex parentVertex = bfs.getParent(vertex);
      Branch parentBranch = __retrieveBranch(parentVertex);
      Branch branch = __retrieveBranch(vertex);
      __createGitBranch(parentBranch, branch, gitMgr);
    }
  }

  private void __createGitBranch(Branch parentBranch, Branch branch, GitMgr gitMgr) {
    if (parentBranch == null) {
      gitMgr.createBaseBranch(branch.getName());
    } else {
      gitMgr.createBranch(parentBranch.getName(), branch.getName());
    }
  }

  private Branch __retrieveBranch(Vertex vertex) {
    if (vertex != null) {
      if (vertex.getType() == VertexType.BRANCH) {
        HashValue hashValue = hashtable.get(vertex.getId());
        if (hashValue.getType() == HashObjectType.BRANCH) {
          return (Branch) hashValue.getObject();
        } else {
          SplMgrLogger.error(ERR_1__DATA_INCONSISTENCY_BETWEEN_HASHMAP_AND_GRAPH, true);
        }
      } else {
        SplMgrLogger.warn(WARN_1__SUBGRAPH_MALFORMED, true);
      }
    }
    return null;
  }
}
