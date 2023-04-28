package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateRowRequestDto;
import hr.algebra.fruity.model.Row;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RowMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract Row partialUpdate(@MappingTarget Row row, JoinedUpdateRowRequestDto requestDto);

}