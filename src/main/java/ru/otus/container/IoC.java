package ru.otus.container;


import java.util.HashMap;
import java.util.Map;

//Статический фасад, точка входа
public class IoC {

    static ThreadLocal<Scope> currentScope;
    static ThreadLocal<Map<String, Scope>> catalogue;


    public static <T> T resolve(String key, Object... args) {
        Scope scope = getCurrentScope();
        return scope.resolve(key, args);
    }


    public static Scope getCurrentScope() {
        return currentScope.get();
    }

    public static void init(Scope root) {
        currentScope = ThreadLocal.withInitial(() -> new Scope(new HashMap<>(), root));
        catalogue = ThreadLocal.withInitial(HashMap::new);
    }
}
