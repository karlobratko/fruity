package hr.algebra.fruity.service;

import hr.algebra.fruity.dto.response.AgentResponseDto;
import hr.algebra.fruity.exception.EntityNotFoundException;
import hr.algebra.fruity.model.Agent;
import hr.algebra.fruity.repository.AgentRepository;
import hr.algebra.fruity.service.impl.AgentServiceImpl;
import hr.algebra.fruity.utils.mother.dto.AgentResponseDtoMother;
import hr.algebra.fruity.utils.mother.model.AgentMother;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import static com.googlecode.catchexception.apis.BDDCatchException.caughtException;
import static com.googlecode.catchexception.apis.BDDCatchException.when;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("AgentService Unit Test")
@SuppressWarnings("static-access")
public class AgentServiceUnitTest implements ServiceUnitTest {

  @InjectMocks
  private AgentServiceImpl agentService;

  @Mock
  private ConversionService conversionService;

  @Mock
  private AgentRepository agentRepository;

  @Nested
  @DisplayName("... WHEN getAllAgents is called")
  public class WHEN_getAllAgents {

    @Test
    @DisplayName("GIVEN non-empty AgentRepository " +
      "... THEN List<AgentResponseDto> is returned")
    public void GIVEN_nonEmptyRepository_THEN_ListOfAgentResponseDto() {
      // GIVEN
      // ... non-empty AgentRepository
      val agents = List.of(
        AgentMother.complete().id(1).build(),
        AgentMother.complete().id(2).build(),
        AgentMother.complete().id(3).build()
      );
      given(agentRepository.findAll()).willReturn(agents);
      // ... ConversionService successfully converts from Agent to AgentResponseDto
      given(conversionService.convert(any(Agent.class), same(AgentResponseDto.class))).willAnswer(invocation -> AgentResponseDtoMother.complete().id(invocation.<Agent>getArgument(0).getId()).build());

      // WHEN
      // ... getAllAgents is called
      val responseDtos = agentService.getAllAgents();

      // THEN
      // ... List<AgentResponseDto> is returned
      and.then(responseDtos).satisfies(it -> {
        and.then(responseDtos).hasSize(agents.size());
        and.then(responseDtos).allSatisfy(child -> and.then(child).isNotNull());
      });
    }

  }

  @Nested
  @DisplayName("... WHEN getAgentById is called")
  public class WHEN_getAgentById {

    @Test
    @DisplayName("GIVEN id " +
      "... THEN AgentResponseDto is returned")
    public void GIVEN_id_THEN_AgentResponseDto() {
      // GIVEN
      // ... id
      val id = 1;
      val agent = AgentMother.complete().build();
      given(agentRepository.findById(same(id))).willReturn(Optional.of(agent));
      // ... ConversionService successfully converts from Agent to AgentResponseDto
      val expectedResponseDto = AgentResponseDtoMother.complete().build();
      given(conversionService.convert(same(agent), same(AgentResponseDto.class))).willReturn(expectedResponseDto);

      // WHEN
      // ... getAgentById is called
      val responseDto = agentService.getAgentById(id);

      // THEN
      // ... AgentResponseDto is returned
      and.then(responseDto)
        .isNotNull()
        .isEqualTo(expectedResponseDto);
    }

  }


  @Nested
  @DisplayName("WHEN getById is called")
  public class WHEN_getById {

    @Test
    @DisplayName("GIVEN invalid id " +
      "... THEN EntityNotFoundException is thrown")
    public void GIVEN_invalidId_THEN_EntityNotFoundException() {
      // GIVEN
      // ... invalid id
      val id = 1;
      given(agentRepository.findById(same(id))).willReturn(Optional.empty());

      // WHEN
      // ... getById is called
      when(() -> agentService.getById(id));

      // THEN
      // ... EntityNotFoundException is thrown
      and.then(caughtException())
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage(EntityNotFoundException.Constants.exceptionMessageFormat)
        .hasNoCause();
    }

    @Test
    @DisplayName("GIVEN id " +
      "... THEN Agent is returned")
    public void GIVEN_id_THEN_Agent() {
      // GIVEN
      // ... id
      val id = 1;
      val expectedAgent = AgentMother.complete().build();
      given(agentRepository.findById(same(id))).willReturn(Optional.of(expectedAgent));

      // WHEN
      // ... getById is called
      val returnedAgent = agentService.getById(id);

      // THEN
      // ... AgentResponseDto is returned
      and.then(returnedAgent)
        .isNotNull()
        .isEqualTo(expectedAgent);
    }

  }

}
