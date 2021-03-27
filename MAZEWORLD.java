
    private static boolean oneoutoften() {
        Random random = new Random();
        int r = random.nextInt(10 + 1 - 1) + 1;
        return (r==1) ? true : false;
    }
    private static int randDoor() {
        Random random = new Random();
        return random.nextInt(5 + 1 - 0) + 0;
    }
    private static int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    private static int nextdoor(int d) {
        return ( (d+1) > 5) ? 0 : d+1;
    }
