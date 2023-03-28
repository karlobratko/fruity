package hr.algebra.fruity.service;

@FunctionalInterface
public interface JwtTokenRequestExtractorService {

  String getToken();

}
