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
import org.jgrapht.traverse.DepthFirstIterator;

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

        String root_feature_id = IDGenerator.generateFeatureID(name);
        setRoot(name);

        if (hashtable.containsKey(root_feature_id)) {
            String msg = String.format(WARN_0__FEATURE_ROOT_ALREADY_EXITS, name);
            SplMgrLogger.warn(msg, true);
            return;
        }
        __createFeatureVertex(root_feature_id, name, featureType, featureMode);
        String msg = String.format(INFO_3__ADDED_ROOT_FEATURE, name);
        SplMgrLogger.info(msg, true);
    }

    private String getRoot() {
        if (hashtable.containsKey("root")) {
            return (String) hashtable.get("root").getObject();
        }
        return null;
    }

    private void setRoot(String name) {
        hashtable.put("root", HashValue.builder().object(name).type(HashObjectType.ROOT).build());
    }

    public void addFeature(
            String base, String name, FeatureType featureType, FeatureMode featureMode) {

        String base_feature_id = IDGenerator.generateFeatureID(base);
        String feature_id = IDGenerator.generateFeatureID(name);

        if (!hashtable.containsKey(base_feature_id)) {
            String msg = String.format(WARN_1__FEATURE_PARENT_DOES_NOT_EXITS, base);
            SplMgrLogger.warn(msg, true);
            return;
        }
        if (hashtable.containsKey(feature_id)) {
            String msg = String.format(WARN_2__FEATURE_ALREADY_EXITS, name);
            SplMgrLogger.warn(msg, true);
            return;
        }

        Vertex new_vertex = __createFeatureVertex(feature_id, name, featureType, featureMode);
        Vertex base_vertex = __retrieveVertexById(base_feature_id);
        if (base_vertex != null) {
            __addFeatureEdge(base_vertex, new_vertex);
        }

        // or property
        if (featureType.equals(FeatureType.OR)) {
            Feature parent = (Feature) hashtable.get(base_feature_id).getObject();
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

    private void __addProductEdge(Vertex base_vertex, Vertex new_vertex) {

        __addEdge(base_vertex, new_vertex, EdgeType.PRODUCT);
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
            FileReader fileReader =
                    new FileReader(String.valueOf(HashMapDir.get_splm_obj_hash_map_file__as_path()));
            Type type = new TypeToken<Hashtable<String, HashValue>>() {
            }.getType();
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
            String feature_id, String name, FeatureType featureType, FeatureMode featureMode) {
        Feature feature =
                Feature.builder().mode(featureMode).type(featureType).name(name).orParent(false).build();
        HashValue hashValue = HashValue.builder().type(HashObjectType.FEATURE).object(feature).build();
        hashtable.put(feature_id, hashValue);

        Vertex new_vertex = new Vertex();
        new_vertex.setId(feature_id);
        new_vertex.setType(VertexType.FEATURE);
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
        return __retrieveVertexById(id,graph);
/*        for (Vertex node : graph.vertexSet()) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;*/
    }
    private Vertex __retrieveVertexById(String id, Graph<Vertex, Edge> subgraph) {
        for (Vertex node : subgraph.vertexSet()) {
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
        String root_id = IDGenerator.generateFeatureID(getRoot());

        __showSubGraph(fm_subgraph, showData, HashObjectType.FEATURE, root_id);
    }

    @NotNull
    private Graph<Vertex, Edge> reduceGraphToFMGraph() {
        Set<Vertex> vertices =
                graph.vertexSet().stream()
                        .filter(vertex -> vertex.getType().equals(VertexType.FEATURE))
                        .collect(Collectors.toSet());

        Graph<Vertex, Edge> fm_subgraph = new AsSubgraph<>(graph, vertices);
        Set<Edge> edges_to_remove =
                fm_subgraph.edgeSet().stream()
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
                fm_subgraph.edgeSet().stream()
                        .filter(edge -> !edge.getType().equals(EdgeType.BRANCH))
                        .collect(Collectors.toSet());
        fm_subgraph.removeAllEdges(edges_to_remove);
        return fm_subgraph;
    }

    public void clearData() {
        hashtable = new Hashtable<>();
        graph = new DefaultDirectedGraph<>(Edge.class);
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

            __addBranchEdge(br_vertex, vbr_vertex); // adding edge between branches
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
        String root_id = IDGenerator.generateBranchID(getRoot());
        __showSubGraph(br_subgraph, false, HashObjectType.BRANCH, root_id);
    }

    public void showRawBrModel() {
        Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();
        String root_id = IDGenerator.generateBranchID(getRoot());
        __showSubGraph(br_subgraph, true, HashObjectType.BRANCH, root_id);
    }

    private void __showSubGraph(
            Graph<Vertex, Edge> br_subgraph, boolean showData, HashObjectType hashObjectType, String rootId) {

        Vertex root = __retrieveVertexById(rootId);
        BreadthFirstIterator<Vertex, Edge> bfs = new BreadthFirstIterator<>(br_subgraph, root);
        while (bfs.hasNext()) {
            Vertex vertex = bfs.next();
            //Vertex parentVertex = bfs.getParent(vertex);
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
        }
    }

    private void __showRawData(
            StringBuilder stringBuilder, String id, HashObjectType hashObjectType) {
        HashValue hashValue = hashtable.get(id);
        stringBuilder.append(String.format("%S:", id));
        stringBuilder.append("\n");
        switch (hashObjectType) {
            case FEATURE: {
                Feature feature = (Feature) hashValue.getObject();
                stringBuilder.append(String.format("Name %S", feature.getName()));
                stringBuilder.append(String.format("Type %S:", feature.getType()));
                stringBuilder.append(String.format("Mode %S:", feature.getMode()));
                stringBuilder.append("\n");
                break;
            }
            case MAPPING: {
                Mapping feature = (Mapping) hashValue.getObject();
                stringBuilder.append(String.format("Name %S", feature.getName()));
                stringBuilder.append("\n");
                break;
            }
            case PRODUCT: {
                Product feature = (Product) hashValue.getObject();
                stringBuilder.append(String.format("Name %S", feature.getName()));
                stringBuilder.append("\n");
                break;
            }
            case BRANCH: {
                Branch feature = (Branch) hashValue.getObject();
                stringBuilder.append(String.format("Name %S", feature.getName()));
                stringBuilder.append("\n");
                break;
            }
        }
    }

    public void genGitBranches(GitMgr gitMgr) {
        Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();
        String rootId = IDGenerator.generateBranchID(getRoot());
        __generateGitBranches(br_subgraph, gitMgr, rootId);
    }

    private void __generateGitBranches(Graph<Vertex, Edge> br_subgraph, GitMgr gitMgr, String rootId) {
        Vertex root = __retrieveVertexById(rootId);
        DepthFirstIterator<Vertex, Edge> dfs =
                new DepthFirstIterator<>(br_subgraph, root);
        while (dfs.hasNext()) {
            Vertex vertex = dfs.next();
            Vertex parentVertex = null;
            for (Edge item : br_subgraph.incomingEdgesOf(vertex)) {
                if (item.getType().equals(EdgeType.BRANCH)) {
                    parentVertex = br_subgraph.getEdgeSource(item);
                    break;
                }
            }
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

    public void showPrM() {
        Graph<Vertex, Edge> pr_subgraph = reduceGraphToPRGraph();
        __showProducts(pr_subgraph, false, HashObjectType.PRODUCT);
    }

    private void __showProducts(Graph<Vertex, Edge> pr_subgraph, Boolean showData, HashObjectType product) {
        StringBuilder stringBuilder = new StringBuilder();
        pr_subgraph.vertexSet().forEach(vertex -> {
            if (vertex.getType().equals(VertexType.PRODUCT)) {
                __printData(pr_subgraph, stringBuilder, vertex);
            }
        });
        SplMgrLogger.info(stringBuilder.toString(), true);
    }

    private void __printData(Graph<Vertex, Edge> pr_subgraph, StringBuilder stringBuilder, Vertex vertex) {
        stringBuilder.append(vertex.getId()).append(" -> ");
        Set<Edge> edges = pr_subgraph.outgoingEdgesOf(vertex);
        for (Edge e : edges) {
            stringBuilder.append(pr_subgraph.getEdgeTarget(e).getId()).append(", ");
        }
        stringBuilder.append("\n");
    }

    private Graph<Vertex, Edge> reduceGraphToPRGraph() {
        Set<Edge> edges =
                graph.edgeSet().stream()
                        .filter(edge -> edge.getType().equals(EdgeType.PRODUCT))
                        .collect(Collectors.toSet());
        Set<Vertex> vertices = new LinkedHashSet<>();
        for (Edge e : edges) {
            vertices.add(graph.getEdgeTarget(e));
        }
        Set<Vertex> vertexSet =
                graph.vertexSet().stream()
                        .filter(vertex -> vertex.getType().equals(VertexType.PRODUCT))
                        .collect(Collectors.toSet());
        vertices.addAll(vertexSet);

        Graph<Vertex, Edge> pr_subgraph = new AsSubgraph<>(graph, vertices);
        Set<Edge> edges_to_remove =
                pr_subgraph.edgeSet().stream()
                        .filter(edge -> !edge.getType().equals(EdgeType.PRODUCT))
                        .collect(Collectors.toSet());
        pr_subgraph.removeAllEdges(edges_to_remove);
        return pr_subgraph;
    }

    public boolean verifyFeature(String feature) {
        String id_feature = IDGenerator.generateFeatureID(feature);
        return hashtable.containsKey(id_feature);
    }

    public void addProduct(String name, List<String> features) {
        String id_product = IDGenerator.generateProductID(name);
        if (hashtable.containsKey(id_product)) {
            SplMgrLogger.warn(WAR_0__PRODUCT_NAME_ALREADY_EXITS, true);
        } else {
            Vertex product = __createProductVertex(id_product, name);
            for (String feature : features) {
                String feature_id = IDGenerator.generateFeatureID(feature);
                Vertex featureVertex = __retrieveVertexById(feature_id);
                if (featureVertex != null) {
                    __addProductEdge(product, featureVertex);
                    SplMgrLogger.info(
                            String.format(INFO_0__ADDED_PRODUCT_RELATION_TO_FEATURE, feature_id), true);
                } else {
                    SplMgrLogger.warn(String.format(WAR_0__FEATURE_NOT_FOUND, feature_id), true);
                }
            }
        }
    }

    public void showMpM() {
        Graph<Vertex, Edge> pr_subgraph = reduceGraphToMpGraph();
        __showMapping(pr_subgraph, false, HashObjectType.MAPPING);
    }

    private void __showMapping(Graph<Vertex, Edge> pr_subgraph, Boolean showData, HashObjectType mapping) {
        StringBuilder stringBuilder = new StringBuilder();
        pr_subgraph.vertexSet().forEach(vertex -> {
            if (vertex.getType().equals(VertexType.MAPPING)) {
                __printData(pr_subgraph, stringBuilder, vertex);
            }
        });
        SplMgrLogger.info(stringBuilder.toString(), true);
    }

    private Graph<Vertex, Edge> reduceGraphToMpGraph() {
        Set<Edge> edges =
                graph.edgeSet().stream()
                        .filter(edge -> edge.getType().equals(EdgeType.MAPPING))
                        .collect(Collectors.toSet());
        Set<Vertex> vertices = new LinkedHashSet<>();
        for (Edge e : edges) {
            vertices.add(graph.getEdgeTarget(e));
        }
        Set<Vertex> vertexSet =
                graph.vertexSet().stream()
                        .filter(vertex -> vertex.getType().equals(VertexType.MAPPING))
                        .collect(Collectors.toSet());
        vertices.addAll(vertexSet);

        Graph<Vertex, Edge> pr_subgraph = new AsSubgraph<>(graph, vertices);
        Set<Edge> edges_to_remove =
                pr_subgraph.edgeSet().stream()
                        .filter(edge -> !edge.getType().equals(EdgeType.MAPPING))
                        .collect(Collectors.toSet());
        pr_subgraph.removeAllEdges(edges_to_remove);
        return pr_subgraph;
    }

    public void checkConflict(GitMgr gitMgr) {
        String branch_name = gitMgr.getCurrentBranch();
        __checkConflicts(gitMgr, branch_name);
    }

    private void __checkConflicts(GitMgr gitMgr, String branch_name) {
        Vertex vertex = __retrieveVertex(branch_name);
        if (vertex != null) {
            Set<Vertex> features = __retrieveFeatures(vertex.getId());
            Set<Vertex> products = __retrieveProducts(features);
            for (Vertex v_product : products) {
                __CheckConflictsByProduct(gitMgr, branch_name, v_product);
            }
        } else {
            //msg we could find the branch with that name
        }
    }

    private void __CheckConflictsByProduct(GitMgr gitMgr, String branch_name, Vertex v_product) {
        Set<Vertex> branches = __retrieveBranches(v_product.getId());
        List<String> branches_name = new LinkedList<>();
        for (Vertex v_branch : branches) {
            HashValue hashValue = hashtable.get(v_branch.getId());
            Branch branch = (Branch) hashValue.getObject();
            branches_name.add(branch.getName());
        }
        HashValue hashValue = hashtable.get(v_product.getId());
        Product product = (Product) hashValue.getObject();
        gitMgr.checkConflict(product.getName(), branch_name, branches_name);
    }

    public void checkConflict(GitMgr gitMgr, String from) {
        __checkConflicts(gitMgr, from);
    }

    private Vertex __retrieveVertex(String branch_name) {

        Graph<Vertex, Edge> br_subgraph = reduceGraphToBrGraph();

        for (Vertex vertex : br_subgraph.vertexSet()) {
            HashValue hashValue = hashtable.get(vertex.getId());
            Branch branch = (Branch) hashValue.getObject();
            if (branch.getName().equals(branch_name)) {
                return vertex;
            }
        }
        return null;
    }

    private Set<Vertex> __retrieveProducts(Set<Vertex> features) {
        Set<Vertex> products = new HashSet<>();
        Graph<Vertex, Edge> pr_subgraph = reduceGraphToPRGraph();

        for (Vertex feature : features) {
            Vertex vertex = __retrieveVertexById(feature.getId(),pr_subgraph);
            if(vertex != null){
                for (Edge edge : pr_subgraph.incomingEdgesOf(vertex)) {
                    Vertex prod = pr_subgraph.getEdgeSource(edge);
                    products.add(prod);
                }
            }
        }
        return products;
    }

    public Set<Vertex> __retrieveBranches(String productId) {
        Graph<Vertex, Edge> pr_subgraph = reduceGraphToPRGraph();
        Graph<Vertex, Edge> mp_subgraph = reduceGraphToMpGraph();

        Vertex pr_vertex = __retrieveVertexById(productId);

        Set<Vertex> v_branches = new LinkedHashSet<>();
        for (Edge edge : pr_subgraph.outgoingEdgesOf(pr_vertex)) {
            Vertex f_vertex = pr_subgraph.getEdgeTarget(edge);
            for (Edge mp_edge : mp_subgraph.incomingEdgesOf(f_vertex)) {
                Vertex mp_vertex = mp_subgraph.getEdgeSource(mp_edge);
                for (Edge br_edge : mp_subgraph.outgoingEdgesOf(mp_vertex)) {
                    Vertex br_vertex = mp_subgraph.getEdgeTarget(br_edge);
                    if (br_vertex.getType().equals(VertexType.BRANCH)) {
                        v_branches.add(br_vertex);
                    }
                }
            }
        }
        return v_branches;
/*        LinkedList<Branch> branches = new LinkedList<>();
        for (Vertex vertex:v_branches) {
            HashValue hashValue = hashtable.get(vertex.getId());
            Branch branch = (Branch) hashValue.getObject();
            branches.add(branch);
        }
        return branches;*/
    }

    public Set<Vertex> __retrieveFeatures(String branchId) {
        Graph<Vertex, Edge> mp_subgraph = reduceGraphToMpGraph();

        Vertex br_vertex = __retrieveVertexById(branchId);

        Set<Vertex> v_features = new LinkedHashSet<>();
        for (Edge edge : mp_subgraph.incomingEdgesOf(br_vertex)) {
            Vertex mp_vertex = mp_subgraph.getEdgeSource(edge);
            for (Edge mp_edge : mp_subgraph.outgoingEdgesOf(mp_vertex)) {
                Vertex vertex = mp_subgraph.getEdgeTarget(mp_edge);
                if (vertex.getType().equals(VertexType.FEATURE)) {
                    v_features.add(vertex);
                }
            }
        }
        return v_features;
/*        LinkedList<Feature> features = new LinkedList<>();
        for (Vertex vertex:v_features) {
            HashValue hashValue = hashtable.get(vertex.getId());
            Feature feature = (Feature) hashValue.getObject();
            features.add(feature);
        }
        return features;*/
    }


}
