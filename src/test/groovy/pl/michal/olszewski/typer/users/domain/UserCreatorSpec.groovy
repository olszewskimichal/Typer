package pl.michal.olszewski.typer.users.domain

import pl.michal.olszewski.typer.users.UserExistsException
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class UserCreatorSpec extends Specification {

    @Shared
    UserSaver saver = new InMemoryUserSaver()
    UserFinder finder = new InMemoryUserFinder()

    def cleanupSpec() {
        saver.deleteAll()
    }

    @Unroll
    def 'should thrown exception when incorrect message'() {

        given: 'Having one user with email = email'
        saver.save(User.builder().id(3L).email("email").build())
        when:
        UserCreator.from(b, finder)
        then:
        thrown a
        where:
        a                        | b
        NullPointerException     | null
        IllegalArgumentException | CreateNewUser.builder().email("a").username(null).build()
        IllegalArgumentException | CreateNewUser.builder().username("a").email(null).build()
        UserExistsException      | CreateNewUser.builder().email("email").username("username").build()
    }

    def 'should create new user when command is correct'() {

        given:
        CreateNewUser createNewUser = CreateNewUser.builder().email("email2").username("username").build()
        when:
        def user = UserCreator.from(createNewUser, finder)
        then:
        verifyAll {
            user.email == 'email2'
            user.username == 'username'
        }
    }
}
