package pl.michal.olszewski.typer.team.domain

import pl.michal.olszewski.typer.file.FileStorageProperties
import pl.michal.olszewski.typer.file.FileStorageService
import spock.lang.Specification

import java.nio.file.Paths

class TeamFileAdapterSpec extends Specification {
    def saver = new InMemoryTeamSaver()
    def finder = new InMemoryTeamFinder()
    def adapter = new TeamFileAdapter(finder, saver, new FileStorageService(new FileStorageProperties("uploads")))


    def setup() {
        saver.deleteAll()
    }

    def 'should create one team from xlsx file'() {
        given:
        def file = Paths.get("testresources/team.xlsx")
        when:
        adapter.loadTeamsFromFile(file)
        then:
        def all = finder.findAll()
        and:
        all.size() == 1
    }

    def 'should create one team from xls file'() {
        given:
        def file = Paths.get("testresources/team.xls")
        when:
        adapter.loadTeamsFromFile(file)
        then:
        def all = finder.findAll()
        and:
        all.size() == 1
    }
}
