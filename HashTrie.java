import java.util.*;

// Node class represents a single node in the Trie.
class Node {
    HashMap<Character, Node> reference;
    boolean flag;
    
    public Node() {
        reference = new HashMap<Character, Node>();
        for (char c = 'a'; c <= 'z'; c++) {
            reference.put(c, null);
        }
        flag = false;
    }
}

// TelephoneDirectory class using Trie for contact names.
class TelephoneDirectory {
    HashMap<String, Long> contact;
    Trie trie;
    
    public TelephoneDirectory() {
        contact = new HashMap<String, Long>();
        trie = new Trie();
    }
    
    public void addContact(String name, Long number) {
        contact.put(name.toLowerCase(), number);
        trie.insert(name.toLowerCase());
        System.out.println("Contact added successfully!");
    }
    
    public void searchAndShowNumbers(String entry) {
        trie.searchContacts(entry.toLowerCase(), contact);
    }
}

// Trie class represents the Trie structure.
class Trie {
    Node root;
    
    public Trie() {
        root = new Node();
    }
    
    public void insert(String contactName) {
        Node node = root;
        for (int i = 0; i < contactName.length(); i++) {
            char currentChar = contactName.charAt(i);
            Node nextNode = node.reference.get(currentChar);
            if (nextNode == null) {
                nextNode = new Node();
                node.reference.put(currentChar, nextNode);
            }
            node = nextNode;
            if (i == contactName.length() - 1) {
                node.flag = true;
            }
        }
    }
    
    public void displayRelatedContacts(Node current, String prefix, ArrayList<String> relatedContacts) {
        if (current.flag) {
            relatedContacts.add(prefix);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            Node nextNode = current.reference.get(c);
            if (nextNode != null) {
                displayRelatedContacts(nextNode, prefix + c, relatedContacts);
            }
        }
    }
    
    public void searchContacts(String entry, HashMap<String, Long> contact) {
        Node current = root;
        String prefix = "";
        for (int i = 0; i < entry.length(); i++) {
            prefix += entry.charAt(i);
            char currentChar = prefix.charAt(i);
            current = current.reference.get(currentChar);
            if (current == null) {
                System.out.println("\nNo contacts were found with the given prefix: " + entry);
                return;
            }
        }
        
        ArrayList<String> relatedContacts = new ArrayList<>();
        displayRelatedContacts(current, prefix, relatedContacts);
        
        if (relatedContacts.isEmpty()) {
            System.out.println("\nNo contacts were found with the given prefix: " + entry);
        } else {
            System.out.println("\n--- Search Results ---");
            for (String contactName : relatedContacts) {
                System.out.println("Contact Name: " + contactName + " | Phone Number: " + contact.get(contactName));
            }
            System.out.println("----------------------");
        }
    }
}

// Driver class to test the telephone directory.
public class HashTrie {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TelephoneDirectory directory = new TelephoneDirectory();
        
        System.out.println("========================================");
        System.out.println("   WELCOME TO TELEPHONE DIRECTORY");
        System.out.println("========================================");
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter contact name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    long number = scanner.nextLong();
                    scanner.nextLine(); // Consume newline
                    directory.addContact(name, number);
                    break;
                    
                case 2:
                    System.out.print("Enter search prefix: ");
                    String searchEntry = scanner.nextLine();
                    directory.searchAndShowNumbers(searchEntry);
                    break;
                    
                case 3:
                    System.out.println("\nThank you for using Telephone Directory!");
                    scanner.close();
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}