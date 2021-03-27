import java.util.Random;


public class Room {
    public Door[] doors;
    public String name;
    public int doorcount;
    static final int MAXDOORSINROOM = 2;

    private static int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public String toString() {
        return this.name + " " + this.doorMap();
    }

    public Room() {
        int n = Room.rand(1,MAXDOORSINROOM);
        n=6;
        doors = new Door[n];
        this.doorcount = n;
        for (int i=0;i<n;i++) {
            doors[i] = new Door(this);
        }
    }
    public Room(String name) {
        int n = Room.rand(1,MAXDOORSINROOM);
        n=6;
        doors = new Door[n];
        this.doorcount = n;
        for (int i=0;i<n;i++) {
            doors[i] = new Door(this);
        }
        this.name = name;
    }
    public String oc(int d) {
        return ((this.doors[d].gonethroughalready) ? "o" : "c") + "/" + ((this.doors[d].locked) ? "X" : "-");
    }
    public String doorLook(int d) {
        return (this.doors[d].room).name  + "/" + oc(d);
    }
    public String doorMap() {
        return doorLook(0) + " " + doorLook(1) + " " + doorLook(2) + " " + doorLook(3) + " " + doorLook(4) + " " + doorLook(5);
    }


    public Room doorLeft() {
        this.doors[0].gonethroughalready = true;
        return this.doors[0].room;
    }
    public Room doorRight() {
        this.doors[1].gonethroughalready = true;
        return this.doors[1].room;
    }
    public Room doorLeftLeft() {
        this.doors[2].gonethroughalready = true;
        return this.doors[2].room;
    }
    public Room doorRightRight() {
        this.doors[3].gonethroughalready = true;
        return this.doors[3].room;
    }
    public Room doorUp() {
        this.doors[4].gonethroughalready = true;
        return this.doors[4].room;
    }
    public Room doorDown() {
        this.doors[5].gonethroughalready = true;
        return this.doors[5].room;
    }

    public boolean doorLeftGoneThroughAlready() {
        return this.doors[0].gonethroughalready;
    }
    public boolean doorRightGoneThroughAlready() {
        return this.doors[1].gonethroughalready;
    }
    public boolean doorLeftLeftGoneThroughAlready() {
        return this.doors[2].gonethroughalready;
    }
    public boolean doorRightiRightGoneThroughAlready() {
        return this.doors[3].gonethroughalready;
    }
    public boolean doorUpGoneThroughAlready() {
        return this.doors[4].gonethroughalready;
    }
    public boolean doorDownGoneThroughAlready() {
        return this.doors[5].gonethroughalready;
    }

}
