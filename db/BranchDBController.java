

package db;

import java.io.*;
import java.util.*;
import branch_management.*;
/**
Manages the database operations related to branches.
This class interacts with the BranchDB class to retrieve branch information, add new branches, update branch details, and handle database read/write operations for branch data.
*/

public class BranchDBController {
    private static BranchDB branchDB;

    public BranchDBController(){
        branchDB = new BranchDB();
    }

    public BranchDBController(boolean doNotOverwrite){
    }

    public HashMap<Integer, String> getAllBranches() {
        return branchDB.getIdToNameMap();
    }

    public HashMap<Integer, String> getOpenBranches(){
        HashMap<Integer, String> openBranches = new HashMap<>();

        List <Branch> branches = branchDB.getBranchList();

        for (Branch branch : branches){
            if (branch.getisOpen()){
                openBranches.put(branch.getBranchId(), branch.getBranchName());
            }
        }
        return openBranches;

        
        
    }

    public Branch getBranchById(int branchId) throws NullPointerException{
        // Return branch object from list matching the branch ID

        return branchDB.getBranchList().stream()
                         .filter(branch -> branch.getBranchId() == branchId)
                         .findFirst()
                         .orElseThrow(() -> new NullPointerException());
    }

    public String getBranchNameById(int branchId) {
        // Return branch name from map by branch ID
        return branchDB.getIdToNameMap().get(branchId);
    }

    public void setBranchName(int branchId, String branchName) {
        // Update branch name in map
        getAllBranches().put(branchId, branchName);
        // Update branch name in list of branch objects
        branchDB.getBranchList().stream()
                  .filter(branch -> branch.getBranchId() == branchId)
                  .findFirst()
                  .ifPresent(branch -> branch.setBranchName(branchName));
    }

    public void addBranch(Branch branch) {
        branchDB.addBranch(branch);
    }

    
    public boolean closeBranch(int branchId){
        try{
            Branch branch = this.getBranchById(branchId);
            branch.setOpen(false);
            return true;
        }
        catch (NullPointerException e){
            return false;
        }
    }


    public int openBranch(int branchId){
        Branch branch;

        try{
            branch = this.getBranchById(branchId);
        }catch (NullPointerException e){
            return 1;
        }

        BranchHR branchHR = branch.getBranchHR();

        if (branchHR.getNoManagers() == 0){
            return 2;
        }

        branch.setOpen(true);
        return 0;

    }

    public boolean writeDB(String filename) {

        List<Branch> branchList = branchDB.getBranchList();
        String[] line = new String[8];

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Branch branch : branchList) {

                line[0] = Integer.toString(branch.getBranchId());
                line[1] = branch.getBranchName();
                line[2] = branch.getLocation();


                line[3] = Integer.toString(branch.getBranchHR().getStaffQuota());

                line[5] =  branch.getisOpen() ? "1" : "0";
                line[6] = Integer.toString(branch.getBranchHR().getNoStaff());
                line[7] = Integer.toString(branch.getBranchHR().getNoManagers());
                

                Menu menu = branch.getMenu();

                String menuDetails = "";

                for( MenuItem menuItem: menu.getFoodList()) {
                	String[] itemDetails = new String[6]; 
                    
                    itemDetails[0] = Integer.toString(menuItem.getId());

                    FoodCategory foodcat = menuItem.getCategory();

                    itemDetails[1] = foodcat.name();

                    itemDetails[2] = menuItem.getName();

                    itemDetails[3] = Double.toString(menuItem.getPrice());

                    itemDetails[4] = menuItem.isAvailable() ? "1" : "0";

                    itemDetails[5] = menuItem.getDescription().replace(',', '*');

                    if (menuDetails == ""){
                        menuDetails = String.join(":", itemDetails);
                    }
                    
                    else{
                        menuDetails = menuDetails + ";" + String.join(":", itemDetails);
                    }
                
                }

                line[4] = menuDetails;

                for (String data : line){
                    bw.write(data);
                    bw.write(",");
                }
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean readDB(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            
            String line;


            while ((line = reader.readLine()) != null) {

                if(line.trim().matches("[,\\s]*")) break;


                String[] parts = line.split(",");
                int branchId = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String location = parts[2].trim();
                int staffQuota = Integer.parseInt(parts[3].trim());
                String menuDetails = parts[4].trim();

                boolean isOpen = parts[5].trim().equals("1");

                int noStaff = Integer.parseInt(parts[6].trim());

                int noManager = Integer.parseInt(parts[7].trim());


                
                Branch branch = new Branch(branchId, name, staffQuota, location, isOpen, noStaff, noManager);
                
                String[] menuItems = menuDetails.split(";");

                Menu menu = branch.getMenu();
                		
                for(String menuItem : menuItems) {
                    
                	String[] itemDetails = menuItem.split(":");
                	int itemId = Integer.parseInt(itemDetails[0].trim());
                	String category = itemDetails[1].trim();

                    FoodCategory foodcat = FoodCategory.valueOf(category);

                    String itemName = itemDetails[2].trim();

                    double price = Double.parseDouble(itemDetails[3].trim());

                    boolean availability = "1".equals(itemDetails[4].trim());
                    String description = itemDetails[5].trim().replace('*', ',');


                    menu.addItem(itemId, itemName, price, foodcat, availability, description);
                
                }
                

                this.addBranch(branch);

            }
            reader.close();
            return true;
        } catch (IOException  e) {
            e.printStackTrace();
            return false;
        }

        

    }
}