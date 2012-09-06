package asset.portal.user;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import asset.connect.api.Connect;
import asset.connect.api.DirectEvent;
import asset.connect.api.DirectEventListener;
import asset.portal.IConnector;
import asset.portal.IRedirector;
import asset.portal.gate.Gate;
import asset.portal.gate.GateRegistry;

public class UserListener implements Listener, DirectEventListener {

	private GateRegistry gateRegistry;
	private UserRegistry userRegistry;
	private IConnector connector;
	private IRedirector redirector;
	
	public UserListener(GateRegistry gateRegistry, UserRegistry userRegistry, IConnector connector, IRedirector redirector) {
		this.gateRegistry = gateRegistry;
		this.userRegistry = userRegistry;
		this.connector = connector;
		this.redirector = redirector;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
		User user = this.getUser(playerJoinEvent.getPlayer().getName());
		String fromServer = user.getFromServer();
		if(fromServer == null) {
			if(user.getServer().equals(this.connector.getConnect().getConnectSettings().getUsername())) {
				return;
			}
			this.redirector.redirectLastServer(user.getName(), user.getServer());
			return;
		}
		user.setServer(this.connector.getConnect().getConnectSettings().getUsername());
		Gate gate = this.gateRegistry.getByDestinationServer(fromServer);
		if(gate == null) {
			return;
		}
		playerJoinEvent.getPlayer().teleport(new Location(Bukkit.getServer().getWorld(gate.getOutwardWorld()), gate.getOutwardX(), gate.getOutwardY(), gate.getOutwardZ(), gate.getOutwardYaw(), 0));
	}

	public void onDirect(Connect connect, DirectEvent directEvent) {
		String[] message = directEvent.getMessage().split(" ");
		if(message.length != 3) {
			return;
		}
		if(message[0].equals("PORTAL")) {
			return;
		}
		if(message[1].equals("REQUEST")) {
			this.redirector.redirect(message[2], this.connector.getConnect().getConnectSettings().getUsername());
			this.getUser(message[2]).setFromServer(directEvent.getUsername());
		} else if(message[1].equals("RESPONSE")) {
			this.getUser(message[2]).setServer(directEvent.getUsername());
		}
	}
	
	public User getUser(String name) {
		if(!this.userRegistry.hasName(name)) {
			this.userRegistry.register(new User(name, this.connector.getConnect().getConnectSettings().getUsername()));
		}
		return this.userRegistry.getByName(name);
	}
	
}
