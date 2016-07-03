package com.madx.command4j.commands.gunzip;

import com.madx.command4j.core.Option;

public class GunzipOption extends Option<Gunzip> {

	protected GunzipOption(String optionCommand) {
		super(optionCommand, null, 0);
	}
	
	protected GunzipOption(String optionCommand, Object optionValue) {
		super(optionCommand, optionValue, 0);
	}
	
	/**
	 * -c, --stdout, --to-stdout write to stdout, keep original files
	 * @return
	 */
	public static Option<Gunzip> onlyMatching() {
		return new GunzipOption("-c");
	}
}
