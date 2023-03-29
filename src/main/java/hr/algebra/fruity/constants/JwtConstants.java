package hr.algebra.fruity.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {

  public static final String headerAuthorizationBearerPrefix = "Bearer ";

  public static final String tokenType = "Bearer";

}
