package de.hswhameln.typetogether.client.runtime.commands.base;

public interface Command {
    /**
	 * Execute this command.
	 */
	public void execute();

	/**
	 * Revert this command.
	 * <p>
	 * Prerequisites:
	 * <ul>
	 * <li>The {@link #execute()} method has been called
	 * <li>It is assumed that the system state is the same as if the execute command
	 * had just been called.
	 * 
	 * 
	 */
	public void revert();

	public void redo();


}
