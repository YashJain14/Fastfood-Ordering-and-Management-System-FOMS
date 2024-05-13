
package branch_management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
Enumerates the available customizations for different food categories.
Each food category has a set of predefined customizations, such as:
BURGER: LESS_PICKLES, LESS_SAUCE, NO_CHEESE
SIDE: LESS_SAUCE, MORE_SAUCE
DRINK: MORE_ICE, LESS_ICE, NO_ICE
SET_MEAL: Combination of customizations from BURGER, SIDE, and DRINK
This class also provides methods to get, add, and remove customizations for each category.
*/

public enum FoodCustomisation {
    BURGER(Arrays.asList("LESS_PICKLES", "LESS_SAUCE", "NO_CHEESE")),
    SIDE(Arrays.asList("LESS_SAUCE", "MORE_SAUCE")),
    DRINK(Arrays.asList("MORE_ICE","LESS_ICE", "NO_ICE")),
    SET_MEAL(combine(BURGER.customizations, SIDE.customizations, DRINK.customizations));

	
    private List<String> customizations;

    FoodCustomisation(List<String> customizations) {
        // Creates a new ArrayList so that the list can be mutable
        this.customizations = new ArrayList<>(customizations);
    }

    public List<String> getcustomization() {
        return customizations;
    }

    public void addcustomization(String customization) {
        customizations.add(customization);
    }

    public void removecustomization(String customization) {
    	customizations.remove(customization);
    }
    
    @SafeVarargs
	private static List<String> combine(List<String>... food) {
        List<String> result = new ArrayList<>();
        for (List<String> list : food) {
            result.addAll(list);
        }
        return result;
    }
}

// 