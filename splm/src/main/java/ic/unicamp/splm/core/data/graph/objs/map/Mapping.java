package ic.unicamp.splm.core.data.graph.objs.map;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Builder
@Data
public class Mapping {
  String name;
  MappingType type;
}
