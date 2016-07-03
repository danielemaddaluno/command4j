package com.madx.command4j.core;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import com.madx.command4j.commands.pipe.Pipe;

/**
 * Builder class for {@link Command}
 * 
 * @author Daniele Maddaluno
 */
public class CommandBuilder {
	
	private CommandBuilder() {}

	/**
	 * Step 1.a
	 * Simple command from a previously defined Command class
	 * @param command
	 * @return the options step
	 */
	public static <T extends Command> OptionsStep<T> command(Class<T> command) {
		return new Steps<T>(command);
	}
	
	/**
	 * Step 1.b
	 * Simple generic command from a String name
	 * @param commandName the name of the command
	 * @return the options step
	 */
	public static OptionsStep<Command> command(String commandName) {
		if(commandName == null || commandName.isEmpty()) throw new IllegalArgumentException("The String field is missing (null or empty)");
		return new Steps<Command>(commandName);
	}
	
	
	public static interface OptionsStep<T extends Command> {
		/**
		 * Step 2.empty
		 * Adds no options and goes to the End Step
		 * @param options
		 * @return
		 */
		EndStep<T> options();
		
		/**
		 * Step 2.full
		 * Adds options to the command specified in the step 1
		 * @param options
		 * @return
		 */
		EndStep<T> options(List<Option<? super T>> options);
		
		/**
		 * Step 2.bis
		 * Adds options to the command specified in the step 1
		 * @param options
		 * @return
		 */
		@Deprecated
		@SuppressWarnings("unchecked")
		EndStep<T> options(Option<? super T>... options);
		
		/**
		 * Step 2.ter
		 * Adds options to the command specified in the step 1
		 * @param options
		 * @return
		 */
		@Deprecated
		EndStep<T> options(OptionsBuilder<T> options);
	}

	public static abstract class EndStep<T extends Command> {
		
		/**
		 * This is set using the step 1.a (steps 1.a and 1.b are exclusive)
		 */
		protected Class<T> newCommandClass;
		
		/**
		 * This is set using the step 1.b (steps 1.a and 1.b are exclusive)
		 */
		protected String commandName;
		
		/**
		 * This is set using the step 2.
		 */
		protected OptionsBuilder<T> options;
		
		/**
		 * This is commonly set during the steps 3.a or 3.b
		 */
		protected Command previousCommand;
		
		/**
		 * Step 4. is the final build step which generates
		 * the command to run through the Command4j.command
		 * @return the Command to run
		 */
		public abstract Command build();

		/**
		 * Optional Step 3.a to pipe the previously created command
		 * with a simple command from a previously defined Command class
		 * @param newCommandClass
		 * @return
		 */
		public <V extends Command> OptionsStep<V> pipe(Class<V> newCommandClass){
			Command c = this.build();
			return new Steps<V>(newCommandClass, new Pipe(c));
		}
		
		/**
		 * Optional Step 3.b to pipe the previously created command
		 * with a simple generic command from a String name
		 * @param commandName
		 * @return
		 */
		public OptionsStep<Command> pipe(String commandName){
			Command c = this.build();
			return new Steps<Command>(commandName, new Pipe(c));
		}
	}

	private static class Steps<T extends Command> extends EndStep<T> implements OptionsStep<T> {
		
		/**
		 * This is used in step 1.a
		 * @param newCommandClass
		 */
		private Steps(Class<T> newCommandClass) {
			this(newCommandClass, null, null);
		}
		
		/**
		 * This is used in step 1.b
		 * @param commandName
		 */
		private Steps(String commandName) {
			this(null, commandName, null);
		}
		
		/**
		 * This is used in optional step 3.a
		 * @param newCommandClass
		 * @param previousCommand
		 */
		private Steps(Class<T> newCommandClass, Command previousCommand) {
			this(newCommandClass, null, previousCommand);
		}

		/**
		 * This is used in optional step 3.b
		 * @param commandName
		 * @param previousCommand
		 */
		private Steps(String commandName, Command previousCommand) {
			this(null, commandName, previousCommand);
		}
		
		/**
		 * Complete constructor used by all the other constructors (steps 1.a, 1.b, 3.a, 3.b)
		 * @param newCommandClass
		 * @param previousCommand
		 * @param commandName
		 */
		private Steps(Class<T> newCommandClass, String commandName, Command previousCommand) {
			this.newCommandClass = newCommandClass;
			this.commandName = commandName;
			this.previousCommand = previousCommand;
		}

		public final EndStep<T> options() {
			this.options = new OptionsBuilder<T>();
			return this;
		}
		
		@Override
		@SafeVarargs
		public final EndStep<T> options(Option<? super T>... options) {
			this.options = new OptionsBuilder<T>(Arrays.asList(options));
			return this;
		}
		
		@Override
		public final EndStep<T> options(List<Option<? super T>> options) {
			this.options = new OptionsBuilder<T>(options);
			return this;
		}
		
		@Override
		public final EndStep<T> options(OptionsBuilder<T> options) {
			this.options = options;
			return this;
		}
		
		@Override
		public Command build() {
			try {
				// 1) Command instantiation
				Command c = null;
				// case step 1.a
				if(newCommandClass != null && commandName == null){
					Constructor<T> ctor = newCommandClass.getDeclaredConstructor();
					ctor.setAccessible(true);
					c = ctor.newInstance();
				// case step 1.b
				} else if(newCommandClass == null && commandName != null) {
					c = new Command(commandName);
				// case error (none of 1.a or 1.b)
				} else {
					throw new IllegalArgumentException();
				}
				
				// 2) Set the options from step 2.
				c.setOptions(options);
				
				// 3) Set the previous command if any (from steps 3.a or 3.b)
				c.setPreviousCommand(previousCommand);
				
				// 4) return the complete Command build which concludes the step 4. (this method of building) 
				return c;
			} catch (NoSuchMethodException e) {
				new NoSuchMethodException("The " + newCommandClass!=null ? newCommandClass.getSimpleName() : commandName + " class must provide a default/no-argument constructor").printStackTrace();
			} catch (IllegalArgumentException e) {
				new IllegalArgumentException("Both newCommandClass and commandName fields are null").printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
