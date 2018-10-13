package pl.michal.olszewski.typer.bet.domain;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface BetStatisticsSaver<T extends BetStatisticsBase> extends org.springframework.data.repository.Repository<T, Long> {

  T save(T bet);

  void deleteAll();
}
