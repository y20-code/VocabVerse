package stringtest;


public class RegularMethod {
    public static void main(String[] args) {
        String str = "  World,hello";
        System.out.println(str.length());
        System.out.println(str.charAt(1));
        System.out.println(str.isEmpty());
        System.out.println(str.equals("张三"));
        System.out.println(str.equals("world"));
        System.out.println(str.equalsIgnoreCase("world"));
        System.out.println(str.split(",")[0]);
        System.out.println(str.split(",")[1]);
        System.out.println(str.trim());
        System.out.println(str.substring(0,5));
        System.out.println(str.replace('l','a'));
        System.out.println(str.startsWith("  Wor"));
        System.out.println(str.endsWith("lo"));
        System.out.println(str.contains("Wor"));
        System.out.println(str.indexOf("l"));
        System.out.println(str.lastIndexOf("l"));
        System.out.println(str.toUpperCase());
        System.out.println(str.toLowerCase());
        System.out.println();
    }
}
