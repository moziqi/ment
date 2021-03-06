package hemu.ment.core.controller;

import hemu.ment.core.cache.CacheConsole;
import hemu.ment.core.cache.SessionObject;
import hemu.ment.core.constant.C;
import hemu.ment.core.ejb.local.UserLocal;
import hemu.ment.core.entity.Enterprise;
import hemu.ment.core.entity.User;
import hemu.ment.core.exception.InformationException;
import hemu.ment.core.utility.ContextUtil;
import hemu.ment.core.utility.EncryptionUtil;
import hemu.ment.core.utility.FacesMessageUtil;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -2228271739572661055L;

	private String email;
	private String password;
	private boolean rememberMe;
	private String authToken;

	private User user;
	private Enterprise enterprise;

	@Inject
	private CacheConsole cacheConsole;

	@EJB
	private UserLocal userEJB;

	public LoginBean() {
	}

	public String login() {
		try {
			user = userEJB.login(email, password);
			enterprise = user.getEnterprise();
			authToken = EncryptionUtil.generateAuthToken();
			ContextUtil.clearSession();
			ContextUtil.getSession().setAttribute(C.AUTH_TOKEN, authToken);
			cacheConsole.cacheSession(authToken, new SessionObject(user));
			cacheConsole.cacheSession(ContextUtil.getClientAddress(), authToken);
			return "/c/sso.xhtml?faces-redirect=true";
		} catch (InformationException e) {
			FacesMessageUtil.addErrorMessage(e.getMessage(), null);
			return (email = password = null);
		}
	}

	public String logout() {
		cacheConsole.invalidateSession((String) ContextUtil.getSessionAttr("authToken"));
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public String getFullName() {
		return user.getFullName();
	}

	public boolean isAuthenticated() {
		return user != null;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public long getId() {
		return user.getId();
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
