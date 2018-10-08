package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

@Profile("prod")
@EnableJpaAuditing
@Component
@ConfigurationProperties(prefix = "statistics")
@Getter
@Setter
class BetStatisticsProperties {

  private Instant lastRoundStatisticCalculationDate;
  private Instant lastLeagueStatisticCalculationDate;
}
