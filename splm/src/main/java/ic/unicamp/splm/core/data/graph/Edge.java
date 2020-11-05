package ic.unicamp.splm.core.data.graph;

import ic.unicamp.splm.core.data.types.EdgeType;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Edge {
  EdgeType type;
}
