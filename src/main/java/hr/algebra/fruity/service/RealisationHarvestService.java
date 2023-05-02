package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.request.CreateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.response.FullRealisationHarvestResponseDto;
import hr.algebra.fruity.dto.response.RealisationHarvestResponseDto;
import hr.algebra.fruity.model.RealisationHarvest;
import java.util.List;

public interface RealisationHarvestService {

  List<RealisationHarvestResponseDto> getAllRealisationHarvestsByRealisationId(Long realisationFk);

  List<RealisationHarvestResponseDto> getAllRealisationHarvestsByRealisationIdAndFruitCultivarId(Long realisationFk, Integer fruitCultivarFk);

  FullRealisationHarvestResponseDto getRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer harvestFk, Integer classFk);

  FullRealisationHarvestResponseDto createRealisationHarvestForRealisationId(Long realisationFk, CreateRealisationHarvestRequestDto requestDto);

  FullRealisationHarvestResponseDto updateRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer harvestFk, Integer classFk, UpdateRealisationHarvestRequestDto requestDto);

  void deleteRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer harvestFk, Integer classFk);

  RealisationHarvest getByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer harvestFk, Integer classFk);

}
