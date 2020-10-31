package com.chatak.transit.afcs.email.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Email Threads handler
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EmailThreadLocal {
	 private static final int MAX_EMAIL_SERVIVE_CLIENT = 10;

  private static Map threadMap = new HashMap();

  private EmailThreadLocal() {
    // Do Nothing
  }

  static {
    for (int i = 0; i < MAX_EMAIL_SERVIVE_CLIENT; i++) {
      EmailServiceClient serviceClient = EmailServiceClient.get();
      EmailThreadLocal.add(serviceClient);
    }
  }

  public static void add(Object object) {
    threadMap.put(Thread.currentThread(), object);
  }

  public static void remove() {
    threadMap.remove(Thread.currentThread());
  }

  public static Object get() {
    Object threadObj = threadMap.get(Thread.currentThread());
    if (null == threadObj) {
      for (int i = 0; i < MAX_EMAIL_SERVIVE_CLIENT; i++) {
        EmailServiceClient serviceClient = EmailServiceClient.get();
        EmailThreadLocal.add(serviceClient);
      }
      return threadMap.get(Thread.currentThread());
    } else {
      return threadObj;
    }
  }
}
