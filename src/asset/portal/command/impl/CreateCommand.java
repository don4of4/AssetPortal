package asset.portal.command.impl;

import org.bukkit.entity.Player;

import asset.portal.command.Command;
import asset.portal.command.CommandPermissionException;
import asset.portal.command.CommandSyntaxException;
import asset.portal.util.PermissionConstants;

public class CreateCommand implements Command {

	public void execute(Player player, String[] args) throws CommandPermissionException, CommandSyntaxException {
		if(!player.hasPermission(PermissionConstants.PORTAL_CREATE)) {
			throw new CommandPermissionException(PermissionConstants.PORTAL_CREATE);
		}
		//TOOD add create context
	}

	public String getId() {
		return "create";
	}

}
