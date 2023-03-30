package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateEquipmentRequestDto;
import hr.algebra.fruity.model.Attachment;
import hr.algebra.fruity.model.Equipment;
import hr.algebra.fruity.repository.AttachmentRepository;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EquipmentMapper {

  @Autowired
  private AttachmentRepository attachmentRepository;

  @Named(MappingHelpers.mapListOfIdsToSetOfAttachment)
  protected Set<Attachment> mapListOfIdsToSetOfAttachment(List<Long> value) {
    return Objects.nonNull(value) ? Set.copyOf(attachmentRepository.findAllById(value)) : null;
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateEquipmentRequestDto.Fields.compatibleAttachmentFks, target = Equipment.Fields.attachments, qualifiedByName = MappingHelpers.mapListOfIdsToSetOfAttachment, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  public abstract Equipment partialUpdate(@MappingTarget Equipment user, UpdateEquipmentRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapListOfIdsToSetOfAttachment = "mapListOfIdsToSetOfAttachment";

  }

}
