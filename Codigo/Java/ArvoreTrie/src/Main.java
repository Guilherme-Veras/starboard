import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Node nodes = new Node();

        nodes.setNodes("abc");
        nodes.setNodes("acd");
        nodes.setNodes("ef");
        nodes.setNodes("efe");
        nodes.setNodes("efa");
        nodes.setNodes("ege");

        ArrayList<String> res = nodes.getWords("a");
        for (int i = 0; i < res.size(); i++) {//tirando # do fim de cada palavra
            res.set(i, res.get(i).substring(0, res.get(i).length() - 1));
        }
        res.sort(Comparator.comparing(String::length));
        System.out.println(res);
    }
}
