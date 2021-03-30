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
