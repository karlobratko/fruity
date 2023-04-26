package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateWorkRowRequestDto;
import hr.algebra.fruity.model.WorkRow;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkRowMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract WorkRow partialUpdate(@MappingTarget WorkRow workRow, UpdateWorkRowRequestDto requestDto);

}