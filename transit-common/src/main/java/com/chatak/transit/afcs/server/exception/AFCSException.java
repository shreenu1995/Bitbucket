package com.chatak.transit.afcs.server.exception;

public class AFCSException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
	 * 
	 */
  public AFCSException() {
    super();
  }

  /**
   * @param arg0
   */
  public AFCSException(String arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   */
  public AFCSException(Throwable arg0) {
    super(arg0);
  }

  /**
   * @param arg0
   * @param arg1
   */
  public AFCSException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
