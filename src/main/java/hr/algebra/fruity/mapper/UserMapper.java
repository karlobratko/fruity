package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.ReplaceUserRequestDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.repository.CountyRepository;
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
public abstract class UserMapper {

  @Autowired
  private CountyRepository countyRepository;

  @Named(MappingHelpers.mapIdToCounty)
  protected County mapIdToCounty(Integer value) {
    return countyRepository.findById(value)
      .orElseThrow(EntityNotFoundException::new);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = ReplaceUserRequestDto.Fields.countyFk, target = User.Fields.county, qualifiedByName = {MappingHelpers.mapIdToCounty})
  public abstract User partialUpdate(@MappingTarget User user, ReplaceUserRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapIdToCounty = "mapIdToCounty";

  }

}

