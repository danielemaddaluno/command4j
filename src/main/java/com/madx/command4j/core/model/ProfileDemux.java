package com.madx.command4j.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.madx.command4j.core.Command;

/**
 * This class is designed for the demultiplexing of profiles if they contains a regex
 * @author Daniele Maddaluno
 * 
 * TODO add this to the execution process to enable the use of regex in command execution...
 */
@SuppressWarnings("unused")
public class ProfileDemux {

	/**
	 * 
	 * @param profiles
	 * @return the profiles demultiplexed
	 */
	public Pair<List<Profile>, List<Command>> profilesDemux(List<Profile> profiles, List<Command> commands){
		List<Profile> demuxProfiles = new ArrayList<Profile>();
		for(Profile profile : profiles){
			for(Command command : commands){
				List<String> paths = listPaths(profile, command);
				for(String path : paths){
					System.out.println(path);
					demuxProfiles.add(Profile.copy(profile));
				}
			}
		}
		return new ImmutablePair<List<Profile>, List<Command>>(profiles, commands);
	}

	private List<String> listPaths(Profile profile, Command command) {
		List<String> matchingFiles = new ArrayList<String>();
		
		// TODO extract from the command the regex expression and add it to a LS command
//		boolean isRegex = command.containsRegex();
//		if (isRegex) {
//			Command ls = CommandBuilder.command(Ls.class).options(Arrays.asList(LsOption.absolutePath(profile.getFilePath()))).build();
//			String response = Command4j.command(profile, ls).toString();
//			if (!response.trim().isEmpty()) {
//				matchingFiles.addAll(aListOf(response).stream().parallel().map(it -> convert(it)).collect(Collectors.toList()));
//			}
//		} else {
//			matchingFiles.add(profile.getFilePath());
//		}

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
