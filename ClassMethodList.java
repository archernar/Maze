import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
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
        Method[] methods = tClass.getDeclaredMethods();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            int m = field.getModifiers();
            String szModifier = "not-specified";
            if (Modifier.isPublic(m))  szModifier = "public";
            if (Modifier.isPrivate(m)) szModifier = "private";
            if (Modifier.isProtected(m)) szModifier = "protected";
            System.out.println(szModifier + " " + (""+field).split(" ",5)[0] + " " + field.getName());
        }

        for (int i = 0; i < methods.length; i++) if (Modifier.isStatic(methods[i].getModifiers()) && Modifier.isPrivate(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
        for (int i = 0; i < methods.length; i++) if (Modifier.isStatic(methods[i].getModifiers()) && Modifier.isPublic(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
        for (int i = 0; i < methods.length; i++) if (Modifier.isStatic(methods[i].getModifiers()) && Modifier.isProtected(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
        for (int i = 0; i < methods.length; i++) if (!Modifier.isStatic(methods[i].getModifiers()) && Modifier.isPrivate(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
        for (int i = 0; i < methods.length; i++) if (!Modifier.isStatic(methods[i].getModifiers()) && Modifier.isPublic(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
        for (int i = 0; i < methods.length; i++) if (!Modifier.isStatic(methods[i].getModifiers()) && Modifier.isProtected(methods[i].getModifiers()) )  System.out.println("" + methods[i]);
    }
}
