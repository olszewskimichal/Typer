package pl.michal.olszewski.typer.livescore;

import lombok.Value;

@Value
class PastMatchesDTO {

  private boolean success;
  private PastMatchesDataDTO data;
}
