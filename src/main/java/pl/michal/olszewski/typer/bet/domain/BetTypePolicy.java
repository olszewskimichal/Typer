package pl.michal.olszewski.typer.bet.domain;

enum BetTypePolicy {

  DADBET_POLICY(0L) {
    @Override
    BetPolicy getBetPolicy() {
      return new DadBetPolicy();
    }
  },

  MYBET_POLICY(1L) {
    @Override
    BetPolicy getBetPolicy() {
      return new MyBetPolicy();
    }
  },

  WORKBET_POLICY(2L) {
    @Override
    public BetPolicy getBetPolicy() {
      return new WorkBetPolicy();
    }
  };

  private long value;

  BetTypePolicy(long value) {
    this.value = value;
  }

  static BetTypePolicy fromValue(long value) {
    for (BetTypePolicy typePolicy : BetTypePolicy.values()) {
      if (typePolicy.getValue() == value) {
        return typePolicy;
      }
    }
    throw new IllegalArgumentException(String.format("Nieprawidlowy identyfikator polityki - takowa o id %s nie istnieje", value));
  }

  public long getValue() {
    return value;
  }

  abstract BetPolicy getBetPolicy();
}
