package pl.michal.olszewski.typer.bet.domain;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


@NoRepositoryBean
@Transactional(readOnly = true)
interface BetStatisticsFinder<T extends BetStatisticsBase> extends Repository<T, Long> {

  List<T> findAll();  //TODO dopisac test i ograniczyc uzycie tej metody w testach

}
