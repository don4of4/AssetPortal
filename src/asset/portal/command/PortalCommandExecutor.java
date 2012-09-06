package asset.portal.command;

import asset.portal.command.impl.CreateCommand;
import asset.portal.command.impl.DeleteCommand;
import asset.portal.command.impl.ListCommand;

public class PortalCommandExecutor extends CommandRegistryExecutor {

	public PortalCommandExecutor() {
		this.submit(new CreateCommand());
		this.submit(new DeleteCommand());
		this.submit(new ListCommand());
	}
	
	public String getId() {
		return "portal";
	}

}
