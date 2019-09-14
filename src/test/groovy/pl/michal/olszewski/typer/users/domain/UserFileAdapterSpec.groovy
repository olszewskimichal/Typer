package pl.michal.olszewski.typer.users.domain

import pl.michal.olszewski.typer.file.FileStorageProperties
import pl.michal.olszewski.typer.file.FileStorageService
import spock.lang.Specification

import java.nio.file.Paths

class UserFileAdapterSpec extends Specification {

    UserSaver saver = new InMemoryUserSaver()
    UserFinder finder = new InMemoryUserFinder()
    UserFileAdapter adapter = new UserFileAdapter(finder, saver, new FileStorageService(new FileStorageProperties("uploads")))

    def setup() {
        saver.deleteAll()
    }

    def 'should create one user from xlsx file'() {
        given:
        def file = Paths.get("testresources/userfile.xlsx")
        when:
        adapter.loadUsersFromFile(file)
        then:
        def all = finder.findAll()
        and:
        all.size() == 1
    }

    def 'should create one user from xls file'() {
        given:
        def file = Paths.get("testresources/userfile.xls")
        when:
        adapter.loadUsersFromFile(file)
        then:
        def all = finder.findAll()
        and:
        all.size() == 1
    }

}
