package com.madx.command4j.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.grep.Grep;
import com.madx.command4j.commands.grep.GrepOption;
import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.CommandBuilder;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.model.ProfileBuilder;

public class CommandBuilderTest {

	private static Profile profile(){
		return ProfileBuilder.newBuilder()
				.name("Local server log")
				.filePath("/Users/madx/Desktop/asd/s*")
				.onLocalhost()
				.build();
	}

	@Test
	public void compile_simple() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(GrepOption.filesMatching(), GrepOption.pattern("tutto"), GrepOption.path(profile().getFilePath()))).
				pipe(Ls.class).
				options().
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/s* | ls ", c.toString());
	}

	@Test
	public void compile_general() throws Exception {
		Command c = CommandBuilder.
				command("gen").
				options(Arrays.asList(Command.text("asd"), Command.keyValue("-b", "40"))).
				pipe(Ls.class).
				options().
				build();
		assertEquals("gen asd -b 40 | ls ", c.toString());
	}

	@Test
	public void compile_mixed() throws Exception {
		Command c =CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(GrepOption.filesMatching(), GrepOption.pattern("tutto"), Command.keyValue("-b", "40"), GrepOption.path(profile().getFilePath()))).
				pipe(Ls.class).
				options().
				build();
		assertEquals("grep -l 'tutto' -b 40 /Users/madx/Desktop/asd/s* | ls ", c.toString());
	}

	@Test
	public void compile_pipe_simple() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(GrepOption.filesMatching(), GrepOption.pattern("tutto"), GrepOption.path(profile().getFilePath()))).
				pipe(Ls.class).
				options(Arrays.asList(Command.keyValue("-e", "/etc/"))).
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/s* | ls -e /etc/ ", c.toString());
	}

	@Test
	public void compile_pipe_generic() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(GrepOption.filesMatching(), GrepOption.pattern("tutto"), GrepOption.path(profile().getFilePath()))).
				pipe("gen").
				options(Arrays.asList(Command.keyValue("-e", "/etc/"))).
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/s* | gen -e /etc/ ", c.toString());
	}
	
}
