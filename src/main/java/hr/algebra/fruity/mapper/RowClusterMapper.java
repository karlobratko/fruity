package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateRowClusterRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.RowCluster;
import hr.algebra.fruity.repository.ArcodeParcelRepository;
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
public abstract class RowClusterMapper {

  @Autowired
  private ArcodeParcelRepository cadastralParcelRepository;

  @Named(MappingHelpers.mapArcodeParcelFkToArcodeParcel)
  protected ArcodeParcel mapArcodeParcelFkToArcodeParcel(Long value) {
    return cadastralParcelRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateRowClusterRequestDto.Fields.arcodeParcelFk, target = RowCluster.Fields.arcodeParcel, qualifiedByName = MappingHelpers.mapArcodeParcelFkToArcodeParcel)
  public abstract RowCluster partialUpdate(@MappingTarget RowCluster rowCluster, UpdateRowClusterRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapArcodeParcelFkToArcodeParcel = "mapArcodeParcelFkToArcodeParcel";

  }

}