package com.madx.command4j.core.model;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.StringUtils;

/**
 * Model class representing a file that will be the target of the grep command. The Server Details class attributes will specify how to access the
 * file (either local file or credentials for accessing a remote machine)
 * 
 * @author Giovanni Gargiulo
 * @author Marco Castigliego
 */
@Immutable
public class Profile {

	private final String name;
	private final String filePath;
	private ServerDetails serverDetails;
	private Integer maxTaskThreads;

	public Profile(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}

	public boolean containsWildcard() {
		return StringUtils.contains(filePath, "*");
	}
	
	public static Profile copy(Profile from, String newFilePath){
		Profile p = new Profile(from.name, newFilePath);
		p.setServerDetails(from.serverDetails);
		return p;
	}

	public void validate() {
		if (StringUtils.isEmpty(name) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Profile name is empty or null");
		}
		String suffix = "Validation error for Profile [" + name + "]:";
		if (StringUtils.isEmpty(filePath) || StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException(suffix + "FilePath is empty or null");
		}
		if (serverDetails == null) {
			throw new IllegalArgumentException(suffix + "ServerDetails is empty or null");
		} else {
			try {
				serverDetails.validate();
			} catch (IllegalArgumentException exception) {
				throw new IllegalArgumentException(suffix + exception.getMessage());
			}
		}

	}

	public Integer getMaxThreads() {
		return maxTaskThreads;
	}

	public ServerDetails getServerDetails() {
		return serverDetails;
	}

	public void setServerDetails(ServerDetails serverDetails) {
		this.serverDetails = serverDetails;
	}

	public Integer getMaxTaskThreads() {
		return maxTaskThreads;
	}

	public void setMaxTaskThreads(Integer maxTaskThreads) {
		this.maxTaskThreads = maxTaskThreads;
	}

	public String getName() {
		return name;
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * Hashcode without considering maxTaskThreads
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serverDetails == null) ? 0 : serverDetails.hashCode());
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
		Profile other = (Profile) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverDetails == null) {
			if (other.serverDetails != null)
				return false;
		} else if (!serverDetails.equals(other.serverDetails))
			return false;
		return true;
	}
	
}
