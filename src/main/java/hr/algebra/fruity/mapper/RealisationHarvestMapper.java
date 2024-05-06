package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.model.UnitOfMeasure;
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
public abstract class RealisationHarvestMapper {

  @Autowired
  private UnitOfMeasureService unitOfMeasureService;

  @Named(WorkAgentMapper.MappingHelpers.mapIdToUnitOfMeasure)
  protected UnitOfMeasure mapIdToUnitOfMeasure(Integer value) {
    return unitOfMeasureService.getById(value);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateRealisationHarvestRequestDto.Fields.unitOfMeasureFk, target = RealisationHarvest.Fields.unitOfMeasure, qualifiedByName = {WorkAgentMapper.MappingHelpers.mapIdToUnitOfMeasure})
  public abstract RealisationHarvest partialUpdate(@MappingTarget RealisationHarvest realisationHarvest, UpdateRealisationHarvestRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapIdToUnitOfMeasure = "mapIdToUnitOfMeasure";

  }

}