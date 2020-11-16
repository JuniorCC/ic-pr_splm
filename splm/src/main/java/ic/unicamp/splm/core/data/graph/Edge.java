package ic.unicamp.splm.core.data.graph;

import ic.unicamp.splm.core.data.types.EdgeType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

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

        Edge edge = (Edge) o;

        return new EqualsBuilder().append(type, edge.type).append(id, edge.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(type).append(id).toHashCode();
    }
}
