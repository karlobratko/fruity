package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRealisationRowRequestDto;
import hr.algebra.fruity.model.RealisationRow;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RealisationRowMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract RealisationRow partialUpdate(@MappingTarget RealisationRow realisationRow, UpdateRealisationRowRequestDto requestDto);

}