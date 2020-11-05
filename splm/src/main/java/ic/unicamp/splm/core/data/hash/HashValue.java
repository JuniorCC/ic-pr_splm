package ic.unicamp.splm.core.data.hash;

import ic.unicamp.splm.core.data.types.HashObjectType;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class HashValue {
  Object object;
  HashObjectType type;
}
