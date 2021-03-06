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
    // constuctor: public Maze()
    // not-specified static ROOMCOUNT
    // not-specified static ENTRANCE
    // not-specified static MAXDEPTH
    // not-specified static rooms
    // not-specified static arrDoor
    // not-specified static arrRoom
    // not-specified static exitroom
    // not-specified static entranceroom
    // not-specified static recursive_entry
    // not-specified static uniquemoves
    // private static int Maze.Seekr(Room,Room,Room)
    // private static Room Maze.randomRoom(Room)
    // private static int Maze.Seek(Room,Room,Room)
    // public static void Maze.main(java.lang.String[])
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
# Room

    //  ____                          _                  
    // |  _ \ ___   ___  _ __ ___    (_) __ ___   ____ _ 
    // | |_) / _ \ / _ \| '_ ` _ \   | |/ _` \ \ / / _` |
    // |  _ < (_) | (_) | | | | | |_ | | (_| |\ V / (_| |
    // |_| \_\___/ \___/|_| |_| |_(_)/ |\__,_| \_/ \__,_|
    //                             |__/                  
    // constuctor: public Room(java.lang.String)
    // constuctor: public Room()
    // public public doors
    // public public name
    // public public doorcount
    // not-specified static MAXDOORSINROOM
    // private static int Room.rand(int,int)
    // public java.lang.String Room.toString()
    // public java.lang.String Room.toString(Room)
    // public java.lang.String Room.oc(int)
    // public java.lang.String Room.doorMap()
    // public java.lang.String Room.doorMap(Room)
    // public java.lang.String Room.doorLook(int)
    // public java.lang.String Room.doorLook(int,Room)
    // public void Room.draw()
    // public boolean Room.allDoorsLocked()
    // public Room Room.doorLeft()
    // public Room Room.doorRight()
    // public Room Room.doorLeftLeft()
    // public Room Room.doorRightRight()
    // public Room Room.doorUp()
    // public Room Room.doorDown()
    // public boolean Room.doorLeftGoneThroughAlready()
    // public boolean Room.doorRightGoneThroughAlready()
    // public boolean Room.doorLeftLeftGoneThroughAlready()
    // public boolean Room.doorRightiRightGoneThroughAlready()
    // public boolean Room.doorUpGoneThroughAlready()
    // public boolean Room.doorDownGoneThroughAlready()
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
# Door

    //  ____                    _                  
    // |  _ \  ___   ___  _ __ (_) __ ___   ____ _ 
    // | | | |/ _ \ / _ \| '__|| |/ _` \ \ / / _` |
    // | |_| | (_) | (_) | | _ | | (_| |\ V / (_| |
    // |____/ \___/ \___/|_|(_)/ |\__,_| \_/ \__,_|
    //                       |__/                  
    // constuctor: public Door(Room)
    // constuctor: public Door()
    // public public room
    // public public roomTheDoorIsIn
    // public public gonethroughalready
    // public public locked
    // public static int Door.rand(int,int)
    // public void Door.attach(Room)
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
       public Door() {
           this.roomTheDoorIsIn = null;
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
    // constuctor: public MAZEWORLD()
    // not-specified static directions
    // not-specified static LEFT
    // not-specified static RIGHT
    // not-specified static LEFTLEFT
    // not-specified static RIGHTRIGHT
    // not-specified static UP
    // not-specified static DOWN
    // private static boolean MAZEWORLD.lambda$randomString2$0(int)
    // public static java.lang.String MAZEWORLD.direction(int)
    // public static java.lang.String MAZEWORLD.rt()
    // public static java.lang.String MAZEWORLD.randomString()
    // public static java.lang.String MAZEWORLD.randomString2()
    // public static boolean MAZEWORLD.oneoutofahundred()
    // public static boolean MAZEWORLD.zerototen()
    // public static boolean MAZEWORLD.oneoutoften()
    // public static boolean MAZEWORLD.threeoutoften()
    // public static boolean MAZEWORLD.fiveoutoften()
    // public static int MAZEWORLD.randDoor()
    // public static int MAZEWORLD.rand(int,int)
    // public static int MAZEWORLD.nextdoor(int)
    import java.util.*;
    
    public class MAZEWORLD {
        static final String directions[] = {"LEFT","RIGHT","LEFTLEFT","RIGHTRIGHT","UP", "DOWN"};
        static final int LEFT = 0;
        static final int RIGHT = 1;
        static final int LEFTLEFT = 2;
        static final int RIGHTRIGHT = 3;
        static final int UP = 4;
        static final int DOWN = 5;
        public MAZEWORLD() {
            super();
        }
    
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
        public static boolean zerototen() {
            Random random = new Random();
            int r = random.nextInt(10 + 1 - 0) + 0;
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
# Terminal Control

    //  _____ _____ ____  __  __  ____ _____ ____  _        _                  
    // |_   _| ____|  _ \|  \/  |/ ___|_   _|  _ \| |      (_) __ ___   ____ _ 
    //   | | |  _| | |_) | |\/| | |     | | | |_) | |      | |/ _` \ \ / / _` |
    //   | | | |___|  _ <| |  | | |___  | | |  _ <| |___ _ | | (_| |\ V / (_| |
    //   |_| |_____|_| \_\_|  |_|\____| |_| |_| \_\_____(_)/ |\__,_| \_/ \__,_|
    //                                                   |__/                  
    // constuctor: public TERMCTRL()
    // not-specified static WHITEONRED
    // not-specified static WHITEONBLACK
    // not-specified static BG_DEF
    // not-specified static BG_BLACK
    // not-specified static BG_RED
    // not-specified static BG_GREEN
    // not-specified static BG_YELLOW
    // not-specified static BG_BLUE
    // not-specified static BG_MAGENTA
    // not-specified static BG_PURPLE
    // not-specified static BG_CYAN
    // not-specified static BG_LGRAY
    // not-specified static BG_DGRAY
    // not-specified static BG_LRED
    // not-specified static BG_LREEN
    // not-specified static BG_LYELLOW
    // not-specified static BG_LBLUE
    // not-specified static BG_LMAGENTA
    // not-specified static BG_PINK
    // not-specified static BG_LCYAN
    // not-specified static BG_WHITE
    // not-specified static FG_DEF
    // not-specified static FG_BLACK
    // not-specified static FG_RED
    // not-specified static FG_GREEN
    // not-specified static FG_YELLOW
    // not-specified static FG_BLUE
    // not-specified static FG_WHITE
    // not-specified static FG_RESET
    import java.util.*;
    
    public class TERMCTRL {
    
        static final String WHITEONRED = "\033[1;97;41m";
        static final String WHITEONBLACK = "\033[1;97;40m";
        static final int BG_DEF = 49;
        static final int BG_BLACK = 30;
        static final int BG_RED = 41;
        static final int BG_GREEN = 42;
        static final int BG_YELLOW = 43;
        static final int BG_BLUE = 44;
        static final int BG_MAGENTA = 45;
        static final int BG_PURPLE = 45;
        static final int BG_CYAN = 46;
        static final int BG_LGRAY = 47;
        static final int BG_DGRAY = 100;
        static final int BG_LRED = 101;
        static final int BG_LREEN = 102;
        static final int BG_LYELLOW = 103;
        static final int BG_LBLUE = 104;
        static final int BG_LMAGENTA = 105;
        static final int BG_PINK = 105;
        static final int BG_LCYAN = 106;
        static final int BG_WHITE = 107;
    
        static final int FG_DEF = 39;
        static final int FG_BLACK = 30;
        static final int FG_RED = 31;
        static final int FG_GREEN = 32;
        static final int FG_YELLOW = 33;
        static final int FG_BLUE = 34;
        static final int FG_WHITE = 97;
        static final int FG_RESET = 0;
    
        public TERMCTRL() {
            super();
        }
        static String setTermString(int FG, int BG) {
            return "\033[" + FG + "m";
    
        }
        static void setTerm(int FG, int BG) {
            System.out.print("\033[1;" + FG + ";" + BG + "m");
        }
    }
    
    
    
    // https://askubuntu.com/questions/558280/changing-colour-of-text-and-background-of-terminal
    
    // To set both the foreground and background colours at once, use ther form "\033[S;FG;BGm". 
    // For example: echo -e "\e[1;97;41m" (bold white foreground on red background)
    
