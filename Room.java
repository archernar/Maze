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
        return this.name + "   " + this.doorMap();
    }
    public String toString(Room r) {
        return this.name + "   " + this.doorMap(r);
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

    public void draw() {
        String szOut = "";
        String szTop = "+==========+";
        String szBot = "+==========+";
        String szName = "+== " + this.name + " ==+";
        String szSpace = "+== " + "    " + " ==+";
        System.out.println(szTop);
        System.out.println(szName);
        System.out.println(szSpace);
        System.out.println(szSpace);
        System.out.println(szSpace);
        System.out.println(szBot);
    }

    public boolean allDoorsLocked() {
        boolean bRet = false;
        for (int i=0;i<this.doorcount;i++) {
            bRet = bRet || (!this.doors[i].locked);
        }
        return !bRet;
    }
    public String oc(int d) {
        return ((this.doors[d].gonethroughalready) ? "X" : "-") + "/" + ((this.doors[d].locked) ? "X" : "-");
    }
    public String doorLook(int d,Room r) {
        String BoldOn  = "";
        String BoldOff = "";
        if ( this.doors[d].room == r ) {
            BoldOn  = TERMCTRL.setTermString(TERMCTRL.FG_GREEN, TERMCTRL.BG_CYAN);
            BoldOff = TERMCTRL.setTermString(TERMCTRL.FG_RESET, TERMCTRL.BG_BLACK);
        }
        if ( this.doors[d].locked ) {
            BoldOn  = TERMCTRL.setTermString(TERMCTRL.FG_RED, TERMCTRL.BG_CYAN);
            BoldOff = TERMCTRL.setTermString(TERMCTRL.FG_RESET, TERMCTRL.BG_BLACK);
        }
        if ( this.doors[d].gonethroughalready ) {
            BoldOn  = TERMCTRL.setTermString(TERMCTRL.FG_YELLOW, TERMCTRL.BG_CYAN);
            BoldOff = TERMCTRL.setTermString(TERMCTRL.FG_RESET, TERMCTRL.BG_BLACK);
        }
        return BoldOn + (this.doors[d].room).name + BoldOff + "/" + oc(d);
    }
    public String doorLook(int d) {
        String BoldOn  ="";
        String BoldOff ="";
        return BoldOn + (this.doors[d].room).name + BoldOff  + "/" + oc(d);
    }
    public String doorMap(Room r) {
        return doorLook(0,r) + " " + doorLook(1,r) + " " + doorLook(2,r) + " " + doorLook(3,r) + " " + doorLook(4,r) + " " + doorLook(5,r);
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
