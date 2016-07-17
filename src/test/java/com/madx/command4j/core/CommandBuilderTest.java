package com.madx.command4j.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.grep.Grep;
import com.madx.command4j.commands.ls.Ls;

public class CommandBuilderTest {

//	private static Profile profile(){
//		return ProfileBuilder.newBuilder()
//				.name("Local server log")
//				.filePath("/Users/madx/Desktop/asd/sym")
//				.onLocalhost()
//				.build();
//	}
	
	private static Option<Command> opt(){
		return Command.path("/Users/madx/Desktop/asd/sym");
	}

	@Test
	public void compile_simple() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), opt())).
				pipe(Ls.class).
				options().
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/sym | ls ", c.toString());
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
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), Command.keyValue("-b", "40"), opt())).
				pipe(Ls.class).
				options().
				build();
		assertEquals("grep -l 'tutto' -b 40 /Users/madx/Desktop/asd/sym | ls ", c.toString());
	}

	@Test
	public void compile_pipe_simple() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), opt())).
				pipe(Ls.class).
				options(Arrays.asList(Command.keyValue("-e", "/etc/"))).
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/sym | ls -e /etc/ ", c.toString());
	}

	@Test
	public void compile_pipe_generic() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), opt())).
				pipe("gen").
				options(Arrays.asList(Command.keyValue("-e", "/etc/"))).
				build();
		assertEquals("grep -l 'tutto' /Users/madx/Desktop/asd/sym | gen -e /etc/ ", c.toString());
	}
	
}
