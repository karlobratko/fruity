package hr.algebra.fruity.mapper;

import hr.algebra.fruity.dto.request.joined.JoinedUpdateRowClusterRequestDto;
import hr.algebra.fruity.model.RowCluster;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RowClusterMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
  public abstract RowCluster partialUpdate(@MappingTarget RowCluster rowCluster, JoinedUpdateRowClusterRequestDto requestDto);

}