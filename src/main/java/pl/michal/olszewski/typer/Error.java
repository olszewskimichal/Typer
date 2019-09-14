package pl.michal.olszewski.typer;

import java.util.Objects;

public class Error {

  private final String code;

  private final String message;

  private final String details;

  private final String path;

  private final String userMessage;

  private Error(String code, String message, String details, String path, String userMessage) {
    this.code = code;
    this.message = message;
    this.details = details;
    this.path = path;
    this.userMessage = userMessage;
  }

  public static Builder error() {
    return new Builder();
  }

  public static Builder error(String code) {
    return new Builder().withCode(code);
  }

  /**
   * @return message for technical interpretation (i.e. message code)
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return path to field that contains error
   */
  public String getPath() {
    return path;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, path, message, details, userMessage);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Error)) {
      return false;
    }

    Error other = (Error) obj;
    return Objects.equals(code, other.code)
        && Objects.equals(path, other.path)
        && Objects.equals(message, other.message)
        && Objects.equals(details, other.details)
        && Objects.equals(userMessage, other.userMessage);
  }

  @Override
  public String toString() {
    return String.format("Error{code='%s', message='%s', details='%s', path='%s', userMessage='%s'}", code, message, details, path, userMessage);
  }

  public static final class Builder {

    private static final String DEFAULT_CODE = "ERROR";
    private static final String DEFAULT_MESSAGE = "An error has occurred";

    private String code = DEFAULT_CODE;

    private String message = DEFAULT_MESSAGE;

    private String details;

    private String path;

    private String userMessage;

    private Builder() {
    }

    public Error build() {
      return new Error(code, message, details, path, userMessage);
    }


    Builder withCode(String code) {
      this.code = code;
      return this;
    }

    Builder withMessage(String message) {
      this.message = message;
      return this;
    }

    Builder withDetails(String details) {
      this.details = details;
      return this;
    }

    Builder withUserMessage(String userMessage) {
      this.userMessage = userMessage;
      return this;
    }

    Builder withPath(String path) {
      this.path = path;
      return this;
    }

  }
}