package pl.michal.olszewski.typer.livescore.dto.read;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PastMatchesDataDTO {

  @JsonProperty("match")
  private List<MatchDTO> matches;
}
