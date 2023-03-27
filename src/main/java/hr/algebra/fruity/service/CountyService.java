package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import java.util.List;

public interface CountyService {

  List<CountyResponseDto> getAllCounties();

  CountyResponseDto getCountyById(Integer id);

}
