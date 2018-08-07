package pl.michal.olszewski.typer.bet.domain;

enum BetTypePolicy {

  DADBET_POLICY {
    @Override
    BetPolicy getBetPolicy() {
      return new DadBetPolicy();
    }
  },

  MYBET_POLICY {
    @Override
    BetPolicy getBetPolicy() {
      return new MyBetPolicy();
    }
  },

  WORKBET_POLICY {
    @Override
    public BetPolicy getBetPolicy() {
      return new WorkBetPolicy();
    }
  };

  abstract BetPolicy getBetPolicy();
}
