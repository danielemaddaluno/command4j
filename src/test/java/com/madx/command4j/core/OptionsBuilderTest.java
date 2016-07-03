package com.madx.command4j.core;

import org.junit.Test;

import com.madx.command4j.commands.grep.GrepOption;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.OptionsBuilder;

public class OptionsBuilderTest {
	@SuppressWarnings("deprecation")
	private static OptionsBuilder<? extends Command> opts(){
		return OptionsBuilder.
				with(GrepOption.filesMatching()).
				and(GrepOption.pattern("tutto")).
				and(Command.text("tex"));
	}
	
	@Test
	public void only_generic() throws Exception {
		System.out.println(opts().onlyGenericOptions());
		System.out.println(opts().onlySpecificOptions());
	}
}
