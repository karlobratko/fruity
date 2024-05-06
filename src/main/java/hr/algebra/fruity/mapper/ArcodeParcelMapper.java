package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateArcodeParcelRequestDto;
import hr.algebra.fruity.model.ArcodeParcel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ArcodeParcelMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract ArcodeParcel partialUpdate(@MappingTarget ArcodeParcel arcodeParcel, JoinedUpdateArcodeParcelRequestDto requestDto);

}