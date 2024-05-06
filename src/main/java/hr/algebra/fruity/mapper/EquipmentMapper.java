package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateEquipmentRequestDto;
import hr.algebra.fruity.model.Equipment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EquipmentMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = JoinedUpdateEquipmentRequestDto.Fields.compatibleAttachments, target = Equipment.Fields.attachments, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  public abstract Equipment partialUpdate(@MappingTarget Equipment equipment, JoinedUpdateEquipmentRequestDto requestDto);

}
