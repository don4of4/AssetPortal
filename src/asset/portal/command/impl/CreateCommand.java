package asset.portal.command.impl;

import org.bukkit.entity.Player;

import asset.portal.command.Command;
import asset.portal.command.CommandPermissionException;
import asset.portal.command.CommandSyntaxException;
import asset.portal.gate.GateRegistry;
import asset.portal.util.MessageConstants;
import asset.portal.util.PermissionConstants;

public class CreateCommand implements Command {

	private GateRegistry gateRegistry;
	
	public CreateCommand(GateRegistry gateRegistry) {
		this.gateRegistry = gateRegistry;
	}
	
	public void execute(Player player, String[] args) throws CommandPermissionException, CommandSyntaxException {
		if(!player.hasPermission(PermissionConstants.PORTAL_CREATE)) {
			throw new CommandPermissionException(PermissionConstants.PORTAL_CREATE);
		}
		if(args.length < 1) {
			throw new CommandSyntaxException("destinationServer");
		}
		String destinationServer = args[0];
		if(this.gateRegistry.hasByDestinationServer(destinationServer)) {
			player.sendMessage(MessageConstants.format(MessageConstants.CREATE_ALREADY_EXISTS, destinationServer));
			return;
		}
		//TOOD add create context
	}

	public String getId() {
		return "create";
	}

}
