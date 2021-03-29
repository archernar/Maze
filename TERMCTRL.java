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

