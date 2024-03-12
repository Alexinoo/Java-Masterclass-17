package final_classes.part14_pirate_game_challenge;

import final_classes.part14_pirate_game_challenge.game.GameConsole;
import final_classes.part14_pirate_game_challenge.pirate.Pirate;
import final_classes.part14_pirate_game_challenge.pirate.PirateGame;
import final_classes.part14_pirate_game_challenge.pirate.Weapon;

public class Main {

    public static void main(String[] args) {
        Weapon weapon = Weapon.getWeaponByChar('P');
        System.out.println("weapon = "+ weapon+
                           ", hitPoints= "+weapon.getHitPoints()+
                            ", minLevel="+weapon.getMinLevel() );
        // weapon = PIXOL, hitPoints= 50, minLevel=1
        System.out.println("_______________________________________");

        var list = Weapon.getWeaponsByLevel(1);
        list.forEach(System.out::println); // KNIFE, AXE, MACHETE , PISTOL
        System.out.println("_______________________________________");


        // Create a new Pirate
        Pirate alex = new Pirate("Alex");
        System.out.println(alex);
        System.out.println("_______________________________________");

        //Call to get Towns
        PirateGame.getTowns(0).forEach(System.out::println);
        System.out.println("_______________________________________");

        PirateGame.getTowns(1).forEach(System.out::println);

        //Play the game now
        System.out.println("_______________________________________");
        var console = new GameConsole<>(new PirateGame("The pirate Game"));
        int playerIndex =  console.addPlayer();
        console.playGame(playerIndex);
    }
}
