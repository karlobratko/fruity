package hr.algebra.fruity.service;

import hr.algebra.fruity.converter.CreateRealisationRowRequestDtoToJoinedCreateRealisationRowRequestDtoConverter;
import hr.algebra.fruity.converter.RealisationRowToFullRealisationRowResponseDtoConverter;
import hr.algebra.fruity.converter.RealisationRowToRealisationRowResponseDtoConverter;
import hr.algebra.fruity.dto.request.joined.JoinedCreateRealisationRowRequestDtoWithRealisationAdapter;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.mapper.RealisationRowMapper;
import hr.algebra.fruity.model.RealisationRow;
import hr.algebra.fruity.repository.RealisationRowRepository;
import hr.algebra.fruity.repository.RowRepository;
import hr.algebra.fruity.service.impl.CurrentUserRealisationRowService;
import hr.algebra.fruity.utils.mother.dto.UpdateRealisationRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.CreateRealisationRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.dto.FullRealisationRowResponseDtoMother;
import hr.algebra.fruity.utils.mother.dto.JoinedCreateRealisationRowRequestDtoMother;
import hr.algebra.fruity.utils.mother.model.RealisationMother;
import hr.algebra.fruity.utils.mother.model.RealisationRowMother;
import hr.algebra.fruity.utils.mother.model.RowMother;
import hr.algebra.fruity.validator.JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("RealisationRow Service Unit Test")
@SuppressWarnings("static-access")
public class RealisationRowServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private CurrentUserRealisationRowService realisationRowService;

  @Mock
  private RealisationRowToRealisationRowResponseDtoConverter toRealisationRowResponseDtoConverter;

  @Mock
  private RealisationRowToFullRealisationRowResponseDtoConverter toFullRealisationRowResponseDtoConverter;

  @Mock
  private CreateRealisationRowRequestDtoToJoinedCreateRealisationRowRequestDtoConverter toJoinedCreateRealisationRowRequestDtoConverter;

  @Mock
  private JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator joinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator;

  @Mock
  private RealisationRowMapper realisationRowMapper;

  @Mock
  private CurrentRequestUserService currentRequestUserService;

  @Mock
  private RealisationRowRepository realisationRowRepository;

  @Mock
  private RowRepository rowRepository;

  @Mock
  private RealisationService realisationService;

  @Nested
  @DisplayName("WHEN getRealisationRowByRealisationIdAndRowId is called")
  public class WHEN_getRealisationRowByRealisationIdAndRowId {

    @Test
    @DisplayName("GIVEN realisationId and rowId " +
      "... THEN RealisationRowResponseDto is returned")
    public void GIVEN_ids_THEN_RealisationRowResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRowRepository successfully finds
      val realisationRow = RealisationRowMother.complete().build();
      given(realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(same(realisationId), same(rowId), same(userId))).willReturn(Optional.of(realisationRow));
      // ... RealisationRowToFullRealisationRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationRowResponseDtoMother.complete().build();
      given(toFullRealisationRowResponseDtoConverter.convert(same(realisationRow))).willReturn(expectedResponseDto);

      // WHEN
      // ... getRealisationRowByRealisationIdAndRowId is called
      val responseDto = realisationRowService.getRealisationRowByRealisationIdAndRowId(realisationId, rowId);

      // THEN
      // ... FullRealisationRowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN createRealisationRowForRealisationId is called")
  public class WHEN_createRealisationRowForRealisationId {

    @Captor
    ArgumentCaptor<Set<RealisationRow>> setOfRealisationRowCaptor;

    @Test
    @DisplayName("GIVEN realisationId and CreateRealisationRowRequestDto " +
      "... THEN RealisationRowResponseDto")
    public void GIVEN_LongAndCreateRealisationRowRequestDto_THEN_RealisationRowResponseDto() {
      // GIVEN
      // ... realisationId
      val realisationId = 1L;
      // ... CreateRealisationRowRequestDto
      val requestDto = CreateRealisationRowRequestDtoMother.complete().build();
      // ... RealisationService successfully gets
      val realisation = RealisationMother.complete().build();
      given(realisationService.getById(same(realisationId))).willReturn(realisation);
      // ... CreateRealisationRowRequestDtoToJoinedCreateRealisationRowRequestDtoConverter successfully converts
      val joinedRequestDto = JoinedCreateRealisationRowRequestDtoMother.complete().rows(new HashSet<>(Set.of(RowMother.complete().id(1L).build(), RowMother.complete().id(3L).build()))).build();
      given(toJoinedCreateRealisationRowRequestDtoConverter.convert(same(requestDto))).willReturn(joinedRequestDto);
      // ... JoinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator successfully validates
      willDoNothing().given(joinedCreateRealisationRowRequestDtoWithRealisationAdapterValidator).validate(any(JoinedCreateRealisationRowRequestDtoWithRealisationAdapter.class));
      // ... RealisationRowRepository successfully finds
      val existingRows = List.of(RealisationRowMother.complete().row(RowMother.complete().id(3L).build()).build(), RealisationRowMother.complete().row(RowMother.complete().id(7L).build()).build());
      given(realisationRowRepository.findAllByRealisation(same(realisation))).willReturn(existingRows);
      // ... RealisationRowRepository will successfully save RealisationRow
      given(realisationRowRepository.saveAll(setOfRealisationRowCaptor.capture())).willReturn(List.of());

      // WHEN
      // ... createRealisationRowForRealisationId is called
      realisationRowService.createRealisationRowForRealisationId(realisationId, requestDto);

      // THEN
      // ... RealisationRowResponseDto
      and.then(setOfRealisationRowCaptor.getValue())
        .isNotNull()
        .hasSize(1);
    }

  }

  @Nested
  @DisplayName("WHEN updateRealisationRowByRealisationIdAndRowId is called")
  public class WHEN_updateRealisationRowByRealisationIdAndRowId {

    @Test
    @DisplayName("GIVEN realisationId, rowId, and UpdateRealisationRowRequestDto " +
      "... THEN RealisationRowResponseDto is returned")
    public void GIVEN_idsAndUpdateRealisationRowRequestDto_THEN_RealisationRowResponseDto() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val rowId = 1L;
      // ... UpdateRealisationRowRequestDto
      val requestDto = UpdateRealisationRowRequestDtoMother.complete().build();
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRowRepository successfully finds
      val realisationRow = RealisationRowMother.complete().build();
      given(realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(same(realisationId), same(rowId), same(userId))).willReturn(Optional.of(realisationRow));
      // ... RealisationRowMapper successfully partially updates RealisationRow with UpdateRealisationRowRequestDto
      given(realisationRowMapper.partialUpdate(same(realisationRow), same(requestDto))).willReturn(realisationRow);
      // ... RealisationRowRepository successfully saves RealisationRow
      given(realisationRowRepository.save(same(realisationRow))).willReturn(realisationRow);
      // ... RealisationRowToFullRealisationRowResponseDtoConverter successfully converts
      val expectedResponseDto = FullRealisationRowResponseDtoMother.complete().build();
      given(toFullRealisationRowResponseDtoConverter.convert(same(realisationRow))).willReturn(expectedResponseDto);

      // WHEN
      // ... updateRealisationRowByRealisationIdAndRowId is called
      val responseDto = realisationRowService.updateRealisationRowByRealisationIdAndRowId(realisationId, rowId, requestDto);

      // THEN
      // ... RealisationRowResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }

  @Nested
  @DisplayName("WHEN deleteRealisationRowByRealisationIdAndRowId is called")
  public class WHEN_deleteRealisationRowByRealisationIdAndRowId {

    @Test
    @DisplayName("GIVEN realisationId and rowId " +
      "... THEN void")
    public void GIVEN_ids_THEN_void() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRowRepository successfully finds
      val realisationRow = RealisationRowMother.complete().build();
      given(realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(same(realisationId), same(rowId), same(userId))).willReturn(Optional.of(realisationRow));
      // ... RealisationRowRepository successfully deletes RealisationRow
      willDoNothing().given(realisationRowRepository).delete(realisationRow);

      // WHEN
      // ... deleteRealisationRowByRealisationIdAndRowId is called
      realisationRowService.deleteRealisationRowByRealisationIdAndRowId(realisationId, rowId);

      // THEN
      // ... void
    }

  }

  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid realisationId and rowId " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidIds_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRowRepository fails to find
      given(realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(same(realisationId), same(rowId), same(userId))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> realisationRowService.getByRealisationIdAndRowId(realisationId, rowId));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN realisationId and rowId " +
      "... THEN RealisationRow is returned")
    public void GIVEN_ids_THEN_RealisationRow() {
      // GIVEN
      // ... invalid ids
      val realisationId = 1L;
      val rowId = 1L;
      // ... CurrentRequestUserService successfully returns userId
      val userId = 1L;
      given(currentRequestUserService.getUserId()).willReturn(userId);
      // ... RealisationRowRepository successfully finds
      val expectedRealisationRow = RealisationRowMother.complete().build();
      given(realisationRowRepository.findByRealisationIdAndRowIdAndRealisationWorkUserId(same(realisationId), same(rowId), same(userId))).willReturn(Optional.of(expectedRealisationRow));

      // WHEN
      // ... getById is called
      val returnedRealisationRow = realisationRowService.getByRealisationIdAndRowId(realisationId, rowId);

      // THEN
      // ... RealisationRowResponseDto is returned
      and.then(returnedRealisationRow)
        .isNotNull()
        .isEqualTo(expectedRealisationRow);
    }

  }

}