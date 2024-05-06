package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.constants.JwtConstants;
import hr.algebra.fruity.exception.JwtTokenNotPresentException;
import hr.algebra.fruity.service.JwtTokenRequestExtractorService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenRequestExtractorServiceImpl implements JwtTokenRequestExtractorService {

  private final HttpServletRequest request;

  @Override
  public String getToken() {
    val authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Objects.isNull(authorization) || !authorization.startsWith(JwtConstants.headerAuthorizationBearerPrefix))
      throw new JwtTokenNotPresentException();

    return authorization.substring(JwtConstants.headerAuthorizationBearerPrefix.length());
  }

}
