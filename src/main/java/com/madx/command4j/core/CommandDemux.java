package com.madx.command4j.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.naming.SizeLimitExceededException;

import org.apache.commons.lang3.SerializationUtils;

import com.google.common.collect.Sets;
import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.Option.CommandOptionDemux;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * This class is designed for the demultiplexing of profiles if they contains a regex
 * @author Daniele Maddaluno
 * 
 */
public class CommandDemux {

	public static List<Command> commandsDemux(Profile profile, Command command) throws SizeLimitExceededException{
		boolean isRegex = command.containsRegex();
		if (isRegex) {
			List<Command> demuxCommands = new ArrayList<Command>();
			List<CommandOptionDemux> regexOptions = command.containedRegex();
			List<Set<String>> regexList = new ArrayList<Set<String>>();

			for(CommandOptionDemux regexOption : regexOptions){
				Command ls = CommandBuilder.command(Ls.class).options(Arrays.asList(Ls.absolutePath(regexOption.getOptionCommand()))).build();
				Set<String> matchingFiles = new TreeSet<String>();
				String response = Command4j.execute(profile, ls).toString();
				if (!response.trim().isEmpty()) {
					matchingFiles.addAll(aListOf(response).stream().parallel().map(it -> convert(it)).collect(Collectors.toList()));
				}
				matchingFiles.remove(StringSymbol.EMPTY.toString());
				regexList.add(matchingFiles);
			}
			Set<List<String>> cartesiansRegex = Sets.cartesianProduct(regexList);
			for(List<String> commandRegexToReplace : cartesiansRegex){
				List<Option<Command>> optionsToReplace = commandRegexToReplace.
						stream().
						map(s -> Command.path(s)).
						collect(Collectors.toList());
				demuxCommands.add(SerializationUtils.clone(command).replaceRegex(optionsToReplace));
				if(optionsToReplace.size()!=0) throw new SizeLimitExceededException("The optionsToReplace should be empty at this point");
			}

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
