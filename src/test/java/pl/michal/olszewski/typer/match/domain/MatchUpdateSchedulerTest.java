package pl.michal.olszewski.typer.match.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;

class MatchUpdateSchedulerTest {

  private CheckMatchPublisher publisher = Mockito.mock(CheckMatchPublisher.class);
  private MatchFinder finder = new InMemoryMatchFinder();
  private MatchUpdateScheduler scheduler = new MatchUpdateScheduler(finder, publisher);

  @BeforeEach
  void setUp() {
    givenMatch()
        .deleteAll();
  }

  @Test
  void shouldSendOneCheckMatchCommandToQueue() {
    //given
    givenMatch()
        .buildAndSave(null, MatchStatus.NEW, 3L);
    //when
    scheduler.createCheckMatchResultCommands();

    //then
    Mockito.verify(publisher, Mockito.times(1)).sendCheckMatchCommandToJms(Mockito.any(CheckMatchResults.class));
  }

  @Test
  void shouldNotSendAnyCommandToQueueWhenDoNotHaveAnyMatchForCheck() {
    //given
    //when
    scheduler.createCheckMatchResultCommands();

    //then
    Mockito.verifyNoMoreInteractions(publisher);
  }


  private MatchFactory givenMatch() {
    return new MatchFactory(new InMemoryMatchSaver());
  }
}