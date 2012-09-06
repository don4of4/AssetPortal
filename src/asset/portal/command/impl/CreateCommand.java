package asset.portal.command.impl;

import org.bukkit.entity.Player;

import asset.portal.command.Command;
import asset.portal.command.CommandPermissionException;
import asset.portal.command.CommandSyntaxException;

public class CreateCommand implements Command {

	public void execute(Player player, String[] args) throws CommandPermissionException, CommandSyntaxException {
		//TOOD add create command
	}

	public String getId() {
		return "create";
	}

}
