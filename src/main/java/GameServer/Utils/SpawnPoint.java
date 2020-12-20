package GameServer.Utils;

public class SpawnPoint {
    private boolean isFree;
    private Vector2 location;

    public SpawnPoint(Vector2 location) {
        location.setY(1700 - location.getY());
        this.location = location;
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Vector2 getLocation() {
        return location;
    }
}
