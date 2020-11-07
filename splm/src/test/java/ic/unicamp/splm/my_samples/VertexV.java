package ic.unicamp.splm.my_samples;

import ic.unicamp.splm.core.data.types.VertexType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class VertexV {
  @Getter
  @Setter
  VertexType type;
  @Getter
  @Setter
  String id;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    VertexV vertexV = (VertexV) o;

    return new EqualsBuilder()
            .append(id, vertexV.id)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
            .append(id)
            .toHashCode();
  }
}
