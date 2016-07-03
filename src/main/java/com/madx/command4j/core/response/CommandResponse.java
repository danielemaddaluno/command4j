package com.madx.command4j.core.response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import com.madx.command4j.core.Command;
import com.madx.command4j.core.model.Profile;

/**
 * This class contains the result of the grep in String format.
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 * @author Daniele Maddaluno
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class CommandResponse {

	private static final Pattern linePattern = Pattern.compile(".*\\r?\\n");
	@Getter
	private final Profile profile;
	@Getter
	private final Command command;
	@Getter
	private final String text;
	@Getter
	private final long executionTime;

	/**
	 * @return the profile name associated with this grep result
	 */
	public String getProfileName() {
		return profile.getName();
	}

	/**
	 * it counts how many lines are in the grep result
	 * 
	 * @return total number of time the new line patter is found
	 */
	public int totalLines() {
		int totalLines = 0;
		Matcher lm = linePattern.matcher(this.getText());
		while (lm.find()) {
			totalLines++;
		}
		return totalLines;
	}

	/**
	 * extract the lines that match with the passed filter as a constant or regular Expression
	 * 
	 * @param grepExpression
	 * @return the lines that match with the passed filter as a constant or regular Expression
	 */
	public CommandResponse filterBy(LineFilter grepExpression) {
		if (grepExpression.isRegularExpression()) {
			return filterByRE(grepExpression.getText());
		} else {
			return filterBy(grepExpression.getText());
		}
	}

	private CommandResponse filterByRE(String expression) {
		StringBuilder textResult = new StringBuilder();

		Pattern pattern = Pattern.compile(expression);
		Matcher lm = linePattern.matcher(this.getText());
		Matcher pm = null;
		while (lm.find()) {
			CharSequence cs = lm.group();
			if (pm == null) {
				pm = pattern.matcher(cs);
			} else {
				pm.reset(cs);
			}
			if (pm.find()) {
				textResult.append(cs);
			}
		}

		return new CommandResponse(profile, command, textResult.toString(), executionTime);
	}

	private CommandResponse filterBy(String expression) {
		StringBuilder textResult = new StringBuilder();
		Matcher lm = linePattern.matcher(this.getText());
		while (lm.find()) {
			CharSequence cs = lm.group();
			if (StringUtils.contains(cs, expression)) {
				textResult.append(cs);
			}
		}

		return new CommandResponse(profile, command, textResult.toString(), executionTime);
	}

	/**
	 * @return all the header information in this format : Profile name >>>%s<<< [ File Name:%s; Total lines found:%s; Total execution time:%s;
	 *         Expression:%s ]
	 */
	public String getHeaderInformation() {
		return String.format("Profile name >>>%s<<< [ Total lines found:%s; Total execution time:%s; Expression:%s ]",
				profile.getName(), totalLines(), getExecutionTime(), command.toString());
	}

	@Override
	public String toString() {
		return text;
	}

}
