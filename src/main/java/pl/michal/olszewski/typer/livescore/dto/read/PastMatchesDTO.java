package pl.michal.olszewski.typer.livescore.dto.read;

import lombok.Value;

@Value
public class PastMatchesDTO {

  private boolean success;
  private PastMatchesDataDTO data;
}
