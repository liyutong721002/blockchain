package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.IOException;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    private static void Mine(BlockChain blockchain, Scanner scanner) throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        int amount = scanner.nextInt();
        Block minedBlock = blockchain.mine(amount);
        System.out.println("amount = " + amount + ", nonce = " + minedBlock.getNonce());
    }

    private static void Append(BlockChain blockchain, Scanner scanner) throws NoSuchAlgorithmException {
        System.out.print("Amount transferred? ");
        int amount = scanner.nextInt();
        System.out.print("Nonce? ");
        int nonce = scanner.nextInt();
        Block block = new Block(blockchain.getSize(), amount, blockchain.getHash(), nonce);
        blockchain.append(block);
    }

    /**
     * The main entry point for the program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java BlockChainDriver <initial amount>");
        }
        int initialAmount = Integer.parseInt(args[0]);
        if (initialAmount < 0) {
            throw new IllegalArgumentException("Invalid initial amount");
        }
        BlockChain blockchain = new BlockChain(initialAmount);
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;

        while (!quit) {
            System.out.println(blockchain.toString());
            System.out.print("Command? ");

            String command = scanner.next();
            switch (command) {
                case "mine":
                    Mine(blockchain, scanner);
                    break;
                case "append":
                    Append(blockchain, scanner);
                    break;
                case "remove":
                    if (!blockchain.removeLast()) {
                        System.out.println("Invalid remove.");
                    }
                    break;
                case "check":
                    if (blockchain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid!");
                    }
                    break;
                case "report":
                    blockchain.printBalances();
                    break;
                case "help":
                    System.out.print("Valid commands:\n"
                            + "    mine: discovers the nonce for a given transaction\n"
                            + "    append: appends a new block onto the end of the chain\n"
                            + "    remove: removes the last block from the end of the chain\n"
                            + "    check: checks that the block chain is valid\n"
                            + "    report: reports the balances of Alice and Bob\n"
                            + "    help: prints this list of commands\n"
                            + "    quit: quits the program\n");
                    break;
                case "quit":
                    scanner.close();
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid command. Type 'help' for a list of commands.");
            }
        }
        scanner.close();
    }

}
