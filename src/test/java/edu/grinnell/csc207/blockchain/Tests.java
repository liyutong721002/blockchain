package edu.grinnell.csc207.blockchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    // TODO: fill me in with tests that you write for this project!

    @Test
    @DisplayName("Placeholder Test")
    public void placeholderTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testBlockCreation() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        assertEquals("Block 0 (Amount: 300, Nonce: 9324351, prevHash: null, hash: 000000201f6c32c24b52b8a5b7d664af23e7db950af8867dbe800eb5c40c30a7)\n", 
                blockchain.toString()); 
        assertEquals(true, blockchain.isValidBlockChain());
    }
    
    @Test
    public void testBlockMine() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        assertEquals(2016357, blockchain.mine(-150).getNonce()); 
    }
    
    @Test
    public void testBlockAppend() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        blockchain.append(blockchain.mine(-150));
        assertEquals("000000d744da56bb0f9a87737a7491b557d49f502d0e375678ca160143986c26", blockchain.getHash().toString()); 
    }
    
    @Test
    public void testBlockPositive() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        blockchain.append(blockchain.mine(-150));
        blockchain.append(blockchain.mine(-100));
        blockchain.append(blockchain.mine(50));
        assertEquals("000000b96183e5caab9534bfded11fbf022d03de1cbf2a08bbdbed24882c9d4a", blockchain.getHash().toString()); 
    }
    
    @Test
    public void testBlockCheck() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        blockchain.append(blockchain.mine(-150));
        blockchain.append(blockchain.mine(-100));
        blockchain.append(blockchain.mine(50));
        blockchain.append(blockchain.mine(100));
        blockchain.append(blockchain.mine(-150));
        assertEquals(true, blockchain.isValidBlockChain()); 
    }
    
    @Test
    public void testBlockBalance() throws NoSuchAlgorithmException{
        BlockChain blockchain = new BlockChain(300);
        blockchain.append(blockchain.mine(-150));
        blockchain.append(blockchain.mine(-100));
        blockchain.append(blockchain.mine(50));
        blockchain.append(blockchain.mine(100));
        blockchain.append(blockchain.mine(-150));
        assertEquals("Alice: 50, Bob: 250", blockchain.printBalances()); 
    }
}
