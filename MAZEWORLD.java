import java.util.*;

public class MAZEWORLD {
        
    public static boolean oneoutoften() {
        Random random = new Random();
        int r = random.nextInt(10 + 1 - 1) + 1;
        return (r==1) ? true : false;
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
}
