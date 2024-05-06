package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CountyToCountyResponseDtoConverter;
import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.repository.CountyRepository;
import hr.algebra.fruity.service.CountyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

  private final CountyToCountyResponseDtoConverter toCountyResponseDtoConverter;

  private final CountyRepository countyRepository;

  @Override
  public List<CountyResponseDto> getAllCounties() {
    return countyRepository.findAll().stream()
      .map(toCountyResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public CountyResponseDto getCountyById(Integer id) {
    return toCountyResponseDtoConverter.convert(getById(id));
  }

  @Override
  public County getById(Integer id) {
    return countyRepository.findById(id)
      .orElseThrow(EntityNotFoundException.supplier("Županija"));
  }

}
