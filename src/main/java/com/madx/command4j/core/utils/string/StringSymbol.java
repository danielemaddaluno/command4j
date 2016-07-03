package com.madx.command4j.core.utils.string;

import org.apache.commons.lang3.StringUtils;

public enum StringSymbol {
	EMPTY(StringUtils.EMPTY),
	SPACE(StringUtils.SPACE),
	QUOTE("\'"),
	PIPE("|");
	
	private String symbol;
	
	private StringSymbol(String symbol){
		this.symbol = symbol;
	}

	@Override
	public String toString(){
		return symbol;
	}
}
