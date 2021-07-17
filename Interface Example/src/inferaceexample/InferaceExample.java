/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inferaceexample;

/**
 *
 * @author Muneeb Riaz
 */
import static java.lang.Double.parseDouble;
import java.util.Scanner;
import java.util.ArrayList;

interface Ipayment {

    double payment();
    public double[] total = new double[2];
}

abstract class Employee implements Ipayment {

    public String firstname;
    public String lastname;
    public String socialSecurityNumber;
    static double sum = 0;

    public void showEmployeeTotal() {
        System.out.println("Total Salary given to the employees : " + total[0]);
    }

    Employee(String firstName, String lastName, String socialSecurityNumber) {
        this.firstname = firstName;
        this.lastname = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String tostring() {
        return "Person name: " + firstname + lastname + "\n" + "social security number: " + socialSecurityNumber;
    }
}

class SalariedEmployee extends Employee {

    private final double weeklySalary;
    private double earned;

    SalariedEmployee(String firstName, String lastName, String socialSecurityNumber, double weeklySalary) {
        super(firstName, lastName, socialSecurityNumber);
        this.weeklySalary = weeklySalary;
    }

    @Override
    public double payment() {
        earned = weeklySalary;
        total[0] = total[0] + earned;
        return earned;
    }

    @Override
    public String tostring() {
        return "Salaried Employee: " + firstname + " " + lastname + "\n" + "social security number: " + socialSecurityNumber + "\n"
                + "Weekly Salary: " + weeklySalary + "\n" + "Earned: " + earned;
    }
}

class HourlyEmployee extends Employee {

    private final double weeklyWage;
    private final double hours;
    private double earned;

    HourlyEmployee(String firstName, String lastName, String socialSecurityNumber, double weeklyWage, double hours) {
        super(firstName, lastName, socialSecurityNumber);
        this.weeklyWage = weeklyWage;
        this.hours = hours;
    }

    @Override
    public double payment() {
        if (hours < 40) {
            earned = weeklyWage * hours;
        } else if (hours > 40) {
            earned = weeklyWage * hours + (hours - 40) * weeklyWage * 1.5;
        }
        total[0] = total[0] + earned;
        return earned;
    }

    @Override
    public String tostring() {
        return "Hourly Employee: " + firstname + " " + lastname + "\n" + "social security number: " + socialSecurityNumber + "\n"
                + "Hourly wage: " + weeklyWage + "\n" + "hours worked: " + hours + "\n" + "Earned: " + earned;
    }
}

class CommissionEmployee extends Employee {

    protected double grossSales;
    protected double earned;
    protected double commissionRate;

    CommissionEmployee(String firstName, String lastName, String socialSecurityNumber, double grossSales, double commissionRate) {
        super(firstName, lastName, socialSecurityNumber);
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    @Override
    public double payment() {
        earned = commissionRate * grossSales;
        total[0] = total[0] + earned;
        return earned;
    }

    @Override
    public String tostring() {
        return "Commission Employee: " + firstname + " " + lastname + "\n" + "social security number: " + socialSecurityNumber + "\n"
                + "Gross Sales: " + grossSales + "\n" + "Commission Rate: " + commissionRate + "\n" + "Earned: " + earned;
    }
}

class BasePlusCommissionEmployee extends CommissionEmployee {

    private final double baseSalary;
    private double newBaseSalary;
    private double g;

    BasePlusCommissionEmployee(String a, String b, String c, double d, double e, double f) {
        super(a, b, c, e, d);
        baseSalary = f;
    }

    @Override
    public double payment() {
        g = baseSalary * 0.10;
        newBaseSalary = baseSalary + g;
        earned = (commissionRate * grossSales) + newBaseSalary;
        total[0] = total[0] + earned;
        return earned;
    }

    @Override
    public String tostring() {
        return "Base Plus Commission Employee: " + firstname + " " + lastname + "\n" + "social security number: " + socialSecurityNumber + "\n"
                + "Gross Sales: " + grossSales + "\n" + "Commission Rate: " + commissionRate + "\n" + "Base Salary: " + baseSalary + "\n"
                + "New base salary with 10% increase is: " + newBaseSalary + "\n" + "Earned: " + earned;
    }
}

abstract class Supplier {

    int supplierID;
    String supplierFirstName;
    String supplierLastName;
    String itemType;
    int quantity;
    double price;

    public abstract void setSupplier(int supplierID, String supplierFirstName, String supplierLastName, String itemType, int quantity, double price);
}

class Inventory extends Supplier implements Ipayment {

    public ArrayList<Item> items = new ArrayList<Item>();
    public ArrayList<Order> orders = new ArrayList<Order>();

    public void addInventory(Item item) {
        items.add(item);
    }

    public void addOrders(Order order) {
        orders.add(order);
    }

    public String getInventory() {
        return items.toString();
    }

    public String getOrders() {
        return orders.toString();
    }

    static double product;

    @Override
    public double payment() {
        product = price * quantity;
        total[1] = total[1] + product;
        return product;
    }

    @Override
    public void setSupplier(int supplierID, String supplierFirstName, String supplierLastName, String itemType, int quantity, double price) {
        this.supplierID = supplierID;
        this.supplierFirstName = supplierFirstName;
        this.supplierLastName = supplierLastName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.price = price;
    }

    public void showInventoryTotal() {
        System.out.println("Total payment given to the supplier for inventory : " + total[1]);
    }

    @Override
    public String toString() {
        return "ID : " + supplierID + "\nName : " + supplierFirstName + " " + supplierLastName
                + "\nItem Type : " + itemType + "\nQuantity : " + quantity + "\nPrice : " + price;
    }
}

class Order {

    public ArrayList<Item> orderItems = new ArrayList<Item>();
    public static double orderTotal;
    public ArrayList<Double> orderSum = new ArrayList<Double>();

    public Order() {

    }

    public Order(double price, int quantity) {
        orderTotal = orderTotal + (price * quantity);
    }

    public void orderSum() {
        orderSum.add(orderTotal);
    }

    public void addItem(Item item) {
        orderItems.add(item);
    }

    public String toString() {
        return orderItems.toString();
    }
}

class Item {

    public String name;
    public double price;
    public int quantity;

    public Item() {

    }

    public Item(String name, double price, int quantity) {
        this.name = name;
        setQuantity(quantity);
        setPrice(price);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return "Name : " + name + ", " + " Price is : " + getPrice() + ", " + " Quantity is : " + getQuantity();
    }
}

class Clothes extends Item {

    public Clothes(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String toString() {
        return "\nType : Clothes, " + super.toString();
    }
}

class Cosmetics extends Item {

    public Cosmetics(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String toString() {
        return "\nType : Cosmetics, " + super.toString();
    }
}

class Electronics extends Item {

    public Electronics(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String toString() {
        return "\nType : Electronics, " + super.toString();
    }
}

public class InferaceExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        // Employeee data
        String type = "";
        ArrayList<String> employeeData = new ArrayList<String>();
        ArrayList<String> supplierData = new ArrayList<String>();
        ArrayList<String> emp = new ArrayList<String>();
        SalariedEmployee se = null;
        HourlyEmployee he = null;
        CommissionEmployee ce = null;
        BasePlusCommissionEmployee bpce = null;
        int selection0 = 0;
        // Inventory data
        ArrayList<String> type1 = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Double> prices = new ArrayList<Double>();
        ArrayList<Integer> quantities = new ArrayList<Integer>();
        Order ord = new Order();
        Order or;
        int a = 0;
        int b = 0;
        ArrayList<Integer> c = new ArrayList<Integer>();
        Inventory invent = new Inventory();
        int selection1, selection2;
        selection1 = 0;
        String name;
        int quantity;
        double price;

        while (selection0 >= 0) {
            System.out.println("1. For Employee ");
            System.out.println("2. For Inventory ");
            System.out.println("3. Exit");
            System.out.print("> ");
            int input = sc.nextInt();
            selection0 = input;
            if (selection0 == 1) {
                // For Employee

                int num = 0;
                while (num >= 0) {
                    num = employeemenu();
                    System.out.println("");
                    if (num == 1) {
                        type = "Salaried Employee";
                        emp.add("salary employee");
                        sc.nextLine();
                        System.out.print("Enter First name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter Last name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter Social security number: ");
                        String socialSecurityNumber = sc.nextLine();
                        System.out.print("Enter weekly salary: ");
                        double weeklySalary = sc.nextDouble();
                        se = new SalariedEmployee(firstName, lastName, socialSecurityNumber, weeklySalary);
                        se.payment();
                        employeeData.add(se.tostring());
                    } else if (num == 2) {
                        type = "Hourly Employee";
                        emp.add("Hourly employee");
                        sc.nextLine();
                        System.out.print("Enter First name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter Last name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter Social security number: ");
                        String socialSecurityNumber = sc.nextLine();
                        System.out.print("Enter hourly wage: ");
                        double wage = sc.nextDouble();
                        System.out.print("Enter hours worked: ");
                        double hours = sc.nextDouble();
                        he = new HourlyEmployee(firstName, lastName, socialSecurityNumber, wage, hours);
                        he.payment();
                        employeeData.add(he.tostring());
                    } else if (num == 3) {
                        type = "Commission Employee";
                        emp.add("commission employee");
                        sc.nextLine();
                        System.out.print("Enter First name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter Last name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter Social security number: ");
                        String socialSecurityNumber = sc.nextLine();
                        System.out.print("Enter gross sale: ");
                        double grossSale = sc.nextDouble();
                        System.out.print("Enter commission rate: ");
                        double commissionRate = sc.nextDouble();
                        ce = new CommissionEmployee(firstName, lastName, socialSecurityNumber, grossSale, commissionRate);
                        ce.payment();
                        employeeData.add(ce.tostring());
                    } else if (num == 4) {
                        type = "Base Plus Commission Employee";
                        emp.add("base plus commission employee");
                        sc.nextLine();
                        System.out.print("Enter First name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter Last name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter Social security number: ");
                        String socialSecurityNumber = sc.nextLine();
                        System.out.print("Enter gross sale: ");
                        double grossSale = sc.nextDouble();
                        System.out.print("Enter commission rate: ");
                        double commissionRate = sc.nextDouble();
                        System.out.print("Enter base salary: ");
                        double baseSalary = sc.nextDouble();
                        bpce = new BasePlusCommissionEmployee(firstName, lastName, socialSecurityNumber, grossSale, commissionRate, baseSalary);
                        bpce.payment();
                        employeeData.add(bpce.tostring());
                    } else if (num == 5) {
                        break;
                    }
                }
                    for (int i = 0; i < employeeData.size(); i++) {
                        System.out.println("");
                        System.out.println("");
                        System.out.println(employeeData.get(i));
                    }
                    System.out.println("");
                    System.out.println("");
                    for (int i = 0; i < emp.size(); i++) {
                        System.out.println("Employee " + (i + 1) + " is a " + emp.get(i));
                    }
                    System.out.println("");
                    System.out.println();
                
            }
            if (selection0 == 2) {
                // For Inventory

                while (selection1 >= 0) {
                    if (selection1 > 0) {
                        System.out.println("");
                    }
                    selection1 = menu();
                    if (selection1 == 1) {
                        selection2 = subMenu();
                        if (selection2 == 1) {
                            System.out.println("--- Clothes ---");
                            type1.add("Clothes");
                            System.out.print("Enter Name: ");
                            name = sc.next();
                            System.out.print("Enter Quantity: ");
                            quantity = sc.nextInt();
                            System.out.print("Enter price per unit: ");
                            price = sc.nextDouble();
                            for (int i = 0; i < names.size(); i++) {
                                if (name.equals(names.get(i)) && price == prices.get(i)) {
                                    quantity = quantities.get(i) + quantity;
                                    quantities.remove(i);
                                    names.remove(i);
                                    prices.remove(i);
                                    invent.items.remove(i);
                                }
                            }
                            names.add(name);
                            prices.add(price);
                            quantities.add(quantity);
                            Clothes clo = new Clothes(name, price, quantity);
                            invent.addInventory(clo);
                            System.out.println("");
                            System.out.print("Enter the ID of supplier: ");
                            int id = sc.nextInt();
                            System.out.print("Enter the first name of supplier: ");
                            String supFirstName = sc.next();
                            System.out.print("Enter the last name of supplier: ");
                            String supLastName = sc.next();
                            invent.setSupplier(id, supFirstName, supLastName, name, quantity, price);
                            System.out.println("");
                            invent.payment();
                            supplierData.add(invent.toString());
                        } else if (selection2 == 2) {
                            System.out.println("--- Cosmetics ---");
                            type1.add("Cosmetics");
                            System.out.print("Enter Name: ");
                            name = sc.next();
                            System.out.print("Enter Quantity: ");
                            quantity = sc.nextInt();
                            System.out.print("Enter price per unit: ");
                            price = sc.nextDouble();
                            for (int i = 0; i < names.size(); i++) {
                                if (name.equals(names.get(i)) && price == prices.get(i)) {
                                    quantity = quantities.get(i) + quantity;
                                    quantities.remove(i);
                                    names.remove(i);
                                    prices.remove(i);
                                    invent.items.remove(i);
                                }
                            }
                            names.add(name);
                            prices.add(price);
                            quantities.add(quantity);
                            Cosmetics co = new Cosmetics(name, price, quantity);
                            invent.addInventory(co);
                            System.out.println("");
                            System.out.print("Enter the ID of supplier: ");
                            int id = sc.nextInt();
                            System.out.print("Enter the first name of supplier: ");
                            String supFirstName = sc.next();
                            System.out.print("Enter the last name of supplier: ");
                            String supLastName = sc.next();
                            invent.setSupplier(id, supFirstName, supLastName, name, quantity, price);
                            System.out.println("");
                            invent.payment();
                            supplierData.add(invent.toString());
                        } else if (selection2 == 3) {
                            System.out.println("--- Electronics ---");
                            type1.add("Electronics");
                            System.out.print("Enter Name: ");
                            name = sc.next();
                            System.out.print("Enter Quantity: ");
                            quantity = sc.nextInt();
                            System.out.print("Enter price per unit: ");
                            price = sc.nextDouble();
                            for (int i = 0; i < names.size(); i++) {
                                if (name.equals(names.get(i)) && price == prices.get(i)) {
                                    quantity = quantities.get(i) + quantity;
                                    quantities.remove(i);
                                    names.remove(i);
                                    prices.remove(i);
                                    invent.items.remove(i);
                                }
                            }
                            names.add(name);
                            prices.add(price);
                            quantities.add(quantity);
                            Electronics elect = new Electronics(name, price, quantity);
                            invent.addInventory(elect);
                            System.out.println("");
                            System.out.print("Enter the ID of supplier: ");
                            int id = sc.nextInt();
                            System.out.print("Enter the first name of supplier: ");
                            String supFirstName = sc.next();
                            System.out.print("Enter the last name of supplier: ");
                            String supLastName = sc.next();
                            invent.setSupplier(id, supFirstName, supLastName, name, quantity, price);
                            System.out.println("");
                            invent.payment();
                            supplierData.add(invent.toString());
                        }

                    } else if (selection1 == 2) {
                        if (invent.items.size() == 0) {
                            System.out.println("we are out of inventory");

                        } else {
                            int count = 1;

                            while (count == 1) {
                                System.out.println("");
                                System.out.println("You can add to your cart from following items");
                                System.out.println("");
                                System.out.println(invent.getInventory());
                                System.out.println("");
                                System.out.print("Enter Name: ");
                                name = sc.next();
                                for (int i = 0; i < names.size(); i++) {
                                    if (names.get(i).equals(name)) {
                                        System.out.print("Enter Quantity: ");
                                        quantity = sc.nextInt();
                                        if (quantity <= quantities.get(i)) {
                                            int quant = quantities.get(i) - quantity;
                                            quantities.remove(i);
                                            quantities.add(i, quant);
                                            invent.items.remove(i);
                                            if (type1.get(i).equals("Clothes")) {
                                                Clothes clo = new Clothes(name, prices.get(i), quantities.get(i));
                                                invent.items.add(i, clo);
                                                Clothes CLO = new Clothes(name, prices.get(i), quantity);
                                                ord.addItem(CLO);
                                                a++;

                                            } else if (type1.get(i).equals("Cosmetics")) {
                                                Cosmetics co = new Cosmetics(name, prices.get(i), quantities.get(i));
                                                invent.items.add(i, co);
                                                Cosmetics CO = new Cosmetics(name, prices.get(i), quantity);
                                                ord.addItem(CO);
                                                a++;
                                            } else if (type1.get(i).equals("Electronics")) {
                                                Electronics elect = new Electronics(name, prices.get(i), quantities.get(i));
                                                invent.items.add(i, elect);
                                                Electronics ELECT = new Electronics(name, prices.get(i), quantity);
                                                ord.addItem(ELECT);
                                                a++;
                                            }
                                            System.out.println("Your Order Details");
                                            System.out.println("------------------");
                                            or = new Order(prices.get(i), quantity);
                                            System.out.println("Order total : " + Order.orderTotal);
                                            for (int j = b; j < ord.orderItems.size(); j++) {
                                                System.out.println("Items : " + ord.orderItems.get(j));
                                            }

                                        } else {
                                            System.out.println("Sorry! We are out of stocks");
                                        }
                                        break;
                                    } else if (i == names.size() - 1) {
                                        System.out.println("Sorry! This item is not in our stocks");
                                    }
                                }
                                System.out.println("Enter");
                                System.out.println("1. For adding more items");
                                System.out.println("2. For completing order");
                                count = sc.nextInt();
                            }
                            if (count == 2) {
                                System.out.println("Your Order Details");
                                System.out.println("------------------");
                                System.out.println("Order total : " + Order.orderTotal);
                                for (int j = b; j < ord.orderItems.size(); j++) {
                                    System.out.println("Items : " + ord.orderItems.get(j));
                                }
                                System.out.println("");
                                System.out.println("Order Added");

                                ord.orderSum();
                                b = a;
                                c.add(b);
                                a = 0;
                                Order.orderTotal = 0;
                            }
                        }
                    } else if (selection1 == 3) {
                        System.out.println("");
                        System.out.println(invent.getInventory());
                    } else if (selection1 == 4) {
                        System.out.println("Your Order Details");
                        System.out.println("------------------");
                        System.out.println("");
                        int k = 0;
                        for (int i = 0; i < ord.orderSum.size(); i++) {
                            int j;
                            System.out.println("Order Total : " + ord.orderSum.get(i));
                            for (j = 0; j < c.get(i) + k; j++) {
                                if (i != 0 && j == 0) {
                                    j = c.get(i - 1);
                                }

                                System.out.println(ord.orderItems.get(j));

                            }
                            k = j;
                        }

                    } else if (selection1 == 5) {
                        System.out.println("Items : ");
                        System.out.println(invent.items.toString());
                        System.out.println("Orders : ");
                        System.out.println(ord.toString());
                        for (int i = 0; i < supplierData.size(); i++) {
                            System.out.println("");
                            System.out.println("");
                            System.out.println(supplierData.get(i));
                        }
                    } else if (selection1 == 6) {
                        break;
                    }
                }
            }

            if (selection0 == 3) {
                break;
            }
        }
       

        System.out.println("");
        if (type.equals("Salaried Employee")) {
            se.showEmployeeTotal();
        } else if (type.equals("Hourly Employee")) {
            he.showEmployeeTotal();
        } else if (type.equals("Commission Employee")) {
            ce.showEmployeeTotal();
        } else if (type.equals("Base Plus Commission Employee")) {
            bpce.showEmployeeTotal();
        }
        invent.showInventoryTotal();

    }

    public static int menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------");
        System.out.println("Welcome to Inventory System");
        System.out.println("---------------------------");
        System.out.println("Please make your selection");
        System.out.println("");
        System.out.println("1. Add Inventory");
        System.out.println("2. Add Order");
        System.out.println("3. Show Inventory");
        System.out.println("4. Show Orders");
        System.out.println("5. Show All details");
        System.out.println("6. Exit System");
        System.out.println("");
        System.out.print("> ");
        int select1 = sc.nextInt();
        return select1;
    }

    public static int subMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("You can add items from the following list");
        System.out.println("1. Clothes");
        System.out.println("2. Cosmetics");
        System.out.println("3. Electronics");
        System.out.println("");
        System.out.print("> ");
        int select2 = sc.nextInt();
        return select2;
    }

    public static int employeemenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------Employee---------");
        System.out.println("");
        System.out.println("Please make your selection:");
        System.out.println("1. Salaried Employee");
        System.out.println("2. Hourly Employee");
        System.out.println("3. Commission Employee");
        System.out.println("4. Base Plus Commission Employee");
        System.out.println("5. Exit");
        System.out.print("> ");
        int select3 = sc.nextInt();
        return select3;
    }
}
