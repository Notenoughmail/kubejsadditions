package pie.ilikepiefoo.fabric.events.sleep;

import dev.latvian.mods.kubejs.entity.EntityJS;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import pie.ilikepiefoo.fabric.FabricEventsJS;

/**
 * An event that checks whether a player's spawn can be set when sleeping.
 *
 * <p>Vanilla always allows this operation.
 */
public class AllowSettingSpawnEventJS extends PlayerEventJS {

	private final Player player;
	private final BlockPos sleepingPos;

	public AllowSettingSpawnEventJS(Player player, BlockPos sleepingPos) {
		this.player = player;
		this.sleepingPos = sleepingPos;
	}

	@Override
	public boolean canCancel() {
		return true;
	}

	public BlockPos getSleepingPos() {
		return sleepingPos;
	}

	@Override
	public EntityJS getEntity() {
		return entityOf(player);
	}

	public BlockContainerJS getPos() {
		return getLevel().getBlock(sleepingPos);
	}

	/**
	 * Checks whether a player's spawn can be set when sleeping.
	 *
	 * @param player      the sleeping player
	 * @param sleepingPos the sleeping position
	 * @return {@code true} if allowed, {@code false} otherwise
	 */
	public static boolean handler(Player player, BlockPos sleepingPos) {
		if(ServerScriptManager.instance == null)
			return true;
		AllowSettingSpawnEventJS event = new AllowSettingSpawnEventJS(player, sleepingPos);
		event.post(ScriptType.SERVER, FabricEventsJS.ALLOW_SETTING_SPAWN);
		return event.isCancelled();
	}
}
