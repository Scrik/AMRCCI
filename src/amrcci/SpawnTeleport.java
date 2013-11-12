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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.events.AuthMeTeleportEvent;

public class SpawnTeleport implements Listener {

	private Main main;
	private File configfile;
	public SpawnTeleport(Main main)
	{
		this.main = main;
		this.configfile = new File(main.getDataFolder(),"spawntpconfig.yml");
	}
	public void loadConfig()
	{
		FileConfiguration config = YamlConfiguration.loadConfiguration(configfile);
		worlds = config.getStringList("worlds");
		config.set("worlds", worlds);
		try {config.save(configfile);} catch (IOException e) {}
	}
	
	private List<String> worlds = new ArrayList<String>();
	
	@EventHandler
	public void onPlayerLoginAfterTeleport(AuthMeTeleportEvent e)
	{
		final Player player = e.getPlayer();
		if (worlds.contains(e.getTo().getWorld().getName()) && !player.hasPermission("amrcci.ignoretp"))
		{
			final Location spawn = AuthMe.getInstance().essentialsSpawn;
			if (spawn != null)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable()
				{
					public void run()
					{
						player.teleport(spawn);
					}	
				});
			}
		}
	}

}
