package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.JwtDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.UserRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.JwtTokenRequestExtractorService;
import hr.algebra.fruity.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtCurrentRequestUserService implements CurrentRequestUserService {

  private final JwtTokenRequestExtractorService jwtTokenRequestExtractorService;

  private final JwtTokenService jwtTokenService;

  private final UserRepository userRepository;

  @Override
  public User getUser() {
    return userRepository.findById(jwtTokenService.getClaim(jwtTokenRequestExtractorService.getToken(), JwtDto.ClaimResolvers.userIdResolver))
      .orElseThrow(EntityNotFoundException::new);
  }

}
