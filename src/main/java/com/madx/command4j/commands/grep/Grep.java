package com.madx.command4j.commands.grep;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.Option;
import com.madx.command4j.core.utils.string.StringSymbol;

/**
 * @author Daniele Maddaluno
 */
public class Grep extends Command {
	private static final long serialVersionUID = 1L;
	
	public static class GrepOption extends Option<Grep> {
		private static final long serialVersionUID = 1L;
		
		protected GrepOption(String optionCommand) {
			super(optionCommand, null, 0);
		}

		protected GrepOption(String optionCommand, Object optionValue) {
			super(optionCommand, optionValue, 0);
		}
	}

	public static Option<Grep> pattern(String pattern) {
		return new GrepOption(StringSymbol.QUOTE + pattern + StringSymbol.QUOTE);
	}

	/**
	 * -A, --after-context=NUM print NUM lines of trailing context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option<Grep> extraLinesAfter(Integer lines) {
		return new GrepOption("-A", lines);
	}

	/**
	 * -B, --before-context=NUM print NUM lines of leading context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option<Grep> extraLinesBefore(Integer lines) {
		return new GrepOption("-B", lines);
	}

	/**
	 * -C, --context=NUM print NUM lines of output context
	 * 
	 * @param optionComand
	 * @return
	 */
	public static Option<Grep> extraLinesBeforeAndAfter(Integer lines) {
		return new GrepOption("-C", lines);
	}

	/**
	 * -o, --only-matching show only the part of a line matching PATTERN
	 * 
	 * @return
	 */
	public static Option<Grep> onlyMatching() {
		return new GrepOption("-o");
	}

	/**
	 * -i, --ignore-case ignore case distinctions
	 * 
	 * @return
	 */
	public static Option<Grep> ignoreCase() {
		return new GrepOption("-i");
	}

	/**
	 * -v, --invert-match select non-matching lines
	 * 
	 * @return
	 */
	public static Option<Grep> invertMatch() {
		return new GrepOption("-v");
	}

	/**
	 * -c, --count print only a count of matching lines per FILE
	 * 
	 * @return
	 */
	public static Option<Grep> countMatches() {
		return new GrepOption("-c");
	}

	/**
	 * -H, --with-filename print the filename for each match
	 * 
	 * @return
	 */
	public static Option<Grep> withFileName() {
		return new GrepOption("-H");
	}

	/**
	 * -m, --max-count=NUM stop after NUM matches
	 * 
	 * @return
	 */
	public static Option<Grep> maxMatches(Integer maxMatches) {
		return new GrepOption("-m", maxMatches);
	}

	/**
	 * -n, --line-number print line number with output lines
	 * 
	 * @return
	 */
	public static Option<Grep> lineNumber() {
		return new GrepOption("-n");
	}

	/**
	 * -L, --files-without-match print only names of FILEs containing no match
	 * 
	 * @return
	 */
	public static Option<Grep> filesNotMatching() {
		return new GrepOption("-L");
	}

	/**
	 * -l, --files-with-matches print only names of FILEs containing matches
	 * 
	 * @return
	 */
	public static Option<Grep> filesMatching() {
		return new GrepOption("-l");
	}

	/**
	 * --version, the version of grep
	 * 
	 * @return
	 */
	public static Option<Grep> grepVersion() {
		return new GrepOption("--version");
	}

	/**
	 * -R, -r, --recursive       equivalent to --directories=recurse
	 * 
	 * @return
	 */
	public static Option<Grep> recursive() {
		return new GrepOption("-r");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --include=FILE_PATTERN  search only files that match FILE_PATTERN
	 * 
	 * @return
	 */
	public static Option<Grep> onlyFilesWhenRecursing(String pattern) {
		return new GrepOption("--include='" + pattern + "'");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --exclude=FILE_PATTERN  skip files and directories matching FILE_PATTERN
	 * 
	 * @return
	 */
	public static Option<Grep> excludeFilesWhenRecursing(String pattern) {
		return new GrepOption("--exclude='" + pattern + "'");
	}

	/**
	 * <b>Recursive option only working with Option.recursive()</b>
	 * 
	 * --exclude-dir=PATTERN  directories that match PATTERN will be skipped.
	 * 
	 * @return
	 */
	public static Option<Grep> excludeDirectoriesWhenRecursing(String pattern) {
		return new GrepOption("--exclude-dir='" + pattern + "'");
	}
}
