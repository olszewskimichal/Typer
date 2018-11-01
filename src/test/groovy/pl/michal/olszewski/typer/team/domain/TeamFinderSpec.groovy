package pl.michal.olszewski.typer.team.domain

import org.springframework.beans.factory.annotation.Autowired
import pl.michal.olszewski.typer.BaseDataJpaSpec

class TeamFinderSpec extends BaseDataJpaSpec {

    @Autowired
    TeamFinder finder


    def 'should find team by id'() {
        given:
        def id = persistTeamInDatabase()
        when:
        def team = finder.findOneOrThrow(id)
        then:
        team != null
    }

    def 'should find all teams from DB'() {
        given:
        persistTwoTeamsInDb()
        when:
        def all = finder.findAll()
        then:
        !all.isEmpty()
        and:
        all.size() == 2
    }

    def 'should find team by name when exists in DB'() {
        given: 'having team in DB with name = nazwa'
        persistTeamInDatabase()
        when:
        def byName = finder.findByName('nazwa')
        then:
        byName.isPresent()
    }

    def 'should not find team by name if not exist in db'() {
        when:
        def byName = finder.findByName('notExists')
        then:
        !byName.isPresent()
    }


    private void persistTwoTeamsInDb() {
        entityManager.persist(Team.builder().build())
        entityManager.persist(Team.builder().build())
    }

    private Long persistTeamInDatabase() {
        return entityManager.persistAndGetId(Team.builder().name("nazwa").build()) as Long;
    }
}
