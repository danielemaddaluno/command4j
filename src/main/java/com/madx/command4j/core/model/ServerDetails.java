package com.madx.command4j.core.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

/**
 * Model class containing details of the server where the target file is stored.
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
public class ServerDetails {

	private static final List<String> localhostAliases = ImmutableList.<String> builder().add("localhost", "127.0.0.1").build();
	private final String host;
	private String user;
	private String password;
	private Integer port;
	private String privateKeyLocation;
	private boolean isPasswordRequired;

	/**
	 * The hostname of the server where the target file is stored. This can be either an IP or proper hostname. In case of a local server, the
	 * hostname has to be either "localhost" or "127.0.0.1"
	 * 
	 * @param host
	 */
	public ServerDetails(String host) {
		this.host = host;
		this.port = 22;
	}

	/**
	 * This method check if this ServerDetails is a "localhost" or "127.0.0.1" otherwise return false
	 * 
	 * @return true if localhost
	 */
	public boolean isLocalhost() {
		return localhostAliases.contains(host.toLowerCase());
	}

	public void validate() {
		if (StringUtils.isEmpty(host) || StringUtils.isBlank(host)) {
			throw new IllegalArgumentException("Host is empty or null");
		}
		if (!isLocalhost()) {
			if (StringUtils.isEmpty(user) || StringUtils.isBlank(user)) {
				throw new IllegalArgumentException("User is empty or null");
			}
			if (isPasswordRequired()) {
				String errorComponent = "Password";
				if (!StringUtils.isEmpty(privateKeyLocation) && !StringUtils.isBlank(privateKeyLocation)) {
					errorComponent = "Passphrase";
				}
				if (StringUtils.isEmpty(password) || StringUtils.isBlank(password)) {
					throw new IllegalArgumentException(errorComponent + " is empty or null");
				}
			}
			if (port < 1 || port > 65535) {
				throw new IllegalArgumentException("Port out of possible ranges (1 to 65535)");
			}
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPrivateKeyLocation() {
		return privateKeyLocation;
	}

	public void setPrivateKeyLocation(String privateKeyLocation) {
		this.privateKeyLocation = privateKeyLocation;
	}

	public boolean isPasswordRequired() {
		return isPasswordRequired;
	}

	public void setPasswordRequired(boolean isPasswordRequired) {
		this.isPasswordRequired = isPasswordRequired;
	}

	public String getHost() {
		return host;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + (isPasswordRequired ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((privateKeyLocation == null) ? 0 : privateKeyLocation.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerDetails other = (ServerDetails) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (isPasswordRequired != other.isPasswordRequired)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (privateKeyLocation == null) {
			if (other.privateKeyLocation != null)
				return false;
		} else if (!privateKeyLocation.equals(other.privateKeyLocation))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServerDetails [host=" + host + ", user=" + user + ", password=" + password + ", port=" + port
				+ ", privateKeyLocation=" + privateKeyLocation + ", isPasswordRequired=" + isPasswordRequired + "]";
	}

}
