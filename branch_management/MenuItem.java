

package branch_management;
/**
Represents a specific menu item with availability and customizations.
MenuItem extends the FoodItem class and adds attributes for availability (whether the item is available for order) and customization options.
*/
public class MenuItem extends FoodItem {
    private boolean isAvailable;
    private FoodCustomisation customisation;


    // Updated constructor to include description
    public MenuItem( String name, double price, FoodCategory category, boolean availability, String description) {
        super(name, price, category, description);
        this.isAvailable = availability;
        this.customisation = determineCustomisation(category);

        
    }

    

    private FoodCustomisation determineCustomisation(FoodCategory category) {
        switch (category) {
            case BURGER:
                return FoodCustomisation.BURGER;
            case DRINK:
                return FoodCustomisation.DRINK;
            case SET_MEAL:
                return FoodCustomisation.SET_MEAL;
            case SIDE:
                return FoodCustomisation.SIDE;
            default:
                return null; // Or throw an exception if appropriate
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public FoodCustomisation getCustomisation() {
        return this.customisation;
    }

    public void setCustomisation(FoodCustomisation customisation) {
        this.customisation = customisation;
    }
}

	

