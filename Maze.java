import java.util.*;
// https://examples.javacodegeeks.com/java-lang-stackoverflowerror-how-to-solve-stackoverflowerror/
// https://github.com/archernar

public class Maze {
    static final int ROOMCOUNT = 10000;
    static final int ENTRANCE  = ROOMCOUNT-1;
    static final int MAXDEPTH  = 500000;
    static Room[] rooms = new Room[ROOMCOUNT];
    static ArrayList<Door> arrDoor = new ArrayList<Door>();
    static ArrayList<Room> arrRoom = new ArrayList<Room>();
    static Room exitroom = new Room("EXIT");
    static Room entranceroom = new Room("ENTR");
    static int recursive_entry = 0;
    static int uniquemoves = 0;
    public Maze() {
        super();
    }
    //
    // Non-Recursive Seek Method
    //
    private static int Seek(Room r, Room entranceroom, Room exitroom) {
        int d = 0;
        while (d < MAXDEPTH) {
            int turn = MAZEWORLD.randDoor();
            if (turn == MAZEWORLD.LEFT)  r = r.doorLeft();
            if (turn == MAZEWORLD.RIGHT) r = r.doorRight();
            if (turn == MAZEWORLD.LEFTLEFT)  r = r.doorLeftLeft();
            if (turn == MAZEWORLD.RIGHTRIGHT) r = r.doorRightRight();
            if (turn == MAZEWORLD.UP) r = r.doorUp();
            if (turn == MAZEWORLD.DOWN) r = r.doorDown();
            if (r == exitroom) { 
                System.out.println("Exit: ");
                d = MAXDEPTH+1;
            }
            else {
                System.out.println(r);
            }

            d++;
        }
        return 0;
    }

    //
    // Recursive Seek Method
    //
    private static int Seekr(Room r, Room entranceroom, Room exitroom) {
        Random random = new Random();
        int nRet = 0;
        recursive_entry++;
        if ( recursive_entry > MAXDEPTH ){
            System.out.println("Quit:");
            System.out.println(MAZEWORLD.rt());
            System.exit(1);
        }

            if (r == exitroom) { 
                System.out.println("Exit: ");
                nRet=1;
            }
            else {
                int turn = MAZEWORLD.randDoor();
                int ct = 0;
                uniquemoves++;
                while ( (r.doors[turn].gonethroughalready) && (ct<10) && (r.doors[turn].locked)) {
                    if  (r.doors[turn].gonethroughalready) System.out.println("Turn: " + (MAZEWORLD.direction(turn)) + " Door-Open, Turn again " + r);
                    if  (r.doors[turn].locked) System.out.println("Turn: " + (MAZEWORLD.direction(turn)) + " Door-Locked, Turn again" + r);
                    turn = MAZEWORLD.nextdoor(turn);
                    ct++;
                }
                // Go through any door locked of otherwise
                if ( ct == 100 ) {
                    System.out.println("Done: " + r);
                    uniquemoves--;
                    nRet=1;
                }
                else {
                    String sz = "";

                    if (turn == MAZEWORLD.LEFT)  sz = r.doorLeft().name;
                    if (turn == MAZEWORLD.RIGHT) sz = r.doorRight().name;
                    if (turn == MAZEWORLD.LEFTLEFT)  sz = r.doorLeftLeft().name;
                    if (turn == MAZEWORLD.RIGHTRIGHT) sz = r.doorRightRight().name;
                    if (turn == MAZEWORLD.UP) sz = r.doorUp().name;
                    if (turn == MAZEWORLD.DOWN) sz = r.doorDown().name;

                    Room roomTo=null;
                    if (turn == MAZEWORLD.LEFT)  roomTo = r.doorLeft();
                    if (turn == MAZEWORLD.RIGHT) roomTo = r.doorRight();
                    if (turn == MAZEWORLD.LEFTLEFT)  roomTo = r.doorLeftLeft();
                    if (turn == MAZEWORLD.RIGHTRIGHT) roomTo = r.doorRightRight();
                    if (turn == MAZEWORLD.UP) roomTo = r.doorUp();
                    if (turn == MAZEWORLD.DOWN) roomTo = r.doorDown();

                    System.out.println("In  : " + r.toString(roomTo) + "  Turn: " + MAZEWORLD.direction(turn) + " " + sz + " " + recursive_entry);
                    // System.out.println("Turn: " + MAZEWORLD.direction(turn) + " " + sz + " " + recursive_entry);

                    if (turn == MAZEWORLD.LEFT)  r = r.doorLeft();
                    if (turn == MAZEWORLD.RIGHT) r = r.doorRight();
                    if (turn == MAZEWORLD.LEFTLEFT)  r = r.doorLeftLeft();
                    if (turn == MAZEWORLD.RIGHTRIGHT) r = r.doorRightRight();
                    if (turn == MAZEWORLD.UP) r = r.doorUp();
                    if (turn == MAZEWORLD.DOWN) r = r.doorDown();
                    try {     
                        nRet = Seekr(r,entranceroom,exitroom);
                    }
                    catch (Exception e) {
                       System.out.println("Exception");
                       System.out.println(MAZEWORLD.rt());
                       System.exit(1);
                    }
                }
            }

        return nRet;
    }
    private static Room randomRoom(Room roomCurrentlyIn) {
        Room r = rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)]; 
        while (r == roomCurrentlyIn)
            r = rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)]; 
        return r;
    }

    public static void main(String[] args) {



        for (int i=0;i<ROOMCOUNT;i++) {
            rooms[i] = new Room();
            arrRoom.add(rooms[i]);
            rooms[i].name = MAZEWORLD.randomString();
            for (int j=0;j<rooms[i].doorcount;j++) {
                arrDoor.add(rooms[i].doors[j]);
            }
        }
        System.out.println("Maze: " + ROOMCOUNT      + " rooms");
        System.out.println("Maze: " + arrDoor.size() + " doors");
        Room rr;
        for (int i = 0; i < arrDoor.size(); i++) {
            rr = randomRoom((arrDoor.get(i)).roomTheDoorIsIn); 
            Room tR = rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)];

            (arrDoor.get(i)).attach( rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)] );
            if (MAZEWORLD.zerototen())  (arrDoor.get(i)).locked = true;
        }

        int exitDoor = MAZEWORLD.rand(0,arrDoor.size()-1);
        (arrDoor.get(exitDoor)).attach( exitroom );

        rooms[ENTRANCE].name="ENTR";
        entranceroom=rooms[ENTRANCE];

        for (int i=0;i<ROOMCOUNT;i++) {
//             rooms[i].draw();
//             System.out.println("");
            if (rooms[i].allDoorsLocked()) {
                System.out.println("LOCK: " + rooms[i]);
                System.exit(0);
            }
        }

        if (true) {
            for (int i=0;i<ROOMCOUNT;i++) {
                    System.out.println("Room: " + rooms[i]);
            }
            System.out.println("");
        }

       try {     
       //  int o  = Seek(entranceroom, entranceroom, exitroom);
       int oo = Seekr(entranceroom, entranceroom, exitroom);
       }
       catch (Exception e) {
           System.out.println("Exception");
           System.out.println(MAZEWORLD.rt());
           System.exit(1);
       }
       System.out.println("");
       System.out.println(MAZEWORLD.rt());
       System.out.println("Maze: " + ROOMCOUNT      + " rooms");
       System.out.println("Maze: " + arrDoor.size() + " doors");
       System.out.println("UNIQ: " + uniquemoves);
}


}
