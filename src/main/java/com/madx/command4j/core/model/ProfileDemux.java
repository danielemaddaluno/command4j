package com.madx.command4j.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.commands.ls.LsOption;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.Command4j;
import com.madx.command4j.core.CommandBuilder;

/**
 * This class is designed for the demultiplexing of profiles if they contains a regex
 * @author Daniele Maddaluno
 */
public class ProfileDemux {
	
	/**
	 * 
	 * @param profiles
	 * @return the profiles demultiplexed
	 */
	public List<Profile> profilesDemux(List<Profile> profiles){
		List<Profile> demuxProfiles = new ArrayList<Profile>();
		for(Profile profile : profiles){
			List<String> newProfiles = listPaths(profile);
			for(String newFilePath : newProfiles){
				demuxProfiles.add(Profile.copy(profile, newFilePath));
			}
		}
		return demuxProfiles;
	}
	
	private List<String> listPaths(Profile profile) {
		List<String> matchingFiles = new ArrayList<String>();

		if (profile.containsWildcard()) {
			Command ls = CommandBuilder.command(Ls.class).options(Arrays.asList(LsOption.absolutePath(profile.getFilePath()))).build();
			String response = Command4j.command(profile, ls).toString();
			if (!response.trim().isEmpty()) {
				matchingFiles.addAll(aListOf(response).stream().parallel().map(it -> convert(it)).collect(Collectors.toList()));
			}
		} else {
			matchingFiles.add(profile.getFilePath());
		}

		return matchingFiles;
	}

	private List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}
	
	private String convert(String fileName) {
		String fileNameWithNoSpaces = "";
		if (fileName.contains(" ")) {
			fileNameWithNoSpaces = "\"" + fileName + "\"";
		} else {
			fileNameWithNoSpaces = fileName;
		}
		return fileNameWithNoSpaces;
	}
}
