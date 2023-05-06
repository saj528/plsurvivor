package com.mygdx.panzerliedsurvivor.components;

/***
 * The purpose of this class is to keep track of things that need to trigger after a set delay, e.g. reloading and firing
 * The reason for its existence is to reduce the number of total variables we need to track in a class
 * (like reloadSpeed and timeSinceLastReload for example)
 */
public class Timer {
    private final float triggerTime;

    private float timeSinceLastTrigger;

    private int numberOfTriggers;

    public Timer(float triggerTime) {
        this.triggerTime = triggerTime;
        this.timeSinceLastTrigger = 0f;
        this.numberOfTriggers = 0;
    }

    /***
     * Increments the timer by the given delta and determines whether the timer has finished or not.
     * If the timer has finished, the time is decremented by the total to continue counting up.
     * @param delta the delta time to increment the timer by
     * @return true if the timer fired, false otherwise
     */
    public boolean updateTimerAndCheckCompletion(float delta) {
        this.timeSinceLastTrigger += delta;
        if (timeSinceLastTrigger >= triggerTime) {
            timeSinceLastTrigger -= triggerTime;
            numberOfTriggers++;
            return true;
        }

        return false;
    }

    /***
     * Resets a timer to 0. Since a timer can have a "remainder" when it resets, we provide this method to reset it to 0
     * if the timer is no longer being used currently. Specifically for the case where we reload and the timer has a remainder,
     * this would technically make our next reload slightly faster for no reason if we didn't reset. Particularly noticeable
     * if the game is lagging and updates slow down.
     */
    public void reset() {
        this.timeSinceLastTrigger = 0;
    }

    public float getTimeSinceLastTrigger() {
        return this.timeSinceLastTrigger;
    }

    public float getTriggerTime() {
        return this.triggerTime;
    }

    public int getNumberOfTriggers() {
        return this.numberOfTriggers;
    }
}
