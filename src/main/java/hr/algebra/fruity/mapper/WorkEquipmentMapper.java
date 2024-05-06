package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateWorkEquipmentRequestDto;
import hr.algebra.fruity.model.WorkEquipment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkEquipmentMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract WorkEquipment partialUpdate(@MappingTarget WorkEquipment workEquipment, UpdateWorkEquipmentRequestDto requestDto);

}