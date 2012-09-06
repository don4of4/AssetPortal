package asset.portal.gate;

import org.bukkit.Location;

public class Gate {

	private String destinationServer;
	private int inwardMinX;
	private int inwardMaxX;
	private int inwardMinY;
	private int inwardMaxY;
	private int inwardMinZ;
	private int inwardMaxZ;
	private String inwardWorld;
	private int outwardX;
	private int outwardY;
	private int outwardZ;
	private int outwardYaw;
	private String outwardWorld;

	public boolean isInside(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		String world = location.getWorld().getName();
		if(x < this.inwardMinX && x > this.inwardMaxX) {
			return false;
		}
		if(y < this.inwardMinY && y > inwardMaxY) {
			return false;
		}
		if(z < this.inwardMinZ && z > this.inwardMaxZ) {
			return false;
		}
		if(!this.inwardWorld.equals(world)) {
			return false;
		}
		return true;
	}
	
	public String getDestinationServer() {
		return this.destinationServer;
	}
	
	public int getOutwardX() {
		return outwardX;
	}

	public int getOutwardY() {
		return outwardY;
	}

	public int getOutwardZ() {
		return outwardZ;
	}

	public int getOutwardYaw() {
		return outwardYaw;
	}

	public String getOutwardWorld() {
		return outwardWorld;
	}
	
	public static Gate fromString(String string) {
		return null;
	}
	
	public static String toString(Gate gate) {
		return null;
	}
	
	@Override
	public String toString() {
		return toString(this);
	}
	
}
