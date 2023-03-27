package hr.algebra.fruity.dto.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record FullUserResponseDto(
  Long id,
  UUID uuid,
  String name,
  String oib,
  String phoneNumber,
  String address,
  CountyResponseDto county
) {

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Fields {

    public static final String id = "id";

    public static final String uuid = "uuid";

    public static final String name = "name";

    public static final String oib = "oib";

    public static final String phoneNumber = "phoneNumber";

    public static final String address = "address";

    public static final String county = "county";

  }

}
