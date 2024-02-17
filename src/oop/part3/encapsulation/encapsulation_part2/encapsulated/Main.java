package oop.part3.encapsulation.encapsulation_part2.encapsulated;

public class Main {

    public static void main(String[] args) {

        //EnhancedPlayer player = new EnhancedPlayer("Alex");
        EnhancedPlayer player = new EnhancedPlayer("Alex",200,"Sword");
        printInformation(player ,player.getHealthPercentage());


        System.out.println("_".repeat(20));

        player.loseHealth(50);
        printInformation(player, player.getHealthPercentage());

        System.out.println("_".repeat(20));

        player.restoreHealth(150);
        printInformation(player, player.getHealthPercentage());

    }

    public static void printInformation(EnhancedPlayer randomPlayer , int healthPercent){
        System.out.println("Health "+randomPlayer.healthRemaining());
        System.out.println("Remaining Health "+healthPercent);

    }
}
