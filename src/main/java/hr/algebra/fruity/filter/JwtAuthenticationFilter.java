package hr.algebra.fruity.filter;

import hr.algebra.fruity.constants.JwtConstants;
import hr.algebra.fruity.dto.JwtDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.repository.EmployeeRepository;
import hr.algebra.fruity.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final EmployeeRepository employeeRepository;

  private final JwtTokenService jwtTokenService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (Objects.isNull(authHeader) || !authHeader.startsWith(JwtConstants.headerAuthorizationBearerPrefix)) {
      filterChain.doFilter(request, response);
      return;
    }

    val jwtToken = authHeader.substring(JwtConstants.headerAuthorizationBearerPrefix.length());
    val employeeId = jwtTokenService.getClaim(jwtToken, JwtDto.ClaimResolvers.employeeIdResolver);
    if (Objects.nonNull(employeeId) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
      val employee = employeeRepository.findById(employeeId).orElseThrow(EntityNotFoundException::new);

      val authToken = new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword(), employee.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }

}
