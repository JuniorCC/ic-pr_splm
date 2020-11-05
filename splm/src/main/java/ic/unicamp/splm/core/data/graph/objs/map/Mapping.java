package ic.unicamp.splm.core.data.graph.objs.map;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Mapping {
  String name;
  MappingType type;
}
