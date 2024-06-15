package org.magiaperro.gui.base.factories;

import java.util.UUID;

import org.magiaperro.gui.base.ConcurrentPersistentGui;

public interface ConcurrentGuiFactory {
    ConcurrentPersistentGui createGui(UUID id);
}
