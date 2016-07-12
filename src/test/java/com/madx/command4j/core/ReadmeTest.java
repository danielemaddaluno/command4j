package com.madx.command4j.core;

import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.model.ProfileBuilder;
import com.madx.command4j.core.response.CommandsResponses;

public class ReadmeTest {
	@Test
	public void readme() throws Exception {
		Profile profile = ProfileBuilder.newBuilder()
				.name("Local server log")
				.onLocalhost()
				.build();

		Command command1 = CommandBuilder.
				command(Ls.class).
				options(Arrays.asList(Ls.path("/"))).
				build();

		Command command2 = CommandBuilder.
				command(Ls.class).
				options(Arrays.asList(Ls.path("/etc"))).
				build();

		CommandsResponses crs = Command4j.execute(profile, Arrays.asList(command1, command2));
		crs.stream().forEach(System.out::println);
	}
}
