package hr.algebra.fruity.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {

  private @NonNull String sender;

  @Singular
  private List<String> receivers;

  @Singular
  private List<String> bccs;

  private @NonNull String subject;

  private @NonNull String text;

}
