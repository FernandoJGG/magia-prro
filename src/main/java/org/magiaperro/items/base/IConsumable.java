package org.magiaperro.items.base;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface IConsumable {
	public void onConsume(PlayerItemConsumeEvent event);
}
