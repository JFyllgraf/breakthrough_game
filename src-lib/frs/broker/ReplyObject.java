package frs.broker;

import javax.servlet.http.HttpServletResponse;

/**
 * Reply object encapsulates the reply from the server - a status code (we reuse
 * the HTTP status codes as they already define a standardized set of codes), a
 * potential error description elaborating the status code, and in case the
 * reply is a valid reply from the server, a payload which is the text returned
 * from the server.
 * <p>
 * The payload needs to be demarshalled by the Requestor to convert it into
 * domain objects and types.
 */
public class ReplyObject {

  private String payload;
  private String errorDescription;
  private final int statusCode;

  /* Include version identity of payload to allow
   * marshalling robustness in future formats.
   */
  private int versionIdentity = Constants.MARSHALLING_VERSION;

  /**
   * Create a reply with the given status code. If the status code represents a
   * valid reply, the description is assigned to the payload, otherwise it is
   * assigned to the error description.
   *
   * @param statusCode
   *          HTTP status code of the reply
   * @param description
   *          associated text, either the payload or the error description
   */
  public ReplyObject(int statusCode, String description) {
    this.statusCode = statusCode;
    payload = errorDescription = null;
    if (isSuccess())
      payload = description;
    else
      errorDescription = description;
  }

  public boolean isSuccess() {
    return statusCode == HttpServletResponse.SC_OK
            || statusCode == HttpServletResponse.SC_CREATED
            || statusCode == HttpServletResponse.SC_NOT_FOUND;
  }

  public String getPayload() {
    return payload;
  }

  public String errorDescription() {
    return errorDescription;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String toString() {
    return "ReplyObject [payload=" + payload + ", errorDescription="
            + errorDescription + ", responseCode="
            + statusCode + "]";
  }

  public int getVersionIdentity() {
    return versionIdentity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReplyObject that = (ReplyObject) o;

    if (statusCode != that.statusCode) return false;
    if (versionIdentity != that.versionIdentity) return false;
    if (payload != null ? !payload.equals(that.payload) : that.payload != null) return false;
    return errorDescription != null ? errorDescription.equals(that.errorDescription) : that.errorDescription == null;
  }

  @Override
  public int hashCode() {
    int result = payload != null ? payload.hashCode() : 0;
    result = 31 * result + (errorDescription != null ? errorDescription.hashCode() : 0);
    result = 31 * result + statusCode;
    result = 31 * result + versionIdentity;
    return result;
  }
}

