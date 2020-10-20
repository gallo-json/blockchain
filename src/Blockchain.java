import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Blockchain {
    private ArrayList<Block> blockchain = new ArrayList<Block>();
    private ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();
    private int miningReward = 100;
    private int difficulty = 2;

    public Blockchain() {
        byte[] genesisHash = new byte[0];
        // Genesis block
        blockchain.add(new Block(new Transaction(0, "Genesis", "Genesis", new Date()), genesisHash));
    }

    public Block getBlock(int i) {
        return blockchain.get(i);
    }
    
    private Block getLatestBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public void addBlock(Transaction transaction) {
        Block newBlock = new Block(transaction, getLatestBlock().getHash());
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public void addBlock(ArrayList<Transaction> transactions) {
        Block newBlock = new Block(transactions, getLatestBlock().getHash());
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public void minePendingTransactions(String minerAddress) {
        Block minedBlock = new Block(pendingTransactions, getLatestBlock().getHash());
        minedBlock.mineBlock(difficulty);
        System.out.println("Block successfully mined.")
        blockchain.add(newBlock);
        pendingTransactions.add(new Transaction(100, "System", minerAddress, new Date()));
    }

    public void createTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public int getBalanceOf(String address) {
        return 0;
    }

    public boolean isChainValid() {
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);
            
            try {
                if (!Arrays.equals(currentBlock.getHash(), currentBlock.calculateHash()) || !Arrays.equals(previousBlock.getHash(), previousBlock.calculateHash())) return false;
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }

    public void printAsString() {
        for (Block block : blockchain) {
            block.printAsString();
        }
    }
}