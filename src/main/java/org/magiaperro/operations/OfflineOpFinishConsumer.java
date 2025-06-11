package org.magiaperro.operations;

@FunctionalInterface
public interface OfflineOpFinishConsumer {
    void finish(Long fullCycles, Long excessTime);
}
