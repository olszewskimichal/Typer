package pl.michal.olszewski.typer.livescore;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Value;

@Value
class PastMatchesDataDTO {

  @JsonProperty("match")
  private List<MatchDTO> matches;
}
