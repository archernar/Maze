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
    static final int LEFT = 0;
    static final int RIGHT = 1;
    static final int LEFTLEFT = 2;
    static final int RIGHTRIGHT = 3;
    static final int UP = 4;
    static final int DOWN = 5;

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
        return generatedString;
    }


    private static int Seek(Room r, Room entranceroom, Room exitroom) {
        Random random = new Random();
        int d = 0;
        while (d < 1000000) {
            int turn = random.nextInt(4 + 1 - 1) + 1;
            if (turn == LEFT)  r = r.doorLeft();
            if (turn == RIGHT) r = r.doorRight();
            if (turn == LEFTLEFT)  r = r.doorLeftLeft();
            if (turn == RIGHTRIGHT) r = r.doorRightRight();
            if (turn == UP) r = r.doorUp();
            if (turn == DOWN) r = r.doorDown();
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

    private static String direction(int t) {
        String szRet = "";
        if (t==0) szRet = "LEFT";
        if (t==1) szRet = "RIGHT";
        if (t==2) szRet = "LEFTLEFT";
        if (t==3) szRet = "RIGHTRIGHT";
        if (t==4) szRet = "UP";
        if (t==5) szRet = "DOWN";
        return szRet;
    }
    static int recursive_entry = 0;
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
                int turn = randDoor();
                int ct = 0;
                while ( (r.doors[turn].gonethroughalready) && (ct<10) && (r.doors[turn].locked)) {
                    if  (r.doors[turn].gonethroughalready) System.out.println("    " + (direction(turn)) + " Door is Open, Turning again...");
                    if  (r.doors[turn].locked) System.out.println("    " + (direction(turn)) + " Door is Locked, Turning again...");
                    //turn = random.nextInt(6 + 1 - 1) + 1;
                    turn = nextdoor(turn);
                    ct++;
                }
                if ( ct == 10 ) {
                    turn = randDoor();
                }
                String sz = "";

                if (turn == LEFT)  sz = r.doorLeft().name;
                if (turn == RIGHT) sz = r.doorRight().name;
                if (turn == LEFTLEFT)  sz = r.doorLeftLeft().name;
                if (turn == RIGHTRIGHT) sz = r.doorRightRight().name;
                if (turn == UP) sz = r.doorUp().name;
                if (turn == DOWN) sz = r.doorDown().name;

                System.out.println("    " + direction(turn) + " Turning to " + sz + "   " + recursive_entry);

                if (turn == LEFT)  r = r.doorLeft();
                if (turn == RIGHT) r = r.doorRight();
                if (turn == LEFTLEFT)  r = r.doorLeftLeft();
                if (turn == RIGHTRIGHT) r = r.doorRightRight();
                if (turn == UP) r = r.doorUp();
                if (turn == DOWN) r = r.doorDown();
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
        Room r = rooms[rand(0,ROOMCOUNT-1)]; 
        while (r == rx)
            r = rooms[rand(0,ROOMCOUNT-1)]; 
        return r;
    }

    public static void main(String[] args) {

        System.out.println("roomcount: " + ROOMCOUNT);
        for (int i=0;i<ROOMCOUNT;i++) {
            rooms[i] = new Room();
            arrRoom.add(rooms[i]);
            rooms[i].name = randomString();
            for (int j=0;j<rooms[i].doorcount;j++) {
                arrDoor.add(rooms[i].doors[j]);
            }

        }
        Room rr;
        for (int i = 0; i < arrDoor.size(); i++) {
            rr = randomRoom((arrDoor.get(i)).roomTheDoorIsIn); 
            (arrDoor.get(i)).attach( rooms[rand(0,ROOMCOUNT-1)] );
            if (oneoutoften())  (arrDoor.get(i)).locked = true;
        }

        int exitDoor = rand(0,arrDoor.size()-1);
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
