package pl.michal.olszewski.typer.match.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MatchFileAdapterTest {

    private MatchFileAdapter matchFileAdapter;
    private MatchFinder matchFinder;

    @BeforeEach
    void setUp() {
        MatchSaver matchSaver = new InMemoryMatchSaver();
        MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
        matchRoundSaver.save(MatchRound.builder().id(1L).build());
        matchFinder = new InMemoryMatchFinder();
        matchFileAdapter = new MatchFileAdapter(new MatchCreator(new InMemoryMatchRoundFinder()), matchSaver);
        matchSaver.deleteAll();
        matchRoundSaver.deleteAll();
    }


    @Test
    void shouldCreateOneMatchLeagueFromXlsxFile() throws IOException {
        //given
        Path file = Paths.get("testresources/match.xlsx");

        //when
        matchFileAdapter.loadMatchesFromFile(file);

        //then
        assertThat(matchFinder.findAll()).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldCreateOneMatchLeagueFromXlsFile() throws IOException {
        Path file = Paths.get("testresources/match.xls");

        //when
        matchFileAdapter.loadMatchesFromFile(file);

        //then
        assertThat(matchFinder.findAll()).isNotEmpty().hasSize(1);
    }
}