import com.Inventory;
import logical.LogicalLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ZKartRunner {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        LogicalLayer logical= new LogicalLayer();
        logical.createCustomerFile();
        logical.createNewInventoryFile();
        logical.addAdmin();
        ArrayList<Inventory> inventories=new ArrayList<>();
        int option;
        do {
            System.out.println("1.Signup user\n2.SignIn user\n3.SignInAdmin\n4.Exit");
            option=scan.nextInt();
            switch (option)
            {
                case 1:
                {
                    System.out.println("Enter name");
                    String name=scan.next();
                    System.out.println("Enter mail");
                    String mail= scan.next();
                    boolean flag= logical.checkUser(mail);
                    System.out.println(flag);
                    if (flag)
                    {
                        System.out.println("You already have an account..Please sign in..");
                        return;
                    }
                    System.out.println("Enter phone");
                    String mobile=scan.next();
                    System.out.println("Enter password");
                    String password=scan.next();
                    boolean checkPassword=logical.passwordChecks(password);
                    System.out.println(checkPassword);
                    if(!checkPassword)
                    {
                        System.out.println("Enter the correct password");
                        return;
                    }
                    String encryptedPassword=logical.passwordEncryption(password);
                    logical.storeCustomerInfoToFile(name,encryptedPassword,mail,mobile);
                    logical.addToCustomersMap();
                    break;
                }
                case 2:
                {
                    logical.addToCustomersMap();
                    System.out.println("Enter mail");
                    String mail= scan.next();
                    boolean flag=logical.checkUser(mail);
                    if (!flag)
                    {
                        System.out.println("Kindly create an account");
                        return;
                    }
                    System.out.println("Enter password");
                    String password=scan.next();
                    String encryptedPassword=logical.passwordEncryption(password);
                    boolean check=logical.checkPassword(mail,encryptedPassword);
                    if (!check)
                    {
                        System.out.println("Check your password");
                        return;
                    }
                    System.out.println("SuccessfullyLogin");
                    int choice;
                    do {
                        System.out.println("1.OrderAnItem\n2.ChangePassWord\n3.CheckOrderedHistory\n4.Logout");
                        choice=scan.nextInt();
                        switch (choice)
                        {
                            case 1:
                            {
                                System.out.println("Enter the category");
                                String category=scan.next();
                                HashMap<String,Inventory> inventoryHashMap=logical.getInventory();
                                Inventory inventory = inventoryHashMap.get(category);
                                System.out.println(inventory);
                                System.out.println("Enter brand");
                                String brand= scan.next();
                                System.out.println("Enter model");
                                String model= scan.next();
                                double price=inventory.getPrice();
                                boolean discount=logical.isApplicable(mail);
                                boolean special=logical.isSpecialApplicable(category,inventories);
                                if(discount && special)
                                {
                                    double discountedAmount=price*30/100;
                                    price-=discountedAmount;
                                }
                                else if (discount)
                                {
                                    double discountedAmount=price*20/100;
                                    price-=discountedAmount;
                                }
                                else if (special)
                                {
                                    double discountedAmount=price*10/100;
                                    price-=discountedAmount;
                                }
                                logical.orderedItems(mail,category,brand,model,price);
                                break;
                            }
                            case 2:
                            {
                                System.out.println("Enter new password");
                                String newPassWord= scan.next();
                                boolean checkPassword=logical.passwordChecks(newPassWord);
                                if(!checkPassword)
                                {
                                    System.out.println("Enter the correct password");
                                    return;
                                }
                                logical.changePassword(mail,newPassWord);
                                System.out.println("Password changed Successfully");
                                break;
                            }
                            case 3:
                            {
                                System.out.println(logical.getOrderedItems());
                                break;
                            }
                        }
                    }while (choice<4);
                    break;
                }
                case 3:
                {
                    System.out.println("Enter mail");
                    String mail= scan.next();
                    System.out.println("Enter password");
                    String password=scan.next();
                    boolean flag=logical.checkAdmin(mail);
                    if (!flag)
                    {
                        System.out.println("You are not an admin");
                        return;
                    }
                    System.out.println("Successfully logged in");
                    System.out.println("Enter the new password");
                    String newPassword = scan.next();
                    boolean checkPassword=logical.passwordChecks(newPassword);
                    if (!checkPassword)
                    {
                        System.out.println("Enter correct password");
                        return;
                    }
                    logical.changeAdminPassWord(mail,newPassword);
                    int choice;
                    do {
                        System.out.println("1.AddToInventory\n2.Logout");
                        choice=scan.nextInt();
                        if (choice == 1) {
                            System.out.println("Enter the category");
                            String category = scan.next();
                            System.out.println("Enter brand");
                            String brand = scan.next();
                            System.out.println("Enter model");
                            String model = scan.next();
                            System.out.println("Enter price");
                            double price = scan.nextDouble();
                            System.out.println("Enter numberOf stocks");
                            int numberOfStocks = scan.nextInt();
                            logical.storeInventoryIntoFile(category, brand, model, price, numberOfStocks);
                            Inventory inventory = logical.getInventoryObject(category, model, brand, price, numberOfStocks);
                            inventories.add(inventory);
                            logical.addInventoryToMap(inventory);
                        }
                        else
                        {
                            break;
                        }
                    }while (choice<2);
                    break;
                }
            }
        }while (option<4);
    }
}
