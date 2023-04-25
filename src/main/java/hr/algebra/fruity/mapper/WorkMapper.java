package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateWorkRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Work;
import hr.algebra.fruity.model.WorkType;
import hr.algebra.fruity.repository.WorkTypeRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class WorkMapper {

  @Autowired
  private WorkTypeRepository workTypeRepository;

  @Named(MappingHelpers.mapIdToWorkType)
  protected WorkType mapIdToWorkType(Integer value) {
    return workTypeRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateWorkRequestDto.Fields.typeFk, target = Work.Fields.type, qualifiedByName = {MappingHelpers.mapIdToWorkType})
  public abstract Work partialUpdate(@MappingTarget Work work, UpdateWorkRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapIdToWorkType = "mapIdToWorkType";

  }

}