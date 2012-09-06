package asset.portal.store;

import asset.portal.gate.GateRegistry;
import asset.portal.gate.GateStore;
import asset.portal.user.UserRegistry;
import asset.portal.user.UserStore;

public abstract class Store implements GateStore, UserStore, Runnable {

	private GateRegistry gateRegistry;
	private UserRegistry userRegistry;

	public void saveAll() {
		this.saveGates();
		this.saveUsers();
	}

	public GateRegistry getGateRegistry() {
		return this.gateRegistry;
	}

	public void setGateRegistry(GateRegistry gateRegistry) {
		this.gateRegistry = gateRegistry;
	}

	public UserRegistry getUserRegistry() {
		return this.userRegistry;
	}

	public void setUserRegistry(UserRegistry userRegistry) {
		this.userRegistry = userRegistry;
	}

	public void run() {
		this.saveAll();
	}

}
