package ic.unicamp.splm.core.data.graph.objs.feature;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Feature {

    String name;

    FeatureMode mode;

    FeatureType type;

    boolean orParent;
}
