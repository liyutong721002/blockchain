package edu.grinnell.csc207.blockchain;
import java.util.Arrays;
/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] hash;
    
    public Hash(byte[] data){
        hash = data;
    }
    public byte[] getData(){
        return hash;
    }
    public boolean isValid(){
        if (hash.length < 3){
            return false;
        }else {
            for (int i = 0; i < 3; i++){
                if (hash[i] != 0){
                    return false;
                }
            }
        }
        return true;
    }
    public String toString(){
        String result = "";
        for (int i = 0; i < hash.length; i++) {
            result += String.format("%02X ", Byte.toUnsignedInt(hash[i]));
        }
        return result;
    }
    public boolean equals(Object other){
        if (other instanceof Hash){
            Hash o = (Hash) other;
            return Arrays.equals(this.hash, o.hash);
        }
        return false;
    }
}
