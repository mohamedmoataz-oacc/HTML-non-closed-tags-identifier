package HTMLtags;

public class Main {
    public static void main(String[] args) {

        TagsIdentifier identifier = new TagsIdentifier("test.html");
        System.out.println(identifier.toString());

        Deque<Integer> q = new Deque<Integer>(10);
        q.addFirst(3);
        q.addFirst(5);
        q.removeLast();
        q.addLast(7);
        System.out.println(q.toString());
        q.addFirst(4);
        System.out.println(q.toString());
        System.out.println("Last: " + q.last());
        System.out.println("First: " + q.first());

    }
}
