package pl.michal.olszewski.typer.livescore.read;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Value;

@Value
public class PastMatchesDataDTO {

  @JsonProperty("match")
  private List<MatchDTO> matches;
}
