

package db;

import users.Staff;
import interfaces.DBController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
/**
Manages the database operations related to staff members.
This class interacts with the UserDB class to read staff data from a file, write staff data to a file, retrieve staff information, add new staff, remove staff, assign managers, and transfer staff between branches.
*/
public class UserDBController implements DBController{

    private static UserDB userDB = new UserDB();
    
    @Override
    public boolean readDB(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().matches("[,\\s]*")) break;
                String[] values = line.split(",");

                String name = values[0];
                String staffId = values[1];
                char role = values[2].charAt(0);
                char gender = values[3].charAt(0);
                int age = Integer.parseInt(values[4]);

                int branchId;
                if (role == 'A'){
                    branchId = -1;
                }
                else{
                    branchId = Integer.parseInt(values[6]);
                }

                Staff staffMember = new Staff(staffId, name, role, gender, age, branchId);

                staffMember.setPassword(values[5]);

                userDB.getStaffList().add(staffMember);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading staff data from file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean writeDB(String filename){
        List<Staff> staffList = userDB.getStaffList();
        

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Staff staff : staffList) {
                List <String> line = new ArrayList<>();
                line.add(staff.getName());
                line.add(staff.getStaffId());
                line.add(Character.toString(staff.getRole()));
                line.add(Character.toString(staff.getGender()));
                line.add(Integer.toString(staff.getAge()));
                line.add(staff.getPassword());

                if (staff.getRole() != 'A'){
                    line.add(Integer.toString(staff.getBranchId()));
                    
                }

                bw.write(String.join(",", line));
                bw.newLine();
                
            }
        }catch(IOException e){
            e.printStackTrace();
            return false;

        }

        return true;



    }




    public List<Staff> getAllStaff() {
        return userDB.getStaffList();
    }



    public Staff getStaffByLoginId(String loginId) throws NullPointerException{

        for (Staff staff : userDB.getStaffList()){
            if (staff.getStaffId().equals(loginId)){
                return staff;
            }
        }

        throw new NullPointerException();
    }


    public int addStaff(Staff newStaff) {
        try{
        getStaffByLoginId(newStaff.getStaffId());
            return 4;
        }catch(NullPointerException e) {
        }

        
        int branchId = newStaff.getBranchId();

        try{
        BranchDBController branchDBController= new BranchDBController(true);

        branch_management.Branch branch = branchDBController.getBranchById(branchId);
        branch_management.BranchHR hr = branch.getBranchHR();
        


        if(newStaff.getRole() == 'M'){
            boolean attempt = hr.addManager(newStaff);
            if (! attempt){
                return 1;
            }  
        }

        else{
            boolean attempt = hr.addStaff(newStaff);
            if (! attempt){
                return 2;
            }  
        }
    }catch(NullPointerException e){
        return 3;

    }

        userDB.getStaffList().add(newStaff);
        return 0;
    }

    public boolean updateStaffId(String staffId, String newStaffId ) {

        Staff staffToUpdate = getStaffByLoginId(staffId);
        if (staffToUpdate == null) {
            return false;
        }

        if (getStaffByLoginId(newStaffId) == null) {
            staffToUpdate.setId(newStaffId);
            return true;
        }
        return false;

    }

    public int removeStaff(String loginId, int branchId) {

        try{
            Staff staffToRemove = getStaffByLoginId(loginId);

            if(branchId != staffToRemove.getBranchId()) return 3;


            BranchDBController branchDBController= new BranchDBController(true);

            branch_management.Branch branch = branchDBController.getBranchById(branchId);

            if (! branch.getisOpen()){
                userDB.getStaffList().remove(staffToRemove);   
            }
            branch_management.BranchHR hr = branch.getBranchHR();


            if (hr.checkManagerisZero()){
                if (hr.checkStaffisZero()){
                    return 1;
                }
                else{
                    return 2;
                }
            }
            userDB.getStaffList().remove(staffToRemove);

            return 0;
        } 
        
        catch (NullPointerException e){
            return 3;
        }
    }


    public int assignManager(int branchId, String staffId){
        try{
            Staff staffToRemove = getStaffByLoginId(staffId);

            if (staffToRemove.getRole() == 'M') return 3;


            BranchDBController branchDBController= new BranchDBController(true);

            branch_management.Branch branch = branchDBController.getBranchById(branchId);
            branch_management.BranchHR hr = branch.getBranchHR();

            if (! hr.addManager(staffToRemove)){
                return 1;
            }
            staffToRemove.setRole('M');

            return 0;
        } 
        
        catch (NullPointerException e){
            return 2;
        }

    }

    public int transferStaff(int dst, int src, String staffId){
        Staff staffToRemove;
        try{
            staffToRemove = getStaffByLoginId(staffId);

            if(staffToRemove.getBranchId() != src){
                return 6;
            }
        }catch(NullPointerException e){
            return 6;
        }

        try{

            BranchDBController branchDBController= new BranchDBController(true);

            branch_management.Branch srcBranch = branchDBController.getBranchById(src);
            branch_management.Branch dstBranch = branchDBController.getBranchById(dst);
            branch_management.BranchHR srchr = srcBranch.getBranchHR();
            branch_management.BranchHR dsthr = dstBranch.getBranchHR();

            if ( staffToRemove.getRole() == 'M' && srchr.checkManagerisZero()){
                if (srchr.checkStaffisZero()){
                    return 1;
                }
                else{
                    return 2;
                }
            }


            else{
                if (staffToRemove.getRole() == 'M'){
                    boolean attempt = dsthr.addManager(staffToRemove);
                    if (!attempt){
                        return 3;
                    }
                }

                else {
                    boolean attempt = dsthr.addStaff(staffToRemove);
                    if (!attempt){
                        return 4;
                    }
                }
            }

            staffToRemove.setBranchId(dst);

            return 0;
        } 
        
        catch (NullPointerException e){
            return 5;
        }
    }
}
