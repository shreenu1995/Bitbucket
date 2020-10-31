package com.afcs.web.util;

import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import com.afcs.web.constants.Constants;
import com.chatak.transit.afcs.server.util.StringUtil;


public final class PaginationUtil {

	private static final int DEFAULT_SIZE = 10;

  private PaginationUtil() {

  }

  public static int getMaxDisplayCount() {
    int maxCount = DEFAULT_SIZE;
    String displaySize = Constants.TEN;

    if(!StringUtil.isNullEmpty(displaySize)) {
      maxCount = Integer.parseInt(displaySize);
    }
    return maxCount;
  }

  public static void performPagination(ModelAndView modelAndView, final HttpSession session, final Integer pageNumber, 
		  final String searchAttribute) {
      final int maxCount = getMaxDisplayCount();
      Integer totalNoOfRows = (Integer) session.getAttribute(searchAttribute);
      int pageCounter =
          (totalNoOfRows / maxCount) + ((totalNoOfRows % maxCount > 0) ? Constants.ONE : 0);
      session.setAttribute(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      session.setAttribute(Constants.PAGE_NUMBER, pageNumber);
      modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, pageNumber);
      int begin = Constants.ONE;
      int end = Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE;

      if (pageNumber > Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE) {
        begin = pageNumber - (Constants.FOUR + Constants.ONE);
        end = pageNumber + Constants.FOUR;

        if (end > pageCounter) {
          end = pageCounter;
          begin = end - Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE + Constants.ONE;
        }

        begin = (begin + Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE > pageCounter)
            ? pageCounter - (Constants.MAX_ENTITIES_PORTAL_DISPLAY_SIZE - Constants.ONE) : begin;
      } else {
        end = (end > pageCounter) ? pageCounter : end;
      }

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, begin);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM, end);

  }
  
  public static void performPagination(int rowCount, int maxCount, HttpSession session, ModelAndView modelAndView) {
      int pageCounter = (rowCount / maxCount) + ((rowCount % maxCount > 0) ? Constants.ONE : 0);
      session.setAttribute(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.PORTAL_PAGES_SESSION_NAME, pageCounter);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_PAGE_NUMBER, Constants.ONE);

      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_BEGIN_PAGE_NUM, Constants.ONE);
      modelAndView.addObject(Constants.MODEL_ATTRIBUTE_PORTAL_LIST_END_PAGE_NUM,
          (pageCounter > Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE)
              ? Constants.MAX_ENTITIES_PAGINATION_DISPLAY_SIZE : pageCounter);
  }
  
}
