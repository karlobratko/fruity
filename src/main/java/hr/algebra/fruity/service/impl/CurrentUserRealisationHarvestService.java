package hr.algebra.fruity.service.impl;

import hr.algebra.fruity.converter.CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter;
import hr.algebra.fruity.converter.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter;
import hr.algebra.fruity.converter.RealisationHarvestToFullRealisationHarvestResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationHarvestToRealisationHarvestResponseDtoConverter;
import hr.algebra.fruity.dto.request.CreateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.request.UpdateRealisationHarvestRequestDto;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.dto.response.FullRealisationHarvestResponseDto;
import hr.algebra.fruity.dto.response.RealisationHarvestResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.exception.WorkAlreadyFinishedException;
import hr.algebra.fruity.mapper.RealisationHarvestMapper;
import hr.algebra.fruity.model.RealisationHarvest;
import hr.algebra.fruity.repository.RealisationHarvestRepository;
import hr.algebra.fruity.service.CurrentRequestUserService;
import hr.algebra.fruity.service.FruitCultivarService;
import hr.algebra.fruity.service.RealisationHarvestService;
import hr.algebra.fruity.service.RealisationService;
import hr.algebra.fruity.validator.JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator;
import hr.algebra.fruity.validator.RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserRealisationHarvestService implements RealisationHarvestService {

  private final RealisationHarvestToRealisationHarvestResponseDtoConverter toRealisationHarvestResponseDtoConverter;

  private final RealisationHarvestToFullRealisationHarvestResponseDtoConverter toFullRealisationHarvestResponseDtoConverter;

  private final CreateRealisationHarvestRequestDtoToJoinedCreateRealisationHarvestRequestDtoConverter toJoinedCreateRealisationHarvestRequestDtoConverter;

  private final JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterToRealisationHarvestConverter fromJoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterConverter;

  private final JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator joinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator;

  private final RealisationHarvestWithUpdateRealisationHarvestRequestDtoValidator realisationHarvestWithUpdateRealisationHarvestRequestDtoValidator;

  private final RealisationHarvestMapper realisationHarvestMapper;

  private final CurrentRequestUserService currentRequestUserService;

  private final RealisationHarvestRepository realisationHarvestRepository;

  private final RealisationService realisationService;

  private final FruitCultivarService fruitCultivarService;

  @Override
  public List<RealisationHarvestResponseDto> getAllRealisationHarvestsByRealisationId(Long realisationFk) {
    return realisationHarvestRepository.findAllByRealisation(realisationService.getById(realisationFk)).stream()
      .map(toRealisationHarvestResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public List<RealisationHarvestResponseDto> getAllRealisationHarvestsByRealisationIdAndFruitCultivarId(Long realisationFk, Integer fruitCultivarFk) {
    return realisationHarvestRepository.findAllByRealisationAndFruitCultivar(realisationService.getById(realisationFk), fruitCultivarService.getById(fruitCultivarFk)).stream()
      .map(toRealisationHarvestResponseDtoConverter::convert)
      .toList();
  }

  @Override
  public FullRealisationHarvestResponseDto getRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer fruitCultivarFk, Integer classFk) {
    return toFullRealisationHarvestResponseDtoConverter.convert(getByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk));
  }

  @Override
  public FullRealisationHarvestResponseDto createRealisationHarvestForRealisationId(Long realisationFk, CreateRealisationHarvestRequestDto requestDto) {
    val realisation = realisationService.getById(realisationFk);
    val joinedRequestDto = Objects.requireNonNull(toJoinedCreateRealisationHarvestRequestDtoConverter.convert(requestDto));

    val requestDtoWithRealisation = new JoinedCreateRealisationHarvestRequestDtoWithRealisationAdapter(joinedRequestDto, realisation);
    joinedCreateRealisationHarvestRequestDtoWithRealisationAdapterValidator.validate(requestDtoWithRealisation);

    val realisationHarvest = realisationHarvestRepository.save(Objects.requireNonNull(fromJoinedCreateRealisationHarvestRequestDtoWithRealisationAdapterConverter.convert(requestDtoWithRealisation)));
    return toFullRealisationHarvestResponseDtoConverter.convert(realisationHarvest);
  }

  @Override
  public FullRealisationHarvestResponseDto updateRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer fruitCultivarFk, Integer classFk, UpdateRealisationHarvestRequestDto requestDto) {
    val realisationHarvest = getByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk);

    realisationHarvestWithUpdateRealisationHarvestRequestDtoValidator.validate(realisationHarvest, requestDto);

    return toFullRealisationHarvestResponseDtoConverter.convert(
      realisationHarvestRepository.save(
        realisationHarvestMapper.partialUpdate(realisationHarvest, requestDto)
      )
    );
  }

  @Override
  public void deleteRealisationHarvestByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer fruitCultivarFk, Integer classFk) {
    val realisationHarvest = getByRealisationIdAndFruitCultivarIdAndClassId(realisationFk, fruitCultivarFk, classFk);

    if (realisationHarvest.getRealisation().getWork().isFinished())
      throw new WorkAlreadyFinishedException();

    realisationHarvestRepository.delete(realisationHarvest);
  }

  @Override
  public RealisationHarvest getByRealisationIdAndFruitCultivarIdAndClassId(Long realisationFk, Integer fruitCultivarFk, Integer classFk) {
    return realisationHarvestRepository.findByRealisationIdAndFruitCultivarIdAndHarvestedFruitClassIdAndRealisationWorkUserId(realisationFk, fruitCultivarFk, classFk, currentRequestUserService.getUserId())
      .orElseThrow(EntityNotFoundException.supplier("Realizirana berba"));
  }

}
