package ic.unicamp.splm.my_samples;

import ic.unicamp.splm.core.data.types.EdgeType;
import ic.unicamp.splm.core.data.types.VertexType;
import ic.unicamp.splm.core.util.dir.GraphDir;
import ic.unicamp.splm.core.util.dir.ObjectDir;
import ic.unicamp.splm.core.util.dir.SplmDir;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IO {

/*
  @Test
  public void name() {
      System.out.println("Begin");

      SplmDir.create_splm_dir_with_msg();
      ObjectDir.create_splm_obj_dir_with_msg();
      GraphDir.create_splm_obj_graph_file_with_msg();

      DefaultDirectedGraph<String, DefaultEdge> graph2 = new DefaultDirectedGraph<>(DefaultEdge.class);
      graph2.addVertex("V1A");
      graph2.addVertex("V2A");
      graph2.addEdge("V1A","V2A", new DefaultEdge());
      //exporter
      DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>();
      Function<String, String> exporter_func_v = (vertex) -> {
          return vertex;
      };
      exporter.setVertexIdProvider(exporter_func_v);
      File file = GraphDir.get_splm_obj_graph_file__as_file();
      exporter.exportGraph(graph2, file);

      //importer
      DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();
      Function<String, String> importer_func_v = (id) -> {
          return id;
      };
      importer.setVertexFactory(importer_func_v);
      File file2 = GraphDir.get_splm_obj_graph_file__as_file();
      DefaultDirectedGraph<String, DefaultEdge> graph1 = new DefaultDirectedGraph<>(DefaultEdge.class);
      importer.importGraph(graph1, file2);

      System.out.println("End");
  }
*/

   /* @Test
    public void name2() {
        System.out.println("Begin");

        SplmDir.create_splm_dir_with_msg();
        ObjectDir.create_splm_obj_dir_with_msg();
        GraphDir.create_splm_obj_graph_file_with_msg();

        DefaultDirectedGraph<String, EdgeE> graph2 = new DefaultDirectedGraph<>(EdgeE.class);
        graph2.addVertex("V1A");
        graph2.addVertex("V2A");
        EdgeE edgeE = new EdgeE();
        edgeE.setType(EdgeType.FEATURE);
        edgeE.setId("V1A:V2A");
        graph2.addEdge("V1A","V2A", edgeE);
        //exporter
        DOTExporter<String, EdgeE> exporter = new DOTExporter<>();
        Function<String, String> exporter_func_v = (vertex) -> {
            return vertex;
        };
        exporter.setVertexIdProvider(exporter_func_v);
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> m = new HashMap<>();
            if (e.getId() != null) {
                m.put("e_id", DefaultAttribute.createAttribute(e.getId()));
            }
            if (e.getType() != null) {
                m.put("e_type", DefaultAttribute.createAttribute(e.getType().toString()));
            }
            return m;
        });
        File file = GraphDir.get_splm_obj_graph_file__as_file();
        exporter.exportGraph(graph2, file);

        //importer
        DOTImporter<String, EdgeE> importer = new DOTImporter<>();
        Function<String, String> importer_func_v = (id) -> {
            return id;
        };
        importer.setVertexFactory(importer_func_v);
        importer.addEdgeAttributeConsumer((p, atr_value) -> {
            EdgeE edge = p.getFirst();

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
                            throw new IllegalStateException("--Unexpected value: " + atr_value.getValue());
                    }
                    break;
            }
        });
        File file2 = GraphDir.get_splm_obj_graph_file__as_file();
        DefaultDirectedGraph<String, EdgeE> graph1 = new DefaultDirectedGraph<>(EdgeE.class);
        importer.importGraph(graph1, file2);

        System.out.println("End");
    }
*/
//    @Test
//    public void name3() {
//        System.out.println("Begin");
//
//        SplmDir.create_splm_dir_with_msg();
//        ObjectDir.create_splm_obj_dir_with_msg();
//        GraphDir.create_splm_obj_graph_file_with_msg();
//
//        DefaultDirectedGraph<String, EdgeEB> graph2 = new DefaultDirectedGraph<>(EdgeEB.class);
//        graph2.addVertex("V1A");
//        graph2.addVertex("V2A");
//        EdgeEB edgeE = EdgeEB.builder().type(EdgeType.FEATURE).id("V1A:V2A").build();
//        graph2.addEdge("V1A","V2A", edgeE);
//        //exporter
//        DOTExporter<String, EdgeEB> exporter = new DOTExporter<>();
//        Function<String, String> exporter_func_v = (vertex) -> {
//            return vertex;
//        };
//        exporter.setVertexIdProvider(exporter_func_v);
//        exporter.setEdgeAttributeProvider(e -> {
//            Map<String, Attribute> m = new HashMap<>();
//            if (e.getId() != null) {
//                m.put("e_id", DefaultAttribute.createAttribute(e.getId()));
//            }
//            if (e.getType() != null) {
//                m.put("e_type", DefaultAttribute.createAttribute(e.getType().toString()));
//            }
//            return m;
//        });
//        File file = GraphDir.get_splm_obj_graph_file__as_file();
//        exporter.exportGraph(graph2, file);
//
//        //importer
//        DOTImporter<String, EdgeEB> importer = new DOTImporter<>();
//        Function<String, String> importer_func_v = (id) -> {
//            return id;
//        };
//        importer.setVertexFactory(importer_func_v);
//        importer.addEdgeAttributeConsumer((p, atr_value) -> {
//            EdgeEB edge = p.getFirst();
//
//            String atr = p.getSecond();
//            switch (atr) {
//                case "e_id":
//                    edge.setId(atr_value.getValue());
//                    break;
//                case "e_type":
//                    switch (atr_value.getValue()) {
//                        case "BRANCH":
//                            edge.setType(EdgeType.BRANCH);
//                            break;
//                        case "FEATURE":
//                            edge.setType(EdgeType.FEATURE);
//                            break;
//                        case "PRODUCT":
//                            edge.setType(EdgeType.PRODUCT);
//                            break;
//                        case "MAPPING":
//                            edge.setType(EdgeType.MAPPING);
//                            break;
//                        default:
//                            throw new IllegalStateException("--Unexpected value: " + atr_value.getValue());
//                    }
//                    break;
//            }
//        });
//        File file2 = GraphDir.get_splm_obj_graph_file__as_file();
//        DefaultDirectedGraph<String, EdgeEB> graph1 = new DefaultDirectedGraph<>(EdgeEB.class);
//        importer.importGraph(graph1, file2);
//
//        System.out.println("End");
//    }

    @Test
    public void name4() {
        System.out.println("Begin");

        SplmDir.create_splm_dir_with_msg();
        ObjectDir.create_splm_obj_dir_with_msg();
        GraphDir.create_splm_obj_graph_file_with_msg();

        DefaultDirectedGraph<VertexV, EdgeE> graph2 = new DefaultDirectedGraph<>(EdgeE.class);
        VertexV vertex1 = new VertexV();
        vertex1.setId("V1A");
        vertex1.setType(VertexType.FEATURE);
        VertexV vertex2 = new VertexV();
        vertex2.setId("V1B");
        vertex2.setType(VertexType.FEATURE);
        graph2.addVertex(vertex1);
        graph2.addVertex(vertex2);
        EdgeE edgeE = new EdgeE();
        edgeE.setType(EdgeType.FEATURE);
        edgeE.setId("V1A:V2A");
        graph2.addEdge(vertex1, vertex2, edgeE);
        //exporter
        DOTExporter<VertexV, EdgeE> exporter = new DOTExporter<>();
        Function<VertexV, String> exporter_func_v = (vertex) -> {
            return vertex.getId();
        };
        exporter.setVertexIdProvider(exporter_func_v);
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> m = new HashMap<>();
            if (v.getId() != null) {
                m.put("v_id", DefaultAttribute.createAttribute(v.getId()));
            }
            if (v.getType() != null) {
                m.put("v_type", DefaultAttribute.createAttribute(v.getType().toString()));
            }
            return m;
        });
        exporter.setEdgeAttributeProvider(e -> {
            Map<String, Attribute> m = new HashMap<>();
            if (e.getId() != null) {
                m.put("e_id", DefaultAttribute.createAttribute(e.getId()));
            }
            if (e.getType() != null) {
                m.put("e_type", DefaultAttribute.createAttribute(e.getType().toString()));
            }
            return m;
        });
        File file = GraphDir.get_splm_obj_graph_file__as_file();
        exporter.exportGraph(graph2, file);

        //importer
        DOTImporter<VertexV, EdgeE> importer = new DOTImporter<>();
        Function<String, VertexV> importer_func_v = (id) -> {
            VertexV v = new VertexV();
            v.setId(id); // taken in account equals and hashcode from the VertexV object
            //v.setType(VertexType.PRODUCT);
            return v;
        };
        importer.setVertexFactory(importer_func_v);
        importer.addVertexAttributeConsumer((p, atr_value) -> {
            VertexV vertex = p.getFirst();

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
        importer.addEdgeAttributeConsumer((p, atr_value) -> {
            EdgeE edge = p.getFirst();

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
        File file2 = GraphDir.get_splm_obj_graph_file__as_file();
        DefaultDirectedGraph<VertexV, EdgeE> graph1 = new DefaultDirectedGraph<>(EdgeE.class);
        importer.importGraph(graph1, file2);

        System.out.println("End");
    }

/*
    @Test
    public void name5() {
        System.out.println("Begin");

        SplmDir.create_splm_dir_with_msg();
        ObjectDir.create_splm_obj_dir_with_msg();
        GraphDir.create_splm_obj_graph_file_with_msg();

        DefaultDirectedGraph<VertexV, DefaultEdge> graph2 = new DefaultDirectedGraph<>(DefaultEdge.class);
        VertexV vertex1 = new VertexV();
        vertex1.setId("V1A");
        vertex1.setType(VertexType.FEATURE);
        VertexV vertex2 = new VertexV();
        vertex2.setId("V1B");
        vertex2.setType(VertexType.FEATURE);
        graph2.addVertex(vertex1);
        graph2.addVertex(vertex2);
        graph2.addEdge(vertex1,vertex2, new DefaultEdge());
        //exporter
        DOTExporter<VertexV, DefaultEdge> exporter = new DOTExporter<>();
        Function<VertexV, String> exporter_func_v = (vertex) -> {
            return vertex.getId();
        };
        exporter.setVertexIdProvider(exporter_func_v);
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> m = new HashMap<>();
            if (v.getId() != null) {
                m.put("v_id", DefaultAttribute.createAttribute(v.getId()));
            }
            if (v.getType() != null) {
                m.put("v_type", DefaultAttribute.createAttribute(v.getType().toString()));
            }
            return m;
        });
        File file = GraphDir.get_splm_obj_graph_file__as_file();
        exporter.exportGraph(graph2, file);

        //importer
        DOTImporter<VertexV, DefaultEdge> importer = new DOTImporter<>();
        Function<String, VertexV> importer_func_v = (id) -> {
            VertexV v = new VertexV();
            v.setId(id);
            v.setType(VertexType.PRODUCT);
            return v;
        };
        importer.setVertexFactory(importer_func_v);
        importer.addVertexAttributeConsumer((p, atr_value) -> {
            VertexV vertex = p.getFirst();

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
        File file2 = GraphDir.get_splm_obj_graph_file__as_file();
        DefaultDirectedGraph<VertexV, DefaultEdge> graph1 = new DefaultDirectedGraph<>(DefaultEdge.class);
        importer.importGraph(graph1, file2);

        System.out.println("End");
    }
*/


   /* private void __loadGraphData() {

        DOTImporter<ic.unicamp.splm.core.data.graph.Vertex, ic.unicamp.splm.core.data.graph.Edge> importer = new DOTImporter<>();
        Function<String, ic.unicamp.splm.core.data.graph.Vertex> function = (id) -> {
            return ic.unicamp.splm.core.data.graph.Vertex.builder().id(id).type(null).build();
        };

        importer.setVertexFactory(function);
        importer.addVertexAttributeConsumer((p, atr_value) -> {
            ic.unicamp.splm.core.data.graph.Vertex vertex = p.getFirst();

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
                            throw new IllegalStateException("--Unexpected value: " + atr_value.getValue());
                    }
                    break;
            }
        });
        importer.addEdgeAttributeConsumer((p, atr_value) -> {
            ic.unicamp.splm.core.data.graph.Edge edge = p.getFirst();

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
                            throw new IllegalStateException("--Unexpected value: " + atr_value.getValue());
                    }
                    break;
            }
        });


        File graphFile = GraphDir.get_splm_obj_graph_file__as_file();
        DefaultDirectedGraph<Vertex, ic.unicamp.splm.core.data.graph.Edge> graph2 = new DefaultDirectedGraph<>(Edge.class);;
        importer.importGraph(graph2, graphFile);
    }

    private void __saveGraphData() {
        DOTExporter<Vertex, Edge> exporter = new DOTExporter<>();

        Function<Vertex,String> function_vertex = (vertex) -> {
            System.out.println(vertex);
            return vertex.getId();
        };
*//*  Function<Edge,String> function_edge = (edge) -> {
    System.out.println(edge);
    return edge.getId();
  };*//*
        //exporter.setEdgeIdProvider(function_edge);
        exporter.setVertexIdProvider(function_vertex);

        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> m = new HashMap<>();
            if (v.getId() != null) {
                m.put("v_id", DefaultAttribute.createAttribute(v.getId()));
            }
            if (v.getType() != null) {
                m.put("v_type", DefaultAttribute.createAttribute(v.getType().toString()));
            }
            return m;
        });
        exporter.setEdgeAttributeProvider(e -> {
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
        //exporter.exportGraph(graph, graphFile);
    }*/
}
