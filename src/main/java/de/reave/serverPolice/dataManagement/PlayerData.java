package de.reave.serverPolice.dataManagement;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class PlayerData {
    Location lastPosition;
    Vector lastVelocity;
    boolean lastOnGround;
    int flagLevel;
    boolean setbacks = true;

    PlayerData(Location lastPosition, Vector lastVelocity){
        this.lastPosition = lastPosition;
        this.lastVelocity = lastVelocity;
    }

    // Getters and setters
    public Vector getLastVelocity() {
        return lastVelocity;
    }

    public void setLastVelocity(Vector lastVelocity) {
        this.lastVelocity = lastVelocity;
    }

    public Location getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(Location lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getFlagLevel() {
        return flagLevel;
    }

    public void setFlagLevel(int flagLevel) {
        this.flagLevel = flagLevel;
    }
    public boolean isLastOnGround() {
        return lastOnGround;
    }

    public void setLastOnGround(boolean lastOnGround) {
        this.lastOnGround = lastOnGround;
    }

    public boolean isSetbacks() {
        return setbacks;
    }

    public void setSetbacks(boolean setbacks) {
        this.setbacks = setbacks;
    }
}
