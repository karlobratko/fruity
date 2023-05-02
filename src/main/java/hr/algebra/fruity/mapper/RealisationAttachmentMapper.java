package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRealisationAttachmentRequestDto;
import hr.algebra.fruity.model.RealisationAttachment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RealisationAttachmentMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract RealisationAttachment partialUpdate(@MappingTarget RealisationAttachment realisationAttachment, UpdateRealisationAttachmentRequestDto requestDto);

}