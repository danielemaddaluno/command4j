# What is Command4j? #

Command4j is a simple API made to centralize and facilitate the command execution on remote or local machines, **in Unix environments**.

<!--
# Maven #
```
<dependency>
    <groupId>com.madx</groupId>
    <artifactId>command4j</artifactId>
    <version>1.0.0</version>
</dependency>
```
-->

# Usage #

## Profile ##
In command4j a profile is a command target context.
The profile contains information such as the name of the file/folder, the host and the credentials to connect to either local or remote machine.
``` java
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
```


## Using Command4j to execute some commands ##

You can use Command4j to execute some commands across multiple local/remote servers in an easy and fluent way:
``` java
import java.util.Arrays;

import org.junit.Test;

import com.madx.command4j.commands.ls.Ls;
import com.madx.command4j.core.Command;
import com.madx.command4j.core.CommandBuilder;
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

		Command command1 = CommandBuilder
				.command(Ls.class)
				.options(Arrays.asList(Ls.path("/")))
				.build();

		Command command2 = CommandBuilder
				.command(Ls.class)
				.options(Arrays.asList(Ls.path("/etc")))
				.build();

		CommandsResponses crs = Command4j.execute(profile, Arrays.asList(command1, command2));
		crs.stream().forEach(System.out::println);
	}
}
```

If you need to execute a command upon some files identified by a regex you can use the `Command.path` option and set true the regex boolean as shown below.
It will demultiplex automatically the command for you (to test it simply try to run the test class inside the library!).

``` java
Profile profile = ProfileBuilder.newBuilder()
		.name("Local server log")
		.onLocalhost()
		.build();

File f = new File("src/test/resources/regex");

Command command = CommandBuilder
		.command(Grep.class)
		.options(Arrays.asList(Grep.pattern("ciao"), Grep.path(f.getAbsolutePath() + "/t*e*", true)))
		.build();

CommandsResponses crs = Command4j.execute(profile, command);
crs.stream().forEach(System.out::println);
```