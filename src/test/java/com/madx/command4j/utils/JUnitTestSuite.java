package com.madx.command4j.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.madx.command4j.core.CommandBuilderTest;
import com.madx.command4j.core.OptionsBuilderTest;
import com.madx.command4j.core.ReadmeTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CommandBuilderTest.class,
	OptionsBuilderTest.class,
	ReadmeTest.class
})
public class JUnitTestSuite {}
