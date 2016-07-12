package com.madx.command4j.commands.pipe;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * http://www.tutorialspoint.com/unix/unix-pipes-filters.htm
 * @author Daniele Maddaluno
 *
 */
public class Pipe extends Command {

	public Pipe(){
		super(StringSymbol.PIPE.toString());
	}

	public Pipe(Command previousCommand){
		super(StringSymbol.PIPE.toString(), previousCommand);
	}
}
