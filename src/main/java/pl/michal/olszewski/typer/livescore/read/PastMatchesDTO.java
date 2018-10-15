package pl.michal.olszewski.typer.livescore.read;

import lombok.Value;

@Value
public class PastMatchesDTO {

  private boolean success;
  private PastMatchesDataDTO data;
}
