package hr.algebra.fruity.utils.mother.model;

import hr.algebra.fruity.model.Email;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailMother {

  public static Email.EmailBuilder complete() {
    return Email.builder()
      .sender(Constants.instanceSender)
      .receivers(Constants.instanceReceivers)
      .bccs(Constants.instanceBccs)
      .subject(Constants.instanceSubject)
      .text(Constants.instanceText);
  }


  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String instanceSender = "sender@email.com";

    public static final List<String> instanceReceivers = List.of("receiver1@email.com", "receiver2@email.com");

    public static final List<String> instanceBccs = List.of("bcc1@email.com", "bcc2@email.com");

    public static final String instanceSubject = "subject";

    public static final String instanceText = "text";

  }

}
