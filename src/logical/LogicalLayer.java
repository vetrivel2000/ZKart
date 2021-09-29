package logical;

import com.Admin;
import com.CustomerInfo;
import com.Inventory;
import com.Orders;
import load.LoadToMemory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class LogicalLayer {
    static int numberOfOrders=0;
    static double totalPrice=0;
    LoadToMemory load = new LoadToMemory();
    ArrayList<Orders> orderedItems= new ArrayList<>();
    public String passwordEncryption(String password)
    {
        char[] oldPassword= password.toCharArray();
        char[] encryptedPassword = new char[oldPassword.length];
        for (int i = 0; i <oldPassword.length ; i++) {
            if (oldPassword[i]=='z')
            {
                encryptedPassword[i]='a';
            }
            else if(oldPassword[i]=='Z')
            {
                encryptedPassword[i]='A';
            }
            else if (oldPassword[i]=='9')
            {
                encryptedPassword[i]='0';
            }
            else
            {
                encryptedPassword[i]= (char) ((int)oldPassword[i]+1);
            }
        }
        String encrypted= new String(encryptedPassword);
        return encrypted;
    }
    public boolean passwordChecks(String password)
    {
        String regex="^(?=.*[0-9])"
                     +"(?=.*[a-z])(?=.*[A-Z])"
                     +"(?=\\S+$).{6,20}$";
        boolean result = Pattern.matches(regex,password);
        return result;
    }
    public void createCustomerFile()
    {
        File file = new File("C:\\Users\\inc1\\Desktop\\zusersdb.txt");
    }
    public void storeCustomerInfoToFile(String name,String encryptedPassword,String email,String mobile)
    {
        FileWriter fileWriter=null;
        try {
            fileWriter = new FileWriter("C:\\Users\\inc1\\Desktop\\zusersdb.txt",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        CustomerInfo customers = new CustomerInfo();
        customers.setEmail(email);
        customers.setName(name);
        customers.setMobileNumber(mobile);
        customers.setPassword(encryptedPassword);
        try {
            bufferedWriter.write(customers.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isApplicable(String mailId)
    {
        HashMap<String,CustomerInfo> customers= new HashMap<>();
        CustomerInfo customer=customers.get(mailId);
        if(customer.getNumberOfPurchases()>=3 || customer.getTotalPrice()>=20000)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isSpecialApplicable(String category,ArrayList<Inventory> inventories)
    {
        int max=0,flag=0;
        for (Inventory inventory : inventories) {
            if (inventory.getCategory().equals(category) && inventory.getNumberOfStocks()>max)
            {
                flag=1;
            }
            else
            {
                flag=0;
            }
            max= inventory.getNumberOfStocks();
        }
        if (flag==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void addAdmin()
    {
        load.addToAdminMap();
    }
    public void orderedItems(String mail,String category,String brand,String model,double price)
    {
        Orders orders= new Orders();
        orders.setBrand(brand);
        orders.setCategory(category);
        orders.setModel(model);
        orders.setPrice(price);
        orderedItems.add(orders);
        totalPrice=+price;
        numberOfOrders++;
        HashMap<String,CustomerInfo> customers= new HashMap<>();
        CustomerInfo customer=customers.get(mail);
        customer.setNumberOfPurchases(numberOfOrders);
        customer.setTotalPrice(totalPrice);
    }
    public ArrayList<Orders> getOrderedItems()
    {
        return orderedItems;
    }
    public void changePassword(String email,String newPassword)
    {
        HashMap<String,CustomerInfo> customers= load.getCustomers();
        CustomerInfo customer= customers.get(email);
        customer.setPassword(newPassword);
    }
    public void changeAdminPassWord(String email,String newPassword)
    {
        HashMap<String,Admin> admins= load.getAdmins();
        Admin admin= admins.get(email);
        admin.setPassword(newPassword);
    }
    public boolean checkUser(String userName)
    {
        HashMap<String,CustomerInfo> customers=load.getCustomers();
        return customers.containsKey(userName);
    }
    public boolean checkPassword(String userName,String password)
    {
        HashMap<String,CustomerInfo> customers=load.getCustomers();
        CustomerInfo customer=customers.get(userName);
        if (customer.getPassword().equals(password))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkAdmin(String userName)
    {
        HashMap<String,Admin> adminHashMap = load.getAdmins();
        return adminHashMap.containsKey(userName);
    }

    public void addToCustomersMap()
    {
        FileReader fileReader=null;
        try {
            fileReader=new FileReader("C:\\Users\\inc1\\Desktop\\zusersdb.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String everything = sb.toString();
        String[] allDetails=everything.split(",");
        String email=allDetails[0];
        String name=allDetails[1];
        String password=allDetails[2];
        String mobileNumber=allDetails[3];
        load.addToCustomerMap(email,password,name,mobileNumber);
    }
    public void createNewInventoryFile()
    {
        File file = new File("C:\\Users\\inc1\\Desktop\\zitemsdb.txt");
    }
    public void storeInventoryIntoFile(String category,String brand,String model,double price,int numberOfStocks)
    {
        FileWriter fileWriter=null;
        try {
            fileWriter = new FileWriter("C:\\Users\\inc1\\Desktop\\zitemsdb.txt",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Inventory inventory= new Inventory();
        inventory.setBrand(brand);
        inventory.setCategory(category);
        inventory.setModel(model);
        inventory.setPrice(price);
        inventory.setNumberOfStocks(numberOfStocks);
        try {
            bufferedWriter.write(inventory.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addInventoryToMap(Inventory inventory)
    {
        load.addToInventory(inventory);
    }
    public Inventory getInventoryObject(String category,String model,String brand,double price,int stocks)
    {
        Inventory inventory= new Inventory();
        inventory.setNumberOfStocks(stocks);
        inventory.setPrice(price);
        inventory.setCategory(category);
        inventory.setModel(model);
        inventory.setBrand(brand);
        return inventory;
    }
    public HashMap<String,Inventory> getInventory()
    {
        return load.getItems();
    }
}
