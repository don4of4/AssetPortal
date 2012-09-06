package asset.portal.util;

import org.bukkit.ChatColor;

public class MessageConstants {

	public static final String PREFIX = "&f[&7Portal&f] ";
	
	public static final String SERVER_OFFLINE = PREFIX + "This server is currently &4offline&f. Try again later!";
	public static final String[] SERVER_OFFLINE_REDIRECT = new String[] {
		PREFIX + "Your last server is currently &4offline&f. If you join",
		PREFIX + "another server, you will lose your last position.",
		PREFIX + "&2You will be redirected when it becomes available."
	};
	
	public static final String[] format(String[] messages, Object... args) {
		return ChatColor.translateAlternateColorCodes('&', StringUtils.concat(messages, "\n")).split("\n");
	}
	
	public static final String format(String message, Object... args) {
		return ChatColor.translateAlternateColorCodes('&', String.format(message, args));
	}
	
}
