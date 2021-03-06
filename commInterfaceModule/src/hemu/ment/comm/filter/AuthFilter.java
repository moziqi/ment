package hemu.ment.comm.filter;

import hemu.ment.comm.utility.ContextUtil;
import hemu.ment.core.cache.CacheConsole;
import hemu.ment.core.constant.C;
import hemu.ment.core.utility.EncryptionUtil;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by muu on 2015/6/12.
 */
public class AuthFilter implements Filter {

	@Inject
	private CacheConsole cacheConsole;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	                     FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String authToken = (String) req.getSession().getAttribute(C.AUTH_TOKEN);
		if (authToken != null && authToken.equals(cacheConsole.getAuthToken(ContextUtil.getClientAddress(req)))) {
			chain.doFilter(request, response);
		} else {
			res.sendRedirect("http://www.entconsole.com/index.xhtml");
		}
	}

	@Override
	public void destroy() {

	}

}
