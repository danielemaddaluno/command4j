package com.madx.command4j.commands.gunzip;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.Option;

/**
 * {@link Gunzip} is a {@link Command} object that build the command to list files. 
 * @author Daniele Maddaluno
 * 
 * https://www.gnu.org/software/gzip/manual/gzip.html
 * gunzip [-123456789acdfhklLNnqrtVv] [-S .suffix] [<file> [<file> ...]]
 * -1 --fast            fastest (worst) compression
 * -2 .. -8             set compression level
 * -9 --best            best (slowest) compression
 * -c --stdout          write to stdout, keep original files
 *    --to-stdout
 * -d --decompress      uncompress files
 *    --uncompress
 * -f --force           force overwriting & compress links
 * -h --help            display this help
 * -k --keep            don't delete input files during operation
 * -l --list            list compressed file contents
 * -N --name            save or restore original file name and time stamp
 * -n --no-name         don't save original file name or time stamp
 * -q --quiet           output no warnings
 * -r --recursive       recursively compress files in directories
 * -S .suf              use suffix .suf instead of .gz
 *    --suffix .suf
 * -t --test            test compressed file
 * -V --version         display program version
 * -v --verbose         print extra statistics
 * @author Daniele Maddaluno
 *
 */
public class Gunzip extends Command {
	public static class GunzipOption extends Option<Gunzip> {
		protected GunzipOption(String optionCommand) {
			super(optionCommand, null, 0);
		}

		protected GunzipOption(String optionCommand, Object optionValue) {
			super(optionCommand, optionValue, 0);
		}
	}

	/**
	 * -c, --stdout, --to-stdout write to stdout, keep original files
	 * @return
	 */
	public static Option<Gunzip> onlyMatching() {
		return new GunzipOption("-c");
	}
}
