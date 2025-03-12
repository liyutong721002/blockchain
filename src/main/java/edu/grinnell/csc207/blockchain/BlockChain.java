package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    private static class Node {

        public Block block;
        public Node next;

        public Node(Block block) {
            this.block = block;
            this.next = null;
        }
    }

    private Node first;
    private Node last;

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block block = new Block(0, initial, null);
        Node node = new Node(block);
        first = node;
        last = node;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        Hash preHash = last.block.getHash();
        Block newBlock = new Block(last.block.getNum()+1, amount, preHash);
        return newBlock;
    }

    public int getSize() {
        return last.block.getNum() + 1;
    }

    public void append(Block blk) {
        if (!blk.getPrevHash().equals(last.block.getHash()) || blk.getNum() != last.block.getNum() + 1) {
            throw new IllegalArgumentException("Invalid block");
        }
        Node newNode = new Node(blk);
        last.next = newNode;
        last = newNode;
    }

    public boolean removeLast() {
        if (last.block.getNum() == 0) {
            return false;
        }
        Node current = first;
        while (current.next != last) {
            current = current.next;
        }
        current.next = null;
        last = current;
        return true;
    }

    public Hash getHash() {
        return last.block.getHash();
    }

    public boolean isValidBlockChain() {
        Node current = first.next;
        int aliceBalance = first.block.getAmount();
        int bobBalance = 0;
        while (current != null) {
            aliceBalance += current.block.getAmount();
            bobBalance -= current.block.getAmount();
            if (aliceBalance < 0 || bobBalance < 0) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    public String printBalances() {
        Node current = first.next;
        int aliceBalance = first.block.getAmount();
        int bobBalance = 0;
        while (current != null) {
            aliceBalance += current.block.getAmount();
            bobBalance -= current.block.getAmount();
            current = current.next;
        }
        System.out.println("Alice: " + aliceBalance + ", Bob: " + bobBalance);
        return "Alice: " + aliceBalance + ", Bob: " + bobBalance;
    }

    public String toString() {
        Node current = first;
        String result = "";
        while (current != null) {
            result += current.block.toString();
            result += "\n";
            current = current.next;
        }
        return result;
    }
}
