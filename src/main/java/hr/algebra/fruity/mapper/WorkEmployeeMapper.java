package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateWorkEmployeeRequestDto;
import hr.algebra.fruity.model.WorkEmployee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkEmployeeMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract WorkEmployee partialUpdate(@MappingTarget WorkEmployee workEmployee, UpdateWorkEmployeeRequestDto requestDto);

}