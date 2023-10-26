import java.util.LinkedList;

public class HotPotato {
    public static DoublyLinkedList<Integer> playWithDoublyLinkedList(int numberOfPlayers, int lengthOfPass){
        DoublyLinkedList<Integer> playerList = new DoublyLinkedList<>();
       //playerList.iterator(0);
        for(int i = 0; i < numberOfPlayers; i++){
            playerList.add(i + 1);
        }
        DoublyLinkedList<Integer> elimOrder = new DoublyLinkedList<>();
        int pos = 0;
        for (int i = 0; i < numberOfPlayers; i++){
            pos += lengthOfPass;
            pos = pos % playerList.size();
            elimOrder.add(playerList.get(pos));
            playerList.remove(pos);
        }
        return elimOrder;
    }

    public static LinkedList<Integer> playWithLinkedList(int numberOfPlayers, int lengthOfPass){
        LinkedList<Integer> playerList = new LinkedList<>();
        for(int i = 0; i < numberOfPlayers; i++){
            playerList.add(i + 1);
        }
        LinkedList<Integer> elimOrder = new LinkedList<>();
        int pos = 0;
        for (int i = 0; i < numberOfPlayers; i++){
            pos += lengthOfPass;
            pos = pos % playerList.size();
            elimOrder.add(playerList.remove(pos));
        }
        return elimOrder;
    }

    public static void main(String... args){
        // in both methods, the list is the order in which the players are eliminated
        // the last player (i.e., the last element in the returned list) is the winner
        System.out.println(playWithDoublyLinkedList(5, 0)); //expected output: [1, 2, 3, 4, 5]
        System.out.println(playWithLinkedList(5, 1));       //expected output: [2, 4, 1, 5, 3]
    }
}