package final_classes.part15_final_class_constrcutor_access_review.pirate;

import final_classes.part15_final_class_constrcutor_access_review.game.Game;
import final_classes.part15_final_class_constrcutor_access_review.game.GameAction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PirateGame extends Game<Pirate> {

    private static final List<List<String>> levelMap;

    //---------------------------------------------------------------------
    static {
        levelMap = new ArrayList<>();
        System.out.println("Loading data...");
        loadData();
        if(levelMap.size() ==0){
            throw new RuntimeException("Could not load data, try later");
        }
        System.out.println("Finished loading data");
    }
    //---------------------------------------------------------------------

    public PirateGame(String gameName) {
        super(gameName);
    }

    @Override
    public Pirate createNewPlayer(String name) {
        return new Pirate(name);
    }

    @Override
    public Map<Character, GameAction> getGameActions(int playerIndex) {
        Pirate pirate = getPlayer(playerIndex);
        System.out.println(pirate);
        List<Weapon> weapons = Weapon.getWeaponsByLevel(pirate.value("level"));

        Map<Character, GameAction> map = new LinkedHashMap<>();

        for (Weapon weapon:weapons) {
            char init = weapon.name().charAt(0);
            map.put(init,new GameAction(init,"Use "+weapon,this::useWeapon));
        }
        map.putAll(getStandardActions());
        return map;
    }

    private static void loadData(){
       // Level 1 Towns
        levelMap.add(new ArrayList<>(List.of(
                "Bridgetown, Barbados",
                "Fitts Village, Barbados",
                "Holetown, Barbados"
        )));

        // Level 2 Towns
        levelMap.add(new ArrayList<>(List.of(
                "Fort-de-France, Martinique",
                "Saint-Anne, Martinique",
                "Le Vaulting, Martinique"
                )));
    }

    public static List<String> getTowns(int level){
        if(level <= (levelMap.size() - 1)){
            return levelMap.get(level);
        }
        return null;
    }

    private boolean useWeapon(int playerIndex){
        return getPlayer(playerIndex).useWeapon();
    }

    @Override
    public boolean executeGameAction(int player, GameAction action) {
        getPlayer(player).setCurrentWeapon(Weapon.getWeaponByChar(action.key()));
        return super.executeGameAction(player, action);
    }
}
