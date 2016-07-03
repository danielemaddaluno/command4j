package com.madx.command4j.core.launchers;

/**
 * @author Daniele Maddaluno
 *
 * @param <R>
 * @param <A>
 * @param <B>
 */
public interface Launcher<R, A, B> {
	
	public R launch(A a, B b);

}
