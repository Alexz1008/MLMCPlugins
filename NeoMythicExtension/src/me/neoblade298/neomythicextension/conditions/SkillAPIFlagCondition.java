package me.neoblade298.neomythicextension.conditions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.sucy.skill.api.util.FlagManager;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import me.neoblade298.neomythicextension.Main;

public class SkillAPIFlagCondition extends SkillCondition implements IEntityCondition {
    private String[] flags;
    private boolean castinstead = false;
    private boolean stunchildren = false;
    private boolean action = true;
    private int setgcd = -1;
    private String msg;
    private ArrayList<Entity> near;
    private Main main;
    
    public SkillAPIFlagCondition(MythicLineConfig mlc, Main main) {
        super(mlc.getLine());
        this.flags = mlc.getString(new String[] {"flag", "f"}).trim().split(",");
        if(mlc.getString("action") != null) {
        	castinstead = mlc.getString("action").equals("castinstead");
        	action = mlc.getString("action").equals("true");
        }
        if(mlc.getString("stunchildren") != null) {
        	stunchildren = mlc.getString("stunchildren").equals("true");
        }
        if(mlc.getInteger("setgcd") != 0) {
        	setgcd = mlc.getInteger("setgcd");
        }
        msg = mlc.getString("msg");
        near = null;
        this.main = main;
    }

    public boolean check(AbstractEntity t) {
        ActiveMob am = MythicMobs.inst().getMobManager().getMythicMobInstance(t);
        boolean result = false;
        if(am != null) {
	        if(msg != null) {
	        	if(am.getEntity().getName() != null) {
	        		msg = msg.replace("<mob.name>", am.getEntity().getName());
	        	}
				msg = msg.replace("&", "�");
				msg = msg.replace("_", " ");
	        }
	        if (am != null) {
	        	LivingEntity ent = (LivingEntity) am.getEntity().getBukkitEntity();
	        	for (String flag : flags) {
	        		if (FlagManager.hasFlag(ent, flag)) {
	        			
	        			// Very specific behavior for stun rework, only use this when castinstead is enabled
	        	    	if (castinstead) {
	        	    		// Give the entity a stun tag
		    				if(!am.getEntity().hasScoreboardTag("StunTag")) {
		        	    		am.getEntity().addScoreboardTag("StunTag");
		        	    		
		        	    		// If a message was specified, show players in radius the message
		            	    	if(msg != null) {
		            	    		Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		            	    			public void run() {
				            	    		near = (ArrayList<Entity>) am.getEntity().getBukkitEntity().getNearbyEntities(40, 40, 40);
				            	    		displayMessage();
		            	    			}
		            	    		});
	            	    		}
	            	    	}
		    				if (setgcd != -1) {
		    					am.setGlobalCooldown(setgcd);
		    				}
		    				
		    				// If stun children, iterate through each child and also give them a stun tag
		    				if(stunchildren) {
		    					for (AbstractEntity e : am.getChildren()) {
		    						e.getBukkitEntity().addScoreboardTag("StunTag");
		    					}
		    				}
		    				
	        	    	}
	        			result = true;
	        		}
	        		else {
	        			result = false;
	        			break;
	        		}
	        	}
	        }
	        if(action && !castinstead) {
	        	result = !result;
	        }
        }
        return !result;
    }
    
    public void displayMessage() {
		for(Entity e : near) {
			if (e instanceof Player) {
				Player p = (Player) e;
				p.sendMessage(msg);
			}
		}
    }
}
