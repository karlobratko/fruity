package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.model.Email;
import hr.algebra.fruity.service.EmailSenderService;
import jakarta.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

  private final JavaMailSender javaMailSender;

  @Override
  @Async
  public void send(Email email) {
    try {
      val mimeMessage = javaMailSender.createMimeMessage();
      
      val message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
      message.setFrom(email.getSender());
      message.setTo(Arrays.copyOf(email.getReceivers().toArray(), email.getReceivers().size(), String[].class));
      message.setBcc(Arrays.copyOf(email.getBccs().toArray(), email.getBccs().size(), String[].class));
      message.setSubject(email.getSubject());
      message.setText(email.getText(), true);

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      log.error(e.getMessage());
    }
  }

}
