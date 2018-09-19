import java.util.ArrayList;

public class Node {
    private char value;
    private ArrayList<Node> nodes;

    public Node() {
        nodes = new ArrayList<>();
    }

    public Node(char value) {
        this.value = value;
        nodes = new ArrayList<>();
    }

    public Node(char value, String palavra) {
        this.value = value;
        this.nodes = new ArrayList<>();
        this.setNodes(palavra);
    }

    public ArrayList<String> getWords(String palavra){
        ArrayList<String> ret = new ArrayList<>();


        if (!palavra.equals("")){
            boolean a = true;
            for (Node node : nodes) {
                if (node.getValue() == palavra.charAt(0)) {
                    ret.addAll(node.getWords(palavra.substring(1)));
                    if (!ret.get(0).equals("erro")) {
                        for (int j = 0; j < ret.size(); j++) {
                            ret.set(j, value + ret.get(j));
                        }
                    }
                    a = false;
                    break;
                }
            }
            if(a){
                ret.add("erro");
            }
        }else{
            for (Node node : nodes) {
                if (node.getValue() != '#') {
                    ret.addAll(node.getWords(""));
                }else {
                    ret.add(Character.toString('#'));
                }
            }
            for (int j = 0; j < ret.size(); j++) {
                ret.set(j, value + ret.get(j));
            }
        }
        return ret;
    }

    public void setNodes(String palavra) {
        if (!palavra.equals("")){
            boolean a = true;
            for (Node node : nodes) {
                if (node.getValue() == palavra.charAt(0)) {
                    node.setNodes(palavra.substring(1));
                    a = false;
                    break;
                }
            }
            if (a){
                nodes.add(new Node(palavra.charAt(0), palavra.substring(1)));
            }
        }else{
            nodes.add(new Node('#'));
        }
    }

    public String getNodes(){
        StringBuilder ret = new StringBuilder();

        ret.append(this.value);
        for (Node node : nodes) {
            ret.append("[").append(node.getNodes()).append("]");

        }

        return ret.toString();
    }

    public char getValue() {
        return value;
    }
}
