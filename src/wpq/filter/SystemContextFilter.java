package wpq.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import wpq.model.SystemContext;
import wpq.model.User;

public class SystemContextFilter implements Filter {
	private int pageSize;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		try {
			int pageOffset = 0;
			try {
				pageOffset = Integer.parseInt(arg0.getParameter("pager.offset"));
			} catch (NumberFormatException e) {
			}
			try {
				pageSize = Integer.parseInt(arg0.getParameter("pageSize"));
			} catch (NumberFormatException e) {
			}
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(pageSize);
			HttpServletRequest hreq = (HttpServletRequest) arg0;
			User u = (User) hreq.getSession().getAttribute("loginUser");
			if(u!=null) SystemContext.setLoginUser(u);
			arg2.doFilter(arg0, arg1);
		} finally{
			SystemContext.removePageOffset();
			SystemContext.removePageSize();
			SystemContext.removeLoginUser();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			pageSize = Integer.parseInt(arg0.getInitParameter("pageSize"));
		} catch (NumberFormatException e) {
			pageSize = 10;
		}
	}

}
