package pl.michal.olszewski.typer.users.domain

import pl.michal.olszewski.typer.users.UserNotFoundException
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser
import spock.lang.Shared
import spock.lang.Specification

class UserRestControllerUnitSpec extends Specification {
    @Shared
    UserSaver saver = new InMemoryUserSaver()
    UserFinder finder = new InMemoryUserFinder()
    UserRestController controller = new UserRestController(finder, saver)

    def setup() {
        saver.deleteAll()
    }

    def 'should create new user'() {
        given:
        def createNewUser = CreateNewUser.builder().email("email").username("name").build()
        when:
        def responseEntity = controller.createNewUser(createNewUser)
        then:
        responseEntity.getStatusCodeValue() == 201
        and:
        responseEntity.getBody() == null
    }

    def 'should return user info when user exists in DB'() {
        given:
        saver.save(User.builder().id(254L).email("nazwa").username("nazwa").build())
        when:
        def responseEntity = controller.getUserInfoById(254L)
        then:
        responseEntity.getStatusCodeValue() == 200
        and:
        responseEntity.getBody().getId() == 254
    }

    def 'should throw exception when user by id not exists'() {
        when:
        controller.getUserInfoById(255L)
        then:
        thrown UserNotFoundException
    }
}
