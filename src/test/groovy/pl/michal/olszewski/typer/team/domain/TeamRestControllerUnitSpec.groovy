package pl.michal.olszewski.typer.team.domain


import pl.michal.olszewski.typer.team.dto.TeamNotFoundException
import pl.michal.olszewski.typer.team.dto.command.CreateNewTeam
import spock.lang.Shared
import spock.lang.Specification

class TeamRestControllerUnitSpec extends Specification {

    @Shared
    TeamSaver saver = new InMemoryTeamSaver()
    TeamFinder finder = new InMemoryTeamFinder()
    TeamRestController controller = new TeamRestController(finder, saver)

    def cleanupSpec() {
        saver.deleteAll()
    }

    def 'should throw exception when team by id not exist in DB'() {
        when:
        controller.getTeamInfoById(356)
        then:
        thrown TeamNotFoundException
    }

    def 'should create new team'() {
        given:
        def teamRequest = CreateNewTeam.builder().name("nowyTeam").build()
        when:
        def responseEntity = controller.createNewTeam(teamRequest)
        then:
        responseEntity.getStatusCodeValue() == 201
        and:
        responseEntity.getBody() == null
    }

    def 'should return team info when team by id exists in DB'() {
        given:
        saver.save(Team.builder().id(123).name('name').build())
        when:
        def responseEntity = controller.getTeamInfoById(123)
        then:
        responseEntity.getStatusCodeValue() == 200
        and:
        responseEntity.getBody().id == 123
    }
}
