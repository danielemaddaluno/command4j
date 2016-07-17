package com.madx.command4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Sets;
import com.madx.command4j.core.Option.CommandOptionDemux;
import com.madx.command4j.core.model.Profile;

/**
 * This class is designed for the demultiplexing of profiles if they contains a regex
 * @author Daniele Maddaluno
 * 
 */
public class CommandDemux {

	public static List<Command> commandsDemux(Profile profile, Command command){
		boolean isRegex = command.containsRegex();
		if (isRegex) {
			List<Command> demuxCommands = new ArrayList<Command>();
			// TODO
			List<CommandOptionDemux> regexOptions = command.containedRegex();
			
//			Command ls = CommandBuilder.command(Ls.class).options(Arrays.asList(LsOption.absolutePath(profile.getFilePath()))).build();
//			//			String response = Command4j.command(profile, ls).toString();
//			//			if (!response.trim().isEmpty()) {
//			//				matchingFiles.addAll(aListOf(response).stream().parallel().map(it -> convert(it)).collect(Collectors.toList()));
//			//			}
			return demuxCommands;
		} else {
			return Collections.singletonList(command);
		}
	}

	private static List<String> aListOf(String filenames) {
		return Arrays.asList(filenames.split("\n"));
	}

	private static String convert(String fileName) {
		String fileNameWithNoSpaces = "";
		if (fileName.contains(" ")) {
			fileNameWithNoSpaces = "\"" + fileName + "\"";
		} else {
			fileNameWithNoSpaces = fileName;
		}
		return fileNameWithNoSpaces;
	}
}
