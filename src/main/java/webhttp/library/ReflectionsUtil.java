package webhttp.library;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

public class ReflectionsUtil {

    public static String getReturnTypeAsString(Function<?, ?> obj) {
        try {
            Method method = obj.getClass().getMethod("apply", Object.class);
            Type returnType = method.getGenericReturnType();

            if(returnType instanceof ParameterizedType type) {
                return type.getRawType().getTypeName();
            } else {
                return returnType.getTypeName();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
