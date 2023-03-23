package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.controller.AuthenticationController;
import hr.algebra.fruity.model.Email;
import hr.algebra.fruity.model.Employee;
import hr.algebra.fruity.properties.EmailProperties;
import hr.algebra.fruity.service.EmailComposerService;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class ThymeleafEmailComposerService implements EmailComposerService {

  private final EmailProperties emailProperties;

  private final ITemplateEngine templateEngine;

  @Override
  public Email composeConfirmRegistrationEmail(Employee employee, UUID registrationToken) {
    val uri = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path(AuthenticationController.Constants.requestMapping)
      .path(AuthenticationController.Constants.confirmRegistrationGetMapping)
      .build(registrationToken.toString());

    val ctx = new Context();
    ctx.setVariable("link", uri.toString());

    val htmlEmailContent = templateEngine.process(Constants.confirmRegistrationEmailTemplate, ctx);

    return Email.builder()
      .sender(emailProperties.sender())
      .receiver(employee.getEmail())
      .subject("Fruity | Potvrda registracije")
      .text(htmlEmailContent)
      .build();
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Constants {

    public static final String confirmRegistrationEmailTemplate = "confirm-registration";

  }

}
