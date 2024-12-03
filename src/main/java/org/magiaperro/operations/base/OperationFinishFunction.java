package org.magiaperro.operations.base;

@FunctionalInterface
public interface OperationFinishFunction {
    void finish(int excessLoops, int excessCycles);
}
