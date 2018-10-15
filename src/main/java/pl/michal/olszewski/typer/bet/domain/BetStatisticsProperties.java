package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "statistics")
@Getter
@Setter
class BetStatisticsProperties {

  private Instant lastStatisticCalculationDate;
}
