package fr.div.nukamcplugin;

import java.util.HashMap;

public class Badge {
	private final HashMap<String, String> badges;
	
	public Badge() {
		this.badges = new HashMap<String, String>(){{
		put("Wastelander", "§k \u0030");
        put("Member", "%memberbadge%");
        put("Media", "%mediabadge%");
        put("Helper", "%helperbadge%");
        put("Moderator", "%moderatorbadge%");
        put("Developer", "%devbadge%");
    }};
	}
    
    public String getBadge(String role) {
    	return badges.get(role);
    }
    
    public boolean addbadge(String role, String badge) {
    	try {
    		badges.put(role, badge);
    		return true;
    	} catch (Exception e) {
			return false;
		}
    }
}