package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.UpdateUserRequestDto;
import hr.algebra.fruity.model.County;
import hr.algebra.fruity.model.User;
import hr.algebra.fruity.service.CountyService;
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
  private CountyService countyService;

  @Named(MappingHelpers.mapIdToCounty)
  protected County mapIdToCounty(Integer value) {
    return countyService.getById(value);
  }

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  @Mapping(source = UpdateUserRequestDto.Fields.countyFk, target = User.Fields.county, qualifiedByName = {MappingHelpers.mapIdToCounty})
  public abstract User partialUpdate(@MappingTarget User user, UpdateUserRequestDto requestDto);

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class MappingHelpers {

    public static final String mapIdToCounty = "mapIdToCounty";

  }

}

