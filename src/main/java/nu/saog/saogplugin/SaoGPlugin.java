package nu.saog.saogplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SaoGPlugin extends JavaPlugin {

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		getLogger().info("onDisable has been invoked!");
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		getLogger().info("onEnable has been invoked!");
	}

	private boolean noharm = false;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("saog")) { // If the player typed /basic then do the following...
			if(args.length > 0) {
				Player target = (Bukkit.getServer().getPlayer(args[0]));
		        if (target == null) {
		           sender.sendMessage(args[0] + " is not online!");
		           return false;
		        } else {
		        	target.setFireTicks(600);
		        	target.sendMessage("You were sent a message");
		        	noharm = true;
		        	Location loc = target.getLocation();
		    		getLogger().info("Player is at location x " + loc.getX() + ", y " + loc.getY() + ", z " + loc.getZ());
		        	int rows = 10;
		        	int cols = 10;
		        	for(int x = 0;x < rows;x++) {
		        		for(int z = 0; z < cols;z++) {
			        		loc.setX(x);
			        		loc.setZ(z);
			        		if(x == 0 || x == (rows-1)) {
				        		getLogger().info("setting block location to " + x + " x " + z);
			        			generateCube(loc,1,Material.TNT);
			        		} else if(z == 0 || z == (cols-1)) {
				        		getLogger().info("setting block location to " + x + " x " + z);
			        			generateCube(loc,1,Material.TNT);
			        		}
		        		}
		        	}
		        }
		        return true;
			}
		} //If this has happened the function will return true. 
	        // If this hasn't happened the value of false will be returned.
		return false; 
	}

	public void generateCube(Location loc, int length, Material material) {
	    // Set one corner of the cube to the given location.
	    // Uses getBlockN() instead of getN() to avoid casting to an int later.
	    int x1 = loc.getBlockX(); 
	    int y1 = loc.getBlockY();
	    int z1 = loc.getBlockZ();
	 
	    // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
	    int x2 = x1 + length;
	    int y2 = y1 + length;
	    int z2 = z1 + length;
	 
	    World world = loc.getWorld();
	    
	    // Loop over the cube in the x dimension.
	    for (int xPoint = x1; xPoint <= x2; xPoint++) { 
	        // Loop over the cube in the y dimension.
	        for (int yPoint = y1; yPoint <= y2; yPoint++) {
	            // Loop over the cube in the z dimension.
	            for (int zPoint = z1; zPoint <= z2; zPoint++) {
	                // Get the block that we are currently looping over.
	                Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
	        		getLogger().info("Block should be createad at location x " + xPoint + " y " + yPoint + " z " + zPoint);
	                // Set the block to type 57 (Diamond block!)
	                currentBlock.setType(material);
	            }
	        }
	    }
	}
	
	@EventHandler
	public void onExplosionPrime(ExplosionPrimeEvent event) {	
	    Entity entity = event.getEntity();
	 
	    // If the event is about primed TNT (TNT that is about to explode), then do something
	    if (entity instanceof TNTPrimed) {
	        entity.getWorld().createExplosion(entity.getLocation(), 0);
	    }
	}	
	
}
