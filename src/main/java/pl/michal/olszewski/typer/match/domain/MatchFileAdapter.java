package pl.michal.olszewski.typer.match.domain;

import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
class MatchFileAdapter {

    private static final String roundId = "name";
    private static final String homeTeamId = "homeTeamId";
    private static final String awayTeamId = "awayTeamId";
    private static final List<String> defaultColumns = Arrays.asList(roundId, homeTeamId, awayTeamId);

    private final MatchCreator matchCreator;
    private final MatchSaver matchSaver;

    public MatchFileAdapter(MatchCreator matchCreator, MatchSaver matchSaver) {
        this.matchCreator = matchCreator;
        this.matchSaver = matchSaver;
    }

    void loadMatchesFromFile(Path path) throws IOException {
        try (FileAdapter fileAdapter = selectAdapter(path)) {
            for (FileAdapterRow fileAdapterRow : fileAdapter) {
                Long roundId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.roundId));
                Long homeTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.homeTeamId));
                Long awayTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.awayTeamId));
                Match matchLeague = matchCreator.from(CreateNewMatch.builder().roundId(roundId).homeTeamId(homeTeamId).awayTeamId(awayTeamId).build());
                matchSaver.save(matchLeague);
            }
        }
    }

    private FileAdapter selectAdapter(Path path) throws IOException {
        if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
            return new XlsxAdapter(path, MatchFileAdapter.defaultColumns);
        } else {
            return new XlsAdapter(path, MatchFileAdapter.defaultColumns);
        }
    }
}
