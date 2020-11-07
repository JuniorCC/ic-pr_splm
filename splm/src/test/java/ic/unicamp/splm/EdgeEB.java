package ic.unicamp.splm;

import ic.unicamp.splm.core.data.types.EdgeType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jgrapht.graph.DefaultEdge;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class EdgeEB extends DefaultEdge {
  EdgeType type;
  String id; // fixing hash value

}
