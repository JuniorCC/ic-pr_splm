package ic.unicamp.splm.core.data.graph.objs.feature;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Feature {

  String name;

  FeatureMode mode;

  FeatureType type;
}
