package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateWorkRequestDto;
import hr.algebra.fruity.model.Work;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract Work partialUpdate(@MappingTarget Work work, JoinedUpdateWorkRequestDto requestDto);

}