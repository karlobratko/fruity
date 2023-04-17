package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRowRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.FruitCultivar;
import hr.algebra.fruity.model.Row;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.FruitCultivarRepository;
import hr.algebra.fruity.repository.RowClusterRepository;
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
public abstract class RowMapper {

  @Autowired
  private RowClusterRepository rowClusterRepository;

  @Autowired
  private FruitCultivarRepository fruitCultivarRepository;

  @Named(MappingHelpers.mapRowClusterFkToRowCluster)
  protected RowCluster mapRowClusterFkToRowCluster(Long value) {
    return rowClusterRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @Named(MappingHelpers.mapFruitCultivarFkToFruitCultivar)
  protected FruitCultivar mapFruitCultivarFkToFruitCultivar(Integer value) {
    return fruitCultivarRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateRowRequestDto.Fields.rowClusterFk, target = Row.Fields.rowCluster, qualifiedByName = MappingHelpers.mapRowClusterFkToRowCluster)
  @Mapping(source = UpdateRowRequestDto.Fields.fruitCultivarFk, target = Row.Fields.fruitCultivar, qualifiedByName = MappingHelpers.mapFruitCultivarFkToFruitCultivar)
  public abstract Row partialUpdate(@MappingTarget Row rowCluster, UpdateRowRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapRowClusterFkToRowCluster = "mapRowClusterFkToRowCluster";

    public static final String mapFruitCultivarFkToFruitCultivar = "mapFruitCultivarFkToFruitCultivar";

  }

}