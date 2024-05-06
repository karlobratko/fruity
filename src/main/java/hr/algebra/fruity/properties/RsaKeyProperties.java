package hr.algebra.fruity.properties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "hr.algebra.fruity.security.rsa")
public record RsaKeyProperties(
  RSAPublicKey publicKey,
  RSAPrivateKey privateKey) {
}
