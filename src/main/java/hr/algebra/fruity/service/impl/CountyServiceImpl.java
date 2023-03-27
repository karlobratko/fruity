package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.repository.CountyRepository;
import hr.algebra.fruity.service.CountyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

  private final ConversionService conversionService;

  private final CountyRepository countyRepository;

  @Override
  public List<CountyResponseDto> getAllCounties() {
    return countyRepository.findAll().stream()
      .map(county -> conversionService.convert(county, CountyResponseDto.class))
      .toList();
  }

  @Override
  public CountyResponseDto getCountyById(Integer id) {
    return conversionService.convert(getCounty(id), CountyResponseDto.class);
  }

  private County getCounty(Integer id) {
    val county = countyRepository.findById(id)
      .orElseThrow(EntityNotFoundException::new);

    return county;
  }

}
