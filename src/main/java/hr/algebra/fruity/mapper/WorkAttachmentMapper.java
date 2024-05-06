package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateWorkAttachmentRequestDto;
import hr.algebra.fruity.model.WorkAttachment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkAttachmentMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract WorkAttachment partialUpdate(@MappingTarget WorkAttachment workAttachment, UpdateWorkAttachmentRequestDto requestDto);

}