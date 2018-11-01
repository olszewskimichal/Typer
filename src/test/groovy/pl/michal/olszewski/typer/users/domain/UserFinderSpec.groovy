package pl.michal.olszewski.typer.users.domain

import org.springframework.beans.factory.annotation.Autowired
import pl.michal.olszewski.typer.BaseDataJpaSpec
import pl.michal.olszewski.typer.users.UserNotFoundException

class UserFinderSpec extends BaseDataJpaSpec {

    @Autowired
    UserFinder finder

    def 'should find user by id when exists in database'() {
        given: 'persist user in database'
        def userId = persistUserInDatabase()
        when:
        def user = finder.findOneOrThrow(userId)
        then:
        user != null
        user.id != null
    }

    def 'should thrown exception when searching user by id not exist'() {
        when:
        finder.findOneOrThrow(4L)
        then:
        thrown UserNotFoundException
    }

    def 'should find all users from database'() {
        given: 'having two users in database'
        persistTwoUsersInDb()
        when:
        def all = finder.findAll()
        then:
        !all.isEmpty()
        all.size() == 2
    }

    def 'should not find user by email if not exist'() {
        when:
        def user = finder.findByEmail('emailTest')
        then:
        !user.isPresent()
    }

    def 'should find user by email if exists in DB'() {
        given: 'having user with email in DB'
        entityManager.persist(User.builder().email("email1").build())
        when:
        def user = finder.findByEmail('email1')
        then:
        user.isPresent()
        user.get().email == 'email1'
    }

    private void persistTwoUsersInDb() {
        entityManager.persist(User.builder().build())
        entityManager.persist(User.builder().build())
    }

    private Long persistUserInDatabase() {
        User user = User.builder().build()
        Long id = (Long) entityManager.persistAndGetId(user)
        entityManager.flush()
        entityManager.clear()
        return id
    }


}
