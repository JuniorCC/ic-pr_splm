package ic.unicamp.splm.core.data.graph.objs.feature;

import lombok.*;

@Builder
@Data
public class Feature {

  String name;

  FeatureMode mode;

  FeatureType type;

  boolean orParent;
}
