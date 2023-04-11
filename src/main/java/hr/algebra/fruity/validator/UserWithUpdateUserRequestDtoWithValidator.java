package hr.algebra.fruity.validator;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.exception.UniquenessViolatedException;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWithUpdateUserRequestDtoWithValidator implements WithValidator<User, UpdateUserRequestDto> {

  private final UserRepository userRepository;

  @Override
  public void validate(User target, UpdateUserRequestDto with) {
    if (Objects.nonNull(with.oib()))
      userRepository.findByOib(with.oib())
        .ifPresent(it -> {
          if (!Objects.equals(it, target))
            throw new UniquenessViolatedException("OIB već postoji i nije jedinstven.");
        });
  }

}
