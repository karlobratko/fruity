package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateCadastralParcelRequestDto;
import hr.algebra.fruity.model.CadastralParcel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CadastralParcelMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract CadastralParcel partialUpdate(@MappingTarget CadastralParcel cadastralParcel, JoinedUpdateCadastralParcelRequestDto requestDto);

}