package pl.michal.olszewski.typer.team.domain

import pl.michal.olszewski.typer.team.dto.TeamExistsException
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam
import spock.lang.Specification
import spock.lang.Unroll

import static pl.michal.olszewski.typer.team.domain.TeamCreator.from

class TeamCreatorSpec extends Specification {

    TeamSaver saver = new InMemoryTeamSaver()
    TeamFinder finder = new InMemoryTeamFinder()

    @Unroll
    def 'should throw exception when incorrect message'() {
        given:
        saver.save(Team.builder().id(3L).name("nazwa").build())
        when:
        from(command, finder)
        then:
        thrown exception

        where:
        exception                | command
        NullPointerException     | null
        IllegalArgumentException | CreateNewTeam.builder().name(null).build()
        TeamExistsException      | CreateNewTeam.builder().name("nazwa").build()
    }

    def 'should create new team when command is correct'() {
        given:
        def team = CreateNewTeam.builder().name("nowyTeam").build()
        when:
        def result = from(team, finder)
        then:
        result != null
        and:
        result.name == 'nowyTeam'
    }
}
