package load;

import com.Admin;
import com.CustomerInfo;
import com.Inventory;

import java.util.HashMap;

public class LoadToMemory {
    HashMap<String, CustomerInfo> customers= new HashMap<>();
    HashMap<String, Admin> admins =new HashMap<>();
    HashMap<String, Inventory> items= new HashMap<>();
    public void addToAdminMap()
    {
        Admin admin = new Admin();
        admin.setEmail("admin@zoho.com");
        admin.setPassword("xyzzy");
        admins.put(admin.getEmail(),admin);
    }
    public void addToCustomerMap(String username,String password,String name,String mobile)
    {
        CustomerInfo customer= new CustomerInfo();
        customer.setEmail(username);
        customer.setPassword(password);
        customer.setMobileNumber(mobile);
        customer.setName(name);
        customer.setNumberOfPurchases(0);
        customer.setTotalPrice(0);
        customers.put(username,customer);
    }
    public void addToInventory(Inventory inventory)
    {
        items.put(inventory.getCategory(),inventory);
    }
    public HashMap<String,CustomerInfo> getCustomers()
    {
        return customers;
    }
    public HashMap<String,Admin> getAdmins()
    {
        return admins;
    }
    public HashMap<String,Inventory> getItems()
    {
        return items;
    }
}
