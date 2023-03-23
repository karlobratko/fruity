package hr.algebra.fruity.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtils {

  public static Map<String, Object> objectToMap(@NonNull Object obj) {
    Map<String, Object> map = new HashMap<>();

    for (Field field : obj.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        map.put(field.getName(), field.get(obj));
      } catch (Exception ignored) {
      }
    }

    return map;
  }

}
