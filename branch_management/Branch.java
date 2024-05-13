
package branch_management;
import java.io.Serializable;
/**
 Represents a branch of the fast-food restaurant.
 Each branch has an ID, name, location, menu, and HR department.
 It also maintains a status of whether it is open or closed.
 */
public class Branch implements Serializable{
 private static final long serialVersionUID = 1L;

 private static int HighestId = 0;
 private int branchId;
 private String branchName;
 private BranchHR branchHR;
 private boolean isOpen;
 private Menu menu;
 private String location;


 public Branch(String branchName, int staffQuota, String location, boolean isOpen){
  this.branchId = generateNextId();
  this.menu = new Menu();
  this.location = location;
  this.isOpen = true;
  this.branchHR = new BranchHR(staffQuota);

 }

public Branch(int branchId, String branchName, int staffQuota, String location, boolean isOpen, int noStaff, int noManagers){
  this.branchId = branchId;
  if (branchId > HighestId){
	HighestId = branchId;
 }
  this.branchName = branchName;
  this.location = location;
  this.menu = new Menu();
  this.isOpen = isOpen;
  this.branchHR = new BranchHR(staffQuota, noStaff, noManagers);
 }



 private static synchronized int generateNextId() {
        return ++HighestId; 
    }

 public int getBranchId(){
  return branchId;
 }


 public String getBranchName(){
  return branchName;
 }

 public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

 public BranchHR getBranchHR(){
  return branchHR;
 }

 public boolean getisOpen(){
  return isOpen;
 }

 public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

 public String getLocation() {
            return location;
        }

 
 public Menu getMenu() {
  return this.menu; 
 }
 

}