package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRealisationAgentRequestDto;
import hr.algebra.fruity.model.UnitOfMeasure;
import hr.algebra.fruity.model.RealisationAgent;
import hr.algebra.fruity.service.UnitOfMeasureService;
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
public abstract class RealisationAgentMapper {

  @Autowired
  private UnitOfMeasureService unitOfMeasureService;

  @Named(MappingHelpers.mapIdToUnitOfMeasure)
  protected UnitOfMeasure mapIdToUnitOfMeasure(Integer value) {
    return unitOfMeasureService.getById(value);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateRealisationAgentRequestDto.Fields.agentUnitOfMeasureFk, target = RealisationAgent.Fields.agentUnitOfMeasure, qualifiedByName = {MappingHelpers.mapIdToUnitOfMeasure})
  @Mapping(source = UpdateRealisationAgentRequestDto.Fields.waterUnitOfMeasureFk, target = RealisationAgent.Fields.waterUnitOfMeasure, qualifiedByName = {MappingHelpers.mapIdToUnitOfMeasure})
  public abstract RealisationAgent partialUpdate(@MappingTarget RealisationAgent realisationAgent, UpdateRealisationAgentRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapIdToUnitOfMeasure = "mapIdToUnitOfMeasure";

  }

}