package blockchain;

import java.util.Date;
import java.util.Scanner;
import java.security.KeyPair;

import blockchain.core.*;
import blockchain.network.Peer;

public class Miner extends Peer {
    private Blockchain pepegaCoin;
    private SigningKeys signingKeys;
    private Scanner input = new Scanner(System.in);
    private KeyPair myKeys;
    private String myWalletAddress;

    public Miner(String name, boolean receiver) throws Exception {
        super(name, receiver);
        pepegaCoin = new Blockchain();
        signingKeys = new SigningKeys();
        
        input.useDelimiter("\n");

        System.out.println("Your designated public and private keys. Do not share your private key with anyone!");
        myKeys = signingKeys.generate();

        System.out.println();

        myWalletAddress = signingKeys.toString(myKeys.getPublic());
        super.send(pepegaCoin.getBlock(0).getJSON());
    }

    public void start() {
        try {
            System.out.println("Creating transaction...");
            System.out.println("Sender: " + myWalletAddress + " (this is your address)");
            System.out.print("Reciever: "); String receiver = input.nextLine();
            System.out.print("Amount: "); int amount = input.nextInt();
    
            System.out.print("Sign transaction? (y/n): "); char isSign = input.next().charAt(0);
            if (isSign == 'y') {
                Transaction tx = new Transaction(amount, myWalletAddress, receiver);
                tx.signTransaction(signingKeys, myKeys);
                System.out.println();
                pepegaCoin.addTransaction(tx);
                tx.printAsString();  
                System.out.println();   
            } else {
                System.out.println("Transaction cancelled.\n");
            }

            while (true) {
                System.out.println("What do you want to do?");
                System.out.println("Start miner and mine pending transactions into a block (a)");
                System.out.println("Make another transaction (b)");
                System.out.println("View blockchain (c)");
                System.out.println("View your balance (d)");
                System.out.println("Quit blockchain (q)");
                System.out.print("Your choice: "); char choice = input.next().charAt(0);
                
                switch (choice) {
                    case 'a':
                        System.out.println("\nStarting the miner...");
                        pepegaCoin.minePendingTransactions(myWalletAddress);
                        System.out.println();
                        super.send(pepegaCoin.getLatestBlock().getJSON());
                        break;
                    case 'b':
                        System.out.println("Creating transaction...");
                        System.out.println("Sender: " + myWalletAddress + " (this is your address)");
                        System.out.print("Reciever: "); String receiver1 = input.next();
                        input.nextLine();
                        System.out.print("Amount: "); int amount1 = input.nextInt();

                        System.out.print("Sign transaction? (y/n): "); char isSign1 = input.next().charAt(0);
                        if (isSign1 == 'y') {
                            Transaction tx = new Transaction(amount1, myWalletAddress, receiver1);
                            tx.signTransaction(signingKeys, myKeys);
                            System.out.println();
                            pepegaCoin.addTransaction(tx);
                            tx.printAsString(); 
                            System.out.println();  
                        } else {
                            System.out.println("Transaction cancelled.\n");
                        }
                        break;
                    case 'c':
                        System.out.println();
                        pepegaCoin.printAsString(); 
                        System.out.println("Chain valid: " + pepegaCoin.isChainValid() + "\n");
                        break;
                    case 'd':
                        System.out.println("\nYour balance: " + pepegaCoin.getBalance(myWalletAddress));
                        System.out.println();
                        break;
                    case 'q':
                        System.out.println("Quitting...");
                        System.exit(0);
                    default:
                        System.out.println("That is not a valid option.\n");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}