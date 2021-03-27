import java.util.*;
// https://examples.javacodegeeks.com/java-lang-stackoverflowerror-how-to-solve-stackoverflowerror/

public class Maze {
    static final int ROOMCOUNT = 60;
    static final int ENTRANCE  = ROOMCOUNT-1;
    static Room[] rooms = new Room[ROOMCOUNT];
    static ArrayList<Door> arrDoor = new ArrayList<Door>();
    static ArrayList<Room> arrRoom = new ArrayList<Room>();
    static Room exitroom = new Room("EXIT");
    static Room entranceroom = new Room("ENTRANCE");
    static int recursive_entry = 0;
    //
    // Non-Recursive Seek Method
    //
    private static int Seek(Room r, Room entranceroom, Room exitroom) {
        Random random = new Random();
        int d = 0;
        while (d < 1000000) {
            int turn = MAZEWORLD.randDoor();
            if (turn == MAZEWORLD.LEFT)  r = r.doorLeft();
            if (turn == MAZEWORLD.RIGHT) r = r.doorRight();
            if (turn == MAZEWORLD.LEFTLEFT)  r = r.doorLeftLeft();
            if (turn == MAZEWORLD.RIGHTRIGHT) r = r.doorRightRight();
            if (turn == MAZEWORLD.UP) r = r.doorUp();
            if (turn == MAZEWORLD.DOWN) r = r.doorDown();
            if (r == exitroom) { 
                System.out.println("Exiting " + r.name);
                d = 50000;
            }
            else {
                System.out.println("I am in " + r.name + " {{ " + r.doorMap());
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
        if ( recursive_entry > 1000 ){
            System.out.println("Quiting");
            System.exit(1);
        }

            if (r == exitroom) { 
                System.out.println("Exiting " + r.name);
                nRet=1;
            }
            else {
                System.out.println("I am in " + r.name + "   There are doors here: " + r.doorMap());
                int turn = MAZEWORLD.randDoor();
                int ct = 0;
                while ( (r.doors[turn].gonethroughalready) && (ct<10) && (r.doors[turn].locked)) {
                    if  (r.doors[turn].gonethroughalready) System.out.println("    " + (MAZEWORLD.direction(turn)) + " Door is Open, Turning again...");
                    if  (r.doors[turn].locked) System.out.println("    " + (MAZEWORLD.direction(turn)) + " Door is Locked, Turning again...");
                    //turn = random.nextInt(6 + 1 - 1) + 1;
                    turn = MAZEWORLD.nextdoor(turn);
                    ct++;
                }
                if ( ct == 10 ) {
                    turn = MAZEWORLD.randDoor();
                }
                String sz = "";

                if (turn == MAZEWORLD.LEFT)  sz = r.doorLeft().name;
                if (turn == MAZEWORLD.RIGHT) sz = r.doorRight().name;
                if (turn == MAZEWORLD.LEFTLEFT)  sz = r.doorLeftLeft().name;
                if (turn == MAZEWORLD.RIGHTRIGHT) sz = r.doorRightRight().name;
                if (turn == MAZEWORLD.UP) sz = r.doorUp().name;
                if (turn == MAZEWORLD.DOWN) sz = r.doorDown().name;

                System.out.println("    " + MAZEWORLD.direction(turn) + " Turning to " + sz + "   " + recursive_entry);

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
                   System.exit(1);
                }
            }

        return nRet;
    }
    private static Room randomRoom(Room rx) {
        Room r = rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)]; 
        while (r == rx)
            r = rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)]; 
        return r;
    }

    public static void main(String[] args) {

        System.out.println("roomcount: " + ROOMCOUNT);
        for (int i=0;i<ROOMCOUNT;i++) {
            rooms[i] = new Room();
            arrRoom.add(rooms[i]);
            rooms[i].name = MAZEWORLD.randomString();
            for (int j=0;j<rooms[i].doorcount;j++) {
                arrDoor.add(rooms[i].doors[j]);
            }

        }
        Room rr;
        for (int i = 0; i < arrDoor.size(); i++) {
            rr = randomRoom((arrDoor.get(i)).roomTheDoorIsIn); 
            (arrDoor.get(i)).attach( rooms[MAZEWORLD.rand(0,ROOMCOUNT-1)] );
            if (MAZEWORLD.oneoutoften())  (arrDoor.get(i)).locked = true;
        }

        int exitDoor = MAZEWORLD.rand(0,arrDoor.size()-1);
        (arrDoor.get(exitDoor)).attach( exitroom );

        rooms[ENTRANCE].name="ENTRANCE";
        entranceroom=rooms[ENTRANCE];
        if (false) {
            for (int i=0;i<ROOMCOUNT;i++) {
                for (int j=0;j<rooms[i].doorcount;j++) {
                    System.out.println("  " + rooms[i].name + "  " + rooms[i].doorcount + "  " + rooms[i].doors[j].room.name);
                }
            }
        }

       try {     
       //  int o  = Seek(entranceroom, entranceroom, exitroom);
       int oo = Seekr(entranceroom, entranceroom, exitroom);
       }
       catch (Exception e) {
           System.out.println("Exception");
           System.exit(1);
       }

}


}
