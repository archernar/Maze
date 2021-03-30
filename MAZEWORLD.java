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
