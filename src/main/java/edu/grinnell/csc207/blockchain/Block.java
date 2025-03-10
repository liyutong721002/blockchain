package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {

    int number;
    int data;
    Hash prehash;
    long nonce;
    Hash ownhash;

    /*
     creates a new block from the specified parameters, performing the mining operation 
    to discover the nonce and hash for this block given these parameters.
     */
    public static byte[] calculateHash(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        md.update(msg.getBytes());
        byte[] hash = md.digest();
        return hash;
    }

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        number = num;
        data = amount;
        prehash = prevHash;
        long newnonce = 0;
        while (true) {
            MessageDigest md = MessageDigest.getInstance("sha-256");
            byte[] numberByte = ByteBuffer.allocate(4).putInt(num).array();
            md.update(numberByte);
            byte[] amountByte = ByteBuffer.allocate(4).putInt(amount).array();
            md.update(amountByte);
            if (num != 0) {
                md.update(prehash.getData());
            }
            byte[] nonceByte = ByteBuffer.allocate(8).putLong(newnonce).array();
            md.update(nonceByte);
            Hash hash = new Hash(md.digest());
            if (hash.isValid()) {
                ownhash = hash;
                nonce = newnonce;
            }
            nonce++;
        }
    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        number = num;
        data = amount;
        prehash = prevHash;
        while (true) {
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
            Hash hash = new Hash(md.digest());
            if (hash.isValid()) {
                ownhash = hash;
                this.nonce = nonce;
            }
            this.nonce++;
        }
    }

    public int getNum() {
        return number;
    }

    public int getAmount() {
        return data;
    }

    public long getNonce() {
        return nonce;
    }

    public Hash getPrevHash() {
        return prehash;
    }

    public Hash getHash() {
        return ownhash;
    }

    public String toString() {
        return ("Block <" + number + "> (Amount: <" + data + ">, Nonce: <" + nonce + ">, prevHash: <" + prehash.toString() + ">, hash: <" + ownhash.toString() + ">)");
    }
}
