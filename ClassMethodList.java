import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class ClassMethodList {

    public static void main(String args[]) throws IOException {
        Object t = null;
        String szClassName = "";
        if ( args.length > 0) {
            szClassName = args[0];
        }

        if ( szClassName.length() == 0) szClassName = "Room";

        try {
            Class<?> c = Class.forName(szClassName);

 		Constructor[] constructors = c.getConstructors();
 		for (int i = 0; i < constructors.length; i++) {
 			System.out.println("constuctor: " + constructors[i]);
 		}

// 		Constructor[] declaredConstructors = c.getDeclaredConstructors();
// 		for (int i = 0; i < declaredConstructors.length; i++) {
// 			System.out.println("declared constructor: " + declaredConstructors[i]);
// 		}




            Constructor<?> cons = c.getConstructor();
            t = cons.newInstance();
        }
        catch (java.lang.NoSuchMethodException e ) {
            System.out.println(szClassName + "() constructor not found");
            System.exit(1);
        }
        catch( Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        Class tClass = t.getClass();
        Method[] methods = tClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println("public method: " + methods[i]);
        }
    }
}
