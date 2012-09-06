package asset.portal.bukkit;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import asset.connect.api.Connect;
import asset.connect.api.request.impl.DirectRequest;
import asset.connect.api.request.impl.RedirectRequest;
import asset.connect.api.result.FutureResultListener;
import asset.connect.api.result.StatusCode;
import asset.connect.api.result.impl.DirectResult;
import asset.portal.IConnector;
import asset.portal.IRedirector;
import asset.portal.gate.Gate;
import asset.portal.gate.GateListener;
import asset.portal.gate.GateRegistry;
import asset.portal.store.Store;
import asset.portal.store.impl.FileStore;
import asset.portal.user.UserListener;
import asset.portal.user.UserRedirectorTask;
import asset.portal.user.UserRegistry;
import asset.portal.util.MessageConstants;

public class PortalPlugin extends JavaPlugin implements IRedirector, IConnector {

	private GateRegistry gateRegistry;
	private GateListener gateListener;
	private UserRegistry userRegistry;
	private UserListener userListener;
	private Store store;

	@Override
	public void onLoad() {
		super.getConfig().options().copyDefaults(true);
		super.saveConfig();
		super.reloadConfig();
	}

	@Override
	public void onEnable() {
		try {
			this.gateRegistry = new GateRegistry();
			this.gateListener = new GateListener(this.gateRegistry, this);
			this.userRegistry = new UserRegistry();
			this.userListener = new UserListener(this.gateRegistry, this.userRegistry, this, this);
			this.store = new FileStore(new File(this.getDataFolder(), "store_user.dat"), new File(this.getDataFolder(), "store_gate.dat"));
			this.store.setUserRegistry(this.userRegistry);
			this.store.loadUsers();
			this.store.setGateRegistry(this.gateRegistry);
			this.store.loadGates();
			this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.store, 100L, 100L);
			this.getServer().getPluginManager().registerEvents(this.gateListener, this);
			this.getServer().getPluginManager().registerEvents(this.userListener, this);
			this.getConnect().registerDirectEventListener(this.userListener);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		try {
			if(this.store != null) {
				this.store.saveAll();
			}
			if(this.gateRegistry != null) {
				this.gateRegistry.clear();
			}
			if(this.userRegistry != null) {
				this.userRegistry.clear();
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			this.gateRegistry = null;
			this.gateListener = null;
			this.userRegistry = null;
			this.userListener = null;
			this.store = null;
		}
	}

	public void requestRedirect(final Player player, Gate gate) {
		try {
			this.getConnect().request(new DirectRequest(gate.getDestinationServer(), "PORTAL REQUEST " + player.getName())).registerListener(new FutureResultListener<DirectResult>() {
				public void onResult(DirectResult directResult) {
					if(directResult.getStatusCode() == StatusCode.SUCCESS) {
						return;
					}
					player.sendMessage(MessageConstants.format(MessageConstants.SERVER_OFFLINE));
				}
			});
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void respondRedirect(Player player, String server) {
		try {
			this.getConnect().request(new DirectRequest(server, "PORTAL RESPONSE " + player.getName()));
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public void redirect(String username, String server) {
		try {
			this.getConnect().request(new RedirectRequest(username, server));
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public boolean redirectResult(String username, String server) {
		try {
			return this.getConnect().request(new RedirectRequest(username, server)).await().getStatusCode() == StatusCode.SUCCESS;
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public void redirectLastServer(String username, String server) {
		if(!this.getConfig().getBoolean("redirect", false)) {
			return;
		}
		UserRedirectorTask userRedirector = new UserRedirectorTask(username, server, this);
		userRedirector.setTaskId(this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, userRedirector, 100L, 100L));
	}
	
	public Connect getConnect() {
		return this.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	}

}
