import java.util.ArrayList;
class Test {
    public static void main(String[] args) {
        //Id itemID = new Id();
        ArrayList<Object> list = new ArrayList<>();
        list.add("hello");
        list.add(1);
        list.add(true);
        list.add(1.0);
        System.out.println(list instanceof ArrayList);
    }
}
