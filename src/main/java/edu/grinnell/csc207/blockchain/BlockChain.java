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

    /**
     * construct a new blockchain with the given initial amount of money
     * @param initial an integer which is the initial amount of money 
     * @throws NoSuchAlgorithmException if hashing algorithm is not available 
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block block = new Block(0, initial, null);
        Node node = new Node(block);
        first = node;
        last = node;
    }

    /**
     * mine a new block based on the amount of money 
     * @param amount an integer of the amount of money transferred
     * @return a new block mined
     * @throws NoSuchAlgorithmException if hashing algorithm is not available 
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Hash preHash = last.block.getHash();
        Block newBlock = new Block(last.block.getNum() + 1, amount, preHash);
        return newBlock;
    }

    /**
     * get the number of blocks
     * @return an integer of the size of the blockchain
     */
    public int getSize() {
        return last.block.getNum() + 1;
    }

    /**
     * append a block to the end of the block chain 
     * @param blk a block to be appended
     */
    public void append(Block blk) {
        if (!blk.getPrevHash().equals(last.block.getHash()) 
                || blk.getNum() != last.block.getNum() + 1) {
            throw new IllegalArgumentException("Invalid block");
        }
        Node newNode = new Node(blk);
        last.next = newNode;
        last = newNode;
    }

    /**
     * remove the last block
     * @return true if the block can be removed
     */
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

    /**
     * get the hash of the last block 
     * @return the hash of the last block
     */
    public Hash getHash() {
        return last.block.getHash();
    }

    /**
     * check if the block chain is valid
     * @return true if the block chain is valid
     */
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

    /**
     * calculate the balances of Alice and Bob and print them
     * @return a string of the balances
     */
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

    /**
     * convert the block chain to a string
     * @return a string representation of the block chain
     */
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
