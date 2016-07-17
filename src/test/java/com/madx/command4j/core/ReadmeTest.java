package com.madx.command4j.core;

import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.gunzip.Gunzip;
import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.model.Profile;
import com.madx.command4j.core.model.ProfileBuilder;
import com.madx.command4j.core.response.CommandsResponses;

public class ReadmeTest {
	@Test
	@SuppressWarnings("unused")
	public void profiles() throws Exception {
		Profile remoteProfile = ProfileBuilder.newBuilder()
				.name("Remote server log")
				.onRemotehost("172.xx.xx.xx")
				.credentials("user", "password")
				.build();

		Profile localProfile = ProfileBuilder.newBuilder()
				.name("Local server log")
				.onLocalhost()
				.build();

		Profile remoteProfileWithPublicKey = ProfileBuilder.newBuilder()
				.name("Another remote server log")
				.onRemotehost("172.x.x.x")
				.userAuthPrivateKeyLocation("/home/user/.ssh/id_dsa")
				.withUser("user")
				.build();
	}

	@Test
	public void readme() throws Exception {
		Profile profile = ProfileBuilder.newBuilder()
				.name("Local server log")
				.onLocalhost()
				.build();

		Command command1 = CommandBuilder
				.command(Ls.class)
				.options(Arrays.asList(Ls.path("/")))
				.build();

		Command command2 = CommandBuilder
				.command(Gunzip.class)
				.options(
						Arrays.asList(
								Gunzip.consoleOut(), 
								Gunzip.path("/Users/madx/Documents/Workspace/Command4j/src/test/resources/grep/local.txt.gz")
								)
						)
				.build();

		System.out.println(command2.toString());

		CommandsResponses crs = Command4j.execute(profile, Arrays.asList(command1, command2));
		crs.stream().forEach(System.out::println);
		
		System.out.println("test");
	}
}
