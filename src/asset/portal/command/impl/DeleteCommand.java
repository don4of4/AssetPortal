package asset.portal.command.impl;

import org.bukkit.entity.Player;

import asset.portal.command.Command;
import asset.portal.command.CommandPermissionException;
import asset.portal.command.CommandSyntaxException;
import asset.portal.gate.Gate;
import asset.portal.gate.GateRegistry;
import asset.portal.util.MessageConstants;
import asset.portal.util.PermissionConstants;

public class DeleteCommand implements Command {

	private GateRegistry gateRegistry;
	
	public DeleteCommand(GateRegistry gateRegistry) {
		this.gateRegistry = gateRegistry;
	}
	
	public void execute(Player player, String[] args) throws CommandPermissionException, CommandSyntaxException {
		if(!player.hasPermission(PermissionConstants.PORTAL_DELETE)) {
			throw new CommandPermissionException(PermissionConstants.PORTAL_DELETE);
		}
		if(args.length < 1) {
			throw new CommandSyntaxException("server");
		}
		Gate gate = this.gateRegistry.getByDestinationServer(args[0]);
		if(gate == null) {
			player.sendMessage(MessageConstants.format(MessageConstants.DELETE_NO_EXIST, args[0]));
			return;
		}
		this.gateRegistry.unregister(gate);
		player.sendMessage(MessageConstants.format(MessageConstants.DELETE_SUCCESS, args[0]));
	}

	public String getId() {
		return "delete";
	}

}
