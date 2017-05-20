package io.github.phantamanta44.shlgl.engine;

/**
 * Timer that schedules game ticks.
 * @author Evan Geng
 */
public class TickTimer {

    /**
     * Tick length, in milliseconds.
     */
    private double millisPerTick = 50D;

    /**
     * Whether this timer is enabled or not.
     */
    private boolean enabled = false;

    /**
     * The time of the last call to {@link #getElapsedTicks()}.
     */
    private double callTime;

    /**
     * Sets the tickrate.
     * @param rate The tickrate, in ticks per second.
     */
    public void setTickRate(int rate) {
        if (enabled)
            throw new IllegalStateException("Cannot change tick rate while timer is enabled!");
        if (rate > 1000)
            throw new IllegalArgumentException("Tick rate cannot be greater than 1000 per second!");
        if (rate < 1)
            throw new IllegalArgumentException("Must have at least one tick per second!");
        this.millisPerTick = Math.floor(1000D / (double)rate);
    }

    /**
     * Begins the tick timer.
     */
    public void begin() {
        enabled = true;
        callTime = System.currentTimeMillis();
    }

    /**
     * Retrieves the number of elapsed ticks since the previous call to {@link #getElapsedTicks()}.
     * @return The elapsed tick count.
     */
    public int getElapsedTicks() {
        if (!enabled)
            throw new IllegalStateException("Timer is not enabled!");
        long time = System.currentTimeMillis();
        double elapsed = time - callTime;
        int ticks = (int)Math.floor(elapsed / millisPerTick);
        callTime = time - (elapsed % millisPerTick);
        return ticks;
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        enabled = false;
    }

}
