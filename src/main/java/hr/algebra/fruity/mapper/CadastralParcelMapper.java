package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateCadastralParcelRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.CadastralMunicipality;
import hr.algebra.fruity.model.CadastralParcel;
import hr.algebra.fruity.model.CadastralParcelOwnershipStatus;
import hr.algebra.fruity.repository.CadastralMunicipalityRepository;
import hr.algebra.fruity.repository.CadastralParcelOwnershipStatusRepository;
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
public abstract class CadastralParcelMapper {

  @Autowired
  private CadastralMunicipalityRepository cadastralMunicipalityRepository;

  @Autowired
  private CadastralParcelOwnershipStatusRepository cadastralParcelOwnershipStatusRepository;

  @Named(MappingHelpers.mapCadastralMunicipalityFkToCadastralMunicipality)
  protected CadastralMunicipality mapCadastralMunicipalityFkToCadastralMunicipality(Integer value) {
    return cadastralMunicipalityRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @Named(MappingHelpers.mapCadastralParcelOwnershipStatusFkToCadastralParcelOwnershipStatus)
  protected CadastralParcelOwnershipStatus mapCadastralParcelOwnershipStatusFkToCadastralParcelOwnershipStatus(Integer value) {
    return cadastralParcelOwnershipStatusRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateCadastralParcelRequestDto.Fields.cadastralMunicipalityFk, target = CadastralParcel.Fields.cadastralMunicipality, qualifiedByName = MappingHelpers.mapCadastralMunicipalityFkToCadastralMunicipality)
  @Mapping(source = UpdateCadastralParcelRequestDto.Fields.ownershipStatusFk, target = CadastralParcel.Fields.ownershipStatus, qualifiedByName = MappingHelpers.mapCadastralParcelOwnershipStatusFkToCadastralParcelOwnershipStatus)
  public abstract CadastralParcel partialUpdate(@MappingTarget CadastralParcel cadastralParcel, UpdateCadastralParcelRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapCadastralMunicipalityFkToCadastralMunicipality = "mapCadastralMunicipalityFkToCadastralMunicipality";

    public static final String mapCadastralParcelOwnershipStatusFkToCadastralParcelOwnershipStatus = "mapCadastralParcelOwnershipStatusFkToCadastralParcelOwnershipStatus";

  }

}