import java.util.Scanner;

// Employee class represents an employee record
class Employee {
    int id;
    String name;
    String designation;

    public Employee(int id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }
}

// AVLNode class represents a node in the AVL Tree
class AVLNode {
    Employee employee;
    int height;
    AVLNode left, right;

    public AVLNode(Employee employee) {
        this.employee = employee;
        this.height = 1;
    }
}

// AVLTree class represents the AVL Tree data structure
class AVLTree {
    AVLNode root;

    // Method to get the height of a node
    int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Method to perform a right rotation
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Method to perform a left rotation
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Method to get the balance factor of a node
    int getBalance(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Method to insert an employee record into the AVL Tree
    AVLNode insert(AVLNode node, Employee employee) {
        if (node == null)
            return new AVLNode(employee);

        if (employee.id < node.employee.id)
            node.left = insert(node.left, employee);
        else if (employee.id > node.employee.id)
            node.right = insert(node.right, employee);
        else
            return node; // Duplicate keys not allowed

        // Update height of current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor and balance the node if needed
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && employee.id < node.left.employee.id)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && employee.id > node.right.employee.id)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && employee.id > node.left.employee.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && employee.id < node.right.employee.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Method to print the AVL Tree (inorder traversal)
    void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.println("ID: " + node.employee.id + ", Name: " + node.employee.name + ", Designation: " + node.employee.designation);
            inorder(node.right);
        }
    }

    // Method to search for an employee record by ID
    AVLNode search(AVLNode node, int id) {
        if (node == null || node.employee.id == id)
            return node;

        if (id < node.employee.id)
            return search(node.left, id);

        return search(node.right, id);
    }
}

// EmployeeManagementSystem class represents the main application
public class EmployeeManagementSystem {
    AVLTree avlTree = new AVLTree();

    // Method to add an employee record
    void addEmployee(int id, String name, String designation) {
        Employee employee = new Employee(id, name, designation);
        avlTree.root = avlTree.insert(avlTree.root, employee);
        System.out.println("Employee added successfully.");
    }

    // Method to search for an employee record by ID
    void searchEmployee(int id) {
        AVLNode node = avlTree.search(avlTree.root, id);
        if (node != null)
            System.out.println("Employee found - ID: " + node.employee.id + ", Name: " + node.employee.name + ", Designation: " + node.employee.designation);
        else
            System.out.println("Employee not found.");
    }

    // Method to display all employee records (inorder traversal)
    void displayEmployees() {
        System.out.println("Employee Records:");
        avlTree.inorder(avlTree.root);
    }

    // Main method to run the application
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("\nEmployee Management System");
			    System.out.println("1. Add Employee");
			    System.out.println("2. Search Employee");
			    System.out.println("3. Display All Employees");
			    System.out.println("4. Exit");
			    System.out.print("Enter your choice: ");

			    int choice = scanner.nextInt();
			    scanner.nextLine(); // Consume newline character

			    switch (choice) {
			        case 1:
			            System.out.print("Enter employee ID: ");
			            int id = scanner.nextInt();
			            scanner.nextLine(); // Consume newline character
			            System.out.print("Enter employee name: ");
			            String name = scanner.nextLine();
			            System.out.print("Enter employee designation: ");
			            String designation = scanner.nextLine();
			            system.addEmployee(id, name, designation);
			            break;
			        case 2:
			            System.out.print("Enter employee ID to search: ");
			            int searchId = scanner.nextInt();
			            scanner.nextLine(); // Consume newline character
			            system.searchEmployee(searchId);
			            break;
			        case 3:
			            system.displayEmployees();
			            break;
			        case 4:
			            System.out.println("Exiting application. Goodbye!");
			            System.exit(0);
			        default:
			            System.out.println("Invalid choice. Please enter a valid option.");
			    }
			}
		}
    }
}
