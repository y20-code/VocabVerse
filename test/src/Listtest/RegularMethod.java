package Listtest;

import java.util.ArrayList;
import java.util.List;

public class RegularMethod {
    public static void main(String[] args) {
        List<String> name = new ArrayList<>();
        name.add("a");
        name.add("b");
        System.out.println(name.add("c"));
        System.out.println(name.size());
        System.out.println(name.isEmpty());
        System.out.println(name.remove("a"));
        System.out.println(name.contains("b"));
//        name.clear();
        System.out.println(name.toArray());
        System.out.println(name.get(0));
        System.out.println(name.set(1, "e"));
        System.out.println(name.remove(0));
        System.out.println(name.indexOf("e"));
        System.out.println(name.lastIndexOf("e"))
        ;
    }
}
