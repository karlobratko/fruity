package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateArcodeParcelRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.ArcodeParcel;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.repository.CadastralParcelRepository;
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
public abstract class ArcodeParcelMapper {

  @Autowired
  private CadastralParcelRepository cadastralParcelRepository;

  @Named(MappingHelpers.mapCadastralParcelFkToCadastralParcel)
  protected CadastralParcel mapCadastralParcelFkToCadastralParcel(Long value) {
    return cadastralParcelRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateArcodeParcelRequestDto.Fields.cadastralParcelFk, target = ArcodeParcel.Fields.cadastralParcel, qualifiedByName = MappingHelpers.mapCadastralParcelFkToCadastralParcel)
  public abstract ArcodeParcel partialUpdate(@MappingTarget ArcodeParcel cadastralParcel, UpdateArcodeParcelRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapCadastralParcelFkToCadastralParcel = "mapCadastralParcelFkToCadastralParcel";

  }

}