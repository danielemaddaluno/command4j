package com.madx.command4j.core;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.grep.Grep;
import com.madx.command4j.commands.ls.Ls;

public class OptionsBuilderTest {
	@SuppressWarnings("deprecation")
	private static OptionsBuilder<? extends Command> opts(){
		return OptionsBuilder.
				with(Grep.filesMatching()).
				and(Grep.pattern("tutto")).
				and(Command.text("tex"));
	}
	
	@Test
	public void only_generic() throws Exception {
		System.out.println(opts().onlyGenericOptions());
		System.out.println(opts().onlySpecificOptions());
	}
	
	private static Option<Command> opt(){
		return Command.path("/Users/madx/Desktop/asd/s*", true);
	}
	
	@Test
	public void isRegexTrue() throws Exception {
		Command c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), opt())).
				pipe(Ls.class).
				options().
				build();
		assertTrue(c.containsRegex());
		
		c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"))).
				pipe(Ls.class).
				options().
				build();
		
		assertFalse(c.containsRegex());
		
		c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"), opt())).
				pipe(Ls.class).
				options(Arrays.asList(Ls.absolutePath("abs path"), Ls.text("wer"))).
				build();
		assertTrue(c.containsRegex());
		
		c = CommandBuilder.
				command(Grep.class).
				options(Arrays.asList(Grep.filesMatching(), Grep.pattern("tutto"))).
				pipe(Ls.class).
				options(Arrays.asList(Ls.absolutePath("abs path"), Ls.text("wer"))).
				build();
		assertFalse(c.containsRegex());
		
		
	}
}
