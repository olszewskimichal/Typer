package pl.michal.olszewski.typer.livescore.dto.read;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class PastMatchesDTO {

  private boolean success;
  private PastMatchesDataDTO data;
}
