package edu.grinnell.csc207.blockchain;

import java.util.Arrays;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] hash;

    /**
     * construct a hash object with given byte array
     * @param data the byte array to be used to hash 
     */
    public Hash(byte[] data) {
        hash = data;
    }

    /**
     * get the data of the hash
     * @return a byte array which is the data of the hash
     */
    public byte[] getData() {
        return hash;
    }

    /**
     * check if the hash is valid
     * @return true if the hash is valid
     */
    public boolean isValid() {
        if (hash.length < 3) {
            return false;
        } else {
            for (int i = 0; i < 3; i++) {
                if (hash[i] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * convert the hash to a hexadecimal string representation of the hash
     * @return a string of the hexadecimal string representation of the hash.
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < hash.length; i++) {
            result += String.format("%02x", Byte.toUnsignedInt(hash[i]));
        }
        return result;
    }

    /**
     * compare the hash to an object 
     * @param other the object to compare with
     * @return true if other is a hash and has the same data
     */
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(this.hash, o.hash);
        }
        return false;
    }
}
