package ic.unicamp.splm.core.data.graph;

import ic.unicamp.splm.core.data.types.VertexEnum;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Vertex {
  VertexEnum type;
  String id;
}
