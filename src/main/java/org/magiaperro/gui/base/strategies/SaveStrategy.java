package org.magiaperro.gui.base.strategies;

import org.magiaperro.gui.base.PersistentGui;

public interface SaveStrategy {
	public void save(PersistentGui persistentGui);
	public void load(PersistentGui persistentGui);
}
