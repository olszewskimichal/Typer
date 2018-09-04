package pl.michal.olszewski.typer.livescore;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LivescoreApiTest {

  @Autowired
  private LivescoreApiClient webTestClient;

  @Test
  void shouldGetPastMatchesForLeague() {
    //given
    Long leagueId = 19L;

    PastMatchesDTO responseBody = webTestClient.getPastMatchesForLeagueFromDate(leagueId, null)
        .block();

    assertThat(responseBody.getData()).isNotNull();
    assertThat(responseBody.getData().getMatches()).isNotNull().isNotEmpty();
    System.out.println(responseBody);
  }

  @Test
  void shouldGetPastMatchesFromDateToDate() {
    //given
    Long leagueId = 19L;
    LocalDate from = LocalDate.of(2018, 8, 10);

    PastMatchesDTO responseBody = webTestClient.getPastMatchesForLeagueFromDate(leagueId, from).block();

    assertThat(responseBody.getData()).isNotNull();
    assertThat(responseBody.getData().getMatches()).isNotNull().isNotEmpty();
    System.out.println(responseBody);
    assertThat(responseBody.getData().getMatches().stream().anyMatch(v -> v.getDate().isBefore(from))).isFalse();
  }
}
