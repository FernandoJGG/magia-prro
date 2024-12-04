package org.magiaperro.operations;

@FunctionalInterface
public interface OperationFinishConsumer {
    void finish(Long fullCycles, Long excessTime);
}
