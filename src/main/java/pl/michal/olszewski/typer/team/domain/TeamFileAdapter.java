package pl.michal.olszewski.typer.team.domain;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam;

@Component
class TeamFileAdapter {

  private static final String name = "name";
  private static final List<String> defaultColumns = Collections.singletonList(name);

  private final TeamCreator teamCreator;
  private final TeamSaver teamSaver;

  public TeamFileAdapter(TeamCreator teamCreator, TeamSaver teamSaver) {
    this.teamCreator = teamCreator;
    this.teamSaver = teamSaver;
  }

  void loadTeamsFromFile(File file) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(file)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String name = fileAdapterRow.get(TeamFileAdapter.name);
        CreateNewTeam createNewTeam = CreateNewTeam.builder().name(name).build();
        Team from = teamCreator.from(createNewTeam);
        teamSaver.save(from);
      }
    }
  }

  private FileAdapter selectAdapter(File file) throws IOException {
    if (file.getName().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(file, TeamFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(file, TeamFileAdapter.defaultColumns);
    }
  }
}
