package com.madx.command4j.commands.sed;
//package org.grep4j.core.commands.producers.sed;
//
//import org.grep4j.core.commands.producers.Command;
//
///**
// * http://askubuntu.com/questions/20414/find-and-replace-text-within-a-file-using-commands
// * http://stackoverflow.com/a/33794818/3138238
// * http://stackoverflow.com/a/11895206/3138238
// * 
// * grep -rli 'old-word' * | xargs sed -i '' 's/old-word/new-word/g'
// * grep -rli 'old-word2' * | xargs -I@ sed -i '' 's/old-word/new-word/g' @
// * 
// * perl -pe 's/old-word/new-word/ig'
// * 
// * To ignore the case of sed regex
// * sed 's/old-word/new-word/Ig' file
// * 
// * 
// * find . -type f -name '*.txt' -exec sed -i '' s/old-word/new-word/ {} +
// * 
// * That will work on OSX as well with a slight modification. You have to add as parameter the extension of a backup file. For example: sed -i '.bak' 's/original/new/g' file.txt 
// * 
// * @author Daniele Maddaluno
// *
// */
//public class SedCommand extends Command {
//
//	@Override
//	public String getCommandToExecute() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
