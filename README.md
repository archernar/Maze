# Maze
    Compile with javac -d ./classes *.java
    Run with java -cp ./classes Maze
    Run with something like (altered JVM memory settings) java -cp ./classes -Xms1024m -Xmx2048m -Xss100m  Maze

    //  __  __                  _                  
    // |  \/  | __ _ _______   (_) __ ___   ____ _ 
    // | |\/| |/ _` |_  / _ \  | |/ _` \ \ / / _` |
    // | |  | | (_| |/ /  __/_ | | (_| |\ V / (_| |
    // |_|  |_|\__,_/___\___(_)/ |\__,_| \_/ \__,_|
    //                       |__/                  
    import java.util.*;
    // https://examples.javacodegeeks.com/java-lang-stackoverflowerror-how-to-solve-stackoverflowerror/
    
    public class Maze {
        static final int ROOMCOUNT = 10;
        static final int ENTRANCE  = ROOMCOUNT-1;
        static final int MAXDEPTH  = 500000;
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
                    try {     
                        System.out.println("In  : " + r);
                    }
                    catch (StackOverflowError e) {
                       System.exit(1);
                    } 
                    catch (Exception e) {
                       System.out.println(MAZEWORLD.rt());
                       System.exit(1);
                    } 
                    int turn = MAZEWORLD.randDoor();
                    int ct = 0;
                    while ( (r.doors[turn].gonethroughalready) && (ct<10) && (r.doors[turn].locked)) {
                        if  (r.doors[turn].gonethroughalready) System.out.println("Turn: " + (MAZEWORLD.direction(turn)) + " Door-Open, Turn again");
                        if  (r.doors[turn].locked) System.out.println("Turn: " + (MAZEWORLD.direction(turn)) + " Door-Locked, Turn again");
                        turn = MAZEWORLD.nextdoor(turn);
                        ct++;
                    }
                    // Go through any door locked of otherwise
                    if ( ct == 10 ) {
                        System.out.println("Done: " + r);
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
    
                        System.out.println("Turn: " + MAZEWORLD.direction(turn) + " " + sz + " " + recursive_entry);
    
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
                if (MAZEWORLD.oneoutofahundred())  (arrDoor.get(i)).locked = true;
            }
    
            int exitDoor = MAZEWORLD.rand(0,arrDoor.size()-1);
            (arrDoor.get(exitDoor)).attach( exitroom );
    
            rooms[ENTRANCE].name="ENTRANCE";
            entranceroom=rooms[ENTRANCE];
    
            for (int i=0;i<ROOMCOUNT;i++) {
    //             rooms[i].draw();
    //             System.out.println("");
                if (rooms[i].allDoorsLocked()) {
                    System.out.println("LOCK: " + rooms[i]);
                    System.exit(0);
                }
            }
    
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
               System.out.println(MAZEWORLD.rt());
               System.exit(1);
           }
           System.out.println("");
           System.out.println(MAZEWORLD.rt());
           System.out.println("Maze: " + ROOMCOUNT      + " rooms");
           System.out.println("Maze: " + arrDoor.size() + " doors");
    
    }
    
    
    }
# Room

    //  ____                          _                  
    // |  _ \ ___   ___  _ __ ___    (_) __ ___   ____ _ 
    // | |_) / _ \ / _ \| '_ ` _ \   | |/ _` \ \ / / _` |
    // |  _ < (_) | (_) | | | | | |_ | | (_| |\ V / (_| |
    // |_| \_\___/ \___/|_| |_| |_(_)/ |\__,_| \_/ \__,_|
    //                             |__/                  
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
# Door

    //  ____                    _                  
    // |  _ \  ___   ___  _ __ (_) __ ___   ____ _ 
    // | | | |/ _ \ / _ \| '__|| |/ _` \ \ / / _` |
    // | |_| | (_) | (_) | | _ | | (_| |\ V / (_| |
    // |____/ \___/ \___/|_|(_)/ |\__,_| \_/ \__,_|
    //                       |__/                  
    import java.util.Random;
    
    
    public class Door {
        public Room room;
        public Room roomTheDoorIsIn;
        public boolean gonethroughalready;
        public boolean locked;
        public static int rand(int min, int max) {
            Random random = new Random();
            return random.nextInt(max + 1 - min) + min;
        }
       public Door(Room r) {
           this.roomTheDoorIsIn = r;
       }
    
       public void attach(Room r) {
           this.room = r;
           this.gonethroughalready = false;
           this.locked = false;
       }
    
    }
# Constants and Statics

    //  __  __    _     ____________        _____  ____  _     ____    _                  
    // |  \/  |  / \   |__  / ____\ \      / / _ \|  _ \| |   |  _ \  (_) __ ___   ____ _ 
    // | |\/| | / _ \    / /|  _|  \ \ /\ / / | | | |_) | |   | | | | | |/ _` \ \ / / _` |
    // | |  | |/ ___ \  / /_| |___  \ V  V /| |_| |  _ <| |___| |_| | | | (_| |\ V / (_| |
    // |_|  |_/_/   \_\/____|_____|  \_/\_/  \___/|_| \_\_____|____(_)/ |\__,_| \_/ \__,_|
    //                                                              |__/                  
    import java.util.*;
    
    public class MAZEWORLD {
        static final String directions[] = {"LEFT","RIGHT","LEFTLEFT","RIGHTRIGHT","UP", "DOWN"};
        static final int LEFT = 0;
        static final int RIGHT = 1;
        static final int LEFTLEFT = 2;
        static final int RIGHTRIGHT = 3;
        static final int UP = 4;
        static final int DOWN = 5;
    
        public static String randomString() {
            int length = 4;
            String candidateChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                sb.append(candidateChars.charAt(random.nextInt(candidateChars
                        .length())));
            }
            return sb.toString();
        }
            
        public static String randomString2() {
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
        public static boolean oneoutofahundred() {
            Random random = new Random();
            int r = random.nextInt(100 + 1 - 1) + 1;
            return (r==1) ? true : false;
        }
        public static boolean oneoutoften() {
            Random random = new Random();
            int r = random.nextInt(10 + 1 - 1) + 1;
            return (r==1) ? true : false;
        }
        public static boolean threeoutoften() {
            Random random = new Random();
            int r = random.nextInt(10 + 1 - 1) + 1;
            return (r<=3) ? true : false;
        }
        public static boolean fiveoutoften() {
            Random random = new Random();
            int r = random.nextInt(10 + 1 - 1) + 1;
            return (r<=5) ? true : false;
        }
        public static int randDoor() {
            Random random = new Random();
            return random.nextInt(5 + 1 - 0) + 0;
        }
        public static int rand(int min, int max) {
            Random random = new Random();
            return random.nextInt(max + 1 - min) + min;
        }
        public static int nextdoor(int d) {
            return ( (d+1) > 5) ? 0 : d+1;
        }
        public static String direction(int t) {
            return MAZEWORLD.directions[t];
        }
    
        public static String rt() {
        final double z = 1024.0 / 1024.0;
        Runtime rt = Runtime.getRuntime();
        Locale.setDefault(Locale.US);
             return "" + 
                    String.format("Max %,d, ", rt.maxMemory()) +
                    String.format("Total %,d, ", rt.totalMemory()) +
                    String.format("Free %,d, ", rt.freeMemory()) +
                    String.format("Avail %,d", rt.totalMemory() - rt.freeMemory());
        }
    
    }
