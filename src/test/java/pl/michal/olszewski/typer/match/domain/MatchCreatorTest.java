package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.IllegalMatchMemberException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

class MatchCreatorTest {

  private MatchRoundFinder matchRoundFinder = new InMemoryMatchRoundFinder();
  private MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();

  @BeforeEach
  void setUp() {
    matchRoundSaver.deleteAll();
  }

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> MatchCreator.from(null, matchRoundFinder));
  }

  @Test
  void shouldCreateMatchWhenCommandIsValidAndSetStatusAsNEW() {
    //given
    MatchRound matchRound = MatchRound.builder().name("a").id(3L).build();
    matchRoundSaver.save(matchRound);
    Match expected = Match.builder().homeTeamId(1L).awayTeamId(2L).matchStatus(MatchStatus.NEW).matchRound(matchRound).build();

    CreateNewMatch createNewMatch = CreateNewMatch
        .builder()
        .awayTeamId(2L)
        .homeTeamId(1L)
        .roundId(3L)
        .build();
    //when
    Match from = MatchCreator.from(createNewMatch, matchRoundFinder);
    //then
    assertThat(from).isNotNull();
    assertThat(from).isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenMatchRoundIsNull() {
    //given
    //when
    //then
    assertThrows(MatchRoundNotFoundException.class, () -> MatchCreator.from(CreateNewMatch.builder().awayTeamId(2L).homeTeamId(1L).build(), matchRoundFinder));
  }

  @Test
  void shouldThrowExceptionWhenOneOfTeamsIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalMatchMemberException.class, () -> MatchCreator.from(CreateNewMatch.builder().awayTeamId(null).homeTeamId(1L).roundId(1L).build(), matchRoundFinder));
    assertThrows(IllegalMatchMemberException.class, () -> MatchCreator.from(CreateNewMatch.builder().awayTeamId(1L).homeTeamId(null).roundId(2L).build(), matchRoundFinder));
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIsTheSameAsAwayTeam() {
    //given
    CreateNewMatch createNewMatch = CreateNewMatch.builder().awayTeamId(1L).homeTeamId(1L).roundId(2L).build();

    //when
    //then
    assertThrows(IllegalMatchMemberException.class, () -> MatchCreator.from(createNewMatch, matchRoundFinder));
  }

  @Test
  void shouldThrowExceptionWhenMatchRoundIsNotFoundInDb() {
    //given
    CreateNewMatch createNewMatch = CreateNewMatch
        .builder()
        .awayTeamId(2L)
        .homeTeamId(1L)
        .roundId(1L)
        .build();
    //when
    //then
    assertThrows(MatchRoundNotFoundException.class, () -> MatchCreator.from(createNewMatch, matchRoundFinder));
  }


}