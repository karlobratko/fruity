package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.CountyResponseDto;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.County;
import java.util.List;

public interface CountyService {

  List<CountyResponseDto> getAllCounties();

  CountyResponseDto getCountyById(Integer id);

  County getById(Integer id);

}
