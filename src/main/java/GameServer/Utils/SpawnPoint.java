package GameServer.Utils;

public class SpawnPoint {
    private boolean isFree;
    private Vector2 location;

    public SpawnPoint(Vector2 location) {
        this.location = location;
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
