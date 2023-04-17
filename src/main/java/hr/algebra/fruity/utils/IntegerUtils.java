package hr.algebra.fruity.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerUtils {

  public static Integer increment(Integer value) {
    return value + 1;
  }

  public static Integer decrement(Integer value) {
    return value - 1;
  }

}
