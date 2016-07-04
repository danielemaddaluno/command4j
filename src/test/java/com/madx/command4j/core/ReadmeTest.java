package com.madx.command4j.core;

import org.junit.Test;

import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.model.ProfileBuilder;

public class ReadmeTest {
	@Test
	public void readme() throws Exception {
		Profile profile = ProfileBuilder.newBuilder()
				.name("Local server log")
				.filePath("/Users/madx/Desktop/asd/s*")
				.onLocalhost()
				.build();
	}
}
