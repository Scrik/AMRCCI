/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package amrcci;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NoCreativeHorseInteract implements Listener {
	
	private Config config;
	public NoCreativeHorseInteract(Config config)
	{
		this.config = config;
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void onInteractEntity(PlayerInteractEntityEvent e)
	{
		if (!config.nocreativehorseinteractenabled) {return;}
		if (e.getPlayer().hasPermission("amrcci.gminteract")) {return;}
		
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			if (e.getRightClicked().getType() == EntityType.HORSE)
			{
				Horse horse = (Horse) e.getRightClicked();
				if (horse.isCarryingChest())
				{
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled=true)
	public void onOpenEntityChest(InventoryOpenEvent e)
	{
		if (!config.nocreativehorseinteractenabled) {return;}
		if (e.getPlayer().hasPermission("amrcci.gminteract")) {return;}
		
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
		{
			if (e.getPlayer().isInsideVehicle())
			{
				if (e.getPlayer().getVehicle() instanceof Horse)
				{
					Horse horse = (Horse) e.getPlayer().getVehicle();
					if (horse.isCarryingChest())
					{
						e.setCancelled(true);
					}
				}
			}
		}		
	}
	

}
