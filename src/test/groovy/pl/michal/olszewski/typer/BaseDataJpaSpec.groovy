package pl.michal.olszewski.typer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

@DataJpaTest
abstract class BaseDataJpaSpec extends Specification {

    def cleanup() {
        entityManager.clear()
    }

    @Autowired
    TestEntityManager entityManager

}
