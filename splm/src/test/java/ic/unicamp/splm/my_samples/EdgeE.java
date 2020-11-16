package ic.unicamp.splm.my_samples;

import ic.unicamp.splm.core.data.types.EdgeType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jgrapht.graph.DefaultEdge;


public class EdgeE extends DefaultEdge {

    @Setter
    @Getter
    EdgeType type;
    @Setter
    @Getter
    String id; // fixing hash value

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EdgeE edgeE = (EdgeE) o;

        return new EqualsBuilder()
                .append(id, edgeE.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }
}
