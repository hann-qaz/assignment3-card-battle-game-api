package utils;

import java.lang.reflect.*;

/**
 * Reflection Utility - demonstrates Runtime Type Information (RTTI)
 */
public class ReflectionUtils {

    /**
     * Inspects a class and prints its structure
     */
    public static void inspectClass(Object obj) {
        if (obj == null) {
            System.out.println("❌ Object is null");
            return;
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\n🔍 ===== REFLECTION INSPECTION =====");
        System.out.println("📦 Class Name: " + clazz.getName());
        System.out.println("📦 Simple Name: " + clazz.getSimpleName());
        System.out.println("📦 Package: " + clazz.getPackage().getName());

        // Parent class
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            System.out.println("👪 Superclass: " + superClass.getSimpleName());
        }

        // Interfaces
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            System.out.println("\n🔌 Implemented Interfaces:");
            for (Class<?> iface : interfaces) {
                System.out.println("  - " + iface.getSimpleName());
            }
        }

        // Fields
        System.out.println("\n📊 Fields:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("  - " + Modifier.toString(field.getModifiers()) +
                    " " + field.getType().getSimpleName() +
                    " " + field.getName());
        }

        // Methods
        System.out.println("\n⚙️ Methods:");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("  - " + Modifier.toString(method.getModifiers()) +
                    " " + method.getReturnType().getSimpleName() +
                    " " + method.getName() + "()");
        }

        System.out.println("🔍 ===== END OF INSPECTION =====\n");
    }

    /**
     * Gets the value of a field using reflection
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("❌ Error accessing field: " + e.getMessage());
            return null;
        }
    }

    /**
     * Invokes a method using reflection
     */
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] paramTypes, Object[] args) {
        try {
            Method method = obj.getClass().getMethod(methodName, paramTypes);
            return method.invoke(obj, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.err.println("❌ Error invoking method: " + e.getMessage());
            return null;
        }
    }
}