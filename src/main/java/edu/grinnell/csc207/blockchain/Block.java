package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    private int number;
    private int data;
    private Hash prehash;
    private long nonce;
    private Hash ownhash;

    /**
     * creates a new block from the specified parameters, performing the mining operation 
     * to discover the nonce and hash for this block given these parameters.
     * @param num the number of block
     * @param amount the amount transferred
     * @param prevHash the hash of the previous block
     * @throws NoSuchAlgorithmException if the hashing algorithm is not available
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        number = num;
        data = amount;
        prehash = prevHash;
        long newnonce = 0;
        boolean ifMine = false;
        while (!ifMine) {
            MessageDigest md = MessageDigest.getInstance("sha-256");
            byte[] numberByte = ByteBuffer.allocate(4).putInt(num).array();
            md.update(numberByte);
            byte[] amountByte = ByteBuffer.allocate(4).putInt(amount).array();
            md.update(amountByte);
            if (prehash != null) {
                md.update(prehash.getData());
            }
            byte[] nonceByte = ByteBuffer.allocate(8).putLong(newnonce).array();
            md.update(nonceByte);
            Hash hash = new Hash(md.digest());
            if (hash.isValid()) {
                ownhash = hash;
                nonce = newnonce;
                ifMine = true;
            }
            newnonce++;
        }
    }

    /**
     * creates a new block from the specified parameters, performing the mining operation 
     * to discover the nonce and hash for this block given these parameters.
     * @param num the number of the block 
     * @param amount the amount transferred
     * @param prevHash the hash of the previous block
     * @param nonce the nonce to be used for hashing
     * @throws NoSuchAlgorithmException if the algorithm is not available
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        number = num;
        data = amount;
        prehash = prevHash;
        this.nonce = nonce;
        MessageDigest md = MessageDigest.getInstance("sha-256");
        byte[] numberByte = ByteBuffer.allocate(4).putInt(num).array();
        md.update(numberByte);
        byte[] amountByte = ByteBuffer.allocate(4).putInt(amount).array();
        md.update(amountByte);
        if (num != 0) {
            md.update(prehash.getData());
        }
        byte[] nonceByte = ByteBuffer.allocate(8).putLong(nonce).array();
        md.update(nonceByte);
        ownhash = new Hash(md.digest());
    }

    /**
     * get the number of the block
     * @return an integer which is the number of the block
     */
    public int getNum() {
        return number;
    }

    /**
     * get the amount to be transferred
     * @return an integer of the amount to be transferred
     */
    public int getAmount() {
        return data;
    }

    /**
     * get the nonce value of the block
     * @return a long value which is the nonce of the block
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * get the hash of the previous block
     * @return a hash of the previous block
     */
    public Hash getPrevHash() {
        return prehash;
    }

    /**
     * get the hash of the block
     * @return a hash of the block
     */
    public Hash getHash() {
        return ownhash;
    }

    /**
     * convert the block to a string
     * @return a string representation of the block
     */
    public String toString() {
        if (prehash == null) {
            return ("Block " + number + " (Amount: " + data + ", Nonce: " + nonce 
                    + ", prevHash: null, hash: " + ownhash.toString() + ")");
        }
        return ("Block " + number + " (Amount: " + data + ", Nonce: " + nonce 
                + ", prevHash: " + prehash.toString() + ", hash: " + ownhash.toString() + ")");
    }
}
