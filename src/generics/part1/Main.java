package generics.part1;

public class Main {

    /*
        Generic class
        ==============

          - Declaration
            - A generic class declaration has angle brackets with a T in them, directly after the class name

            - T is the placeholder for a type that will be specified later
            - This is called a Type identifier and it can be any letter or word but T which is short
              for Type is commonly used

            - Many of Java's libraries are written using generic classes and abstraction.interfaces
     */

    public static void main(String[] args) {
        BaseballTeam phillies = new BaseballTeam("Philadelphia Phillies");
        BaseballTeam astros = new BaseballTeam("Houston Astros");
        scoreResult(phillies ,3 , astros , 5);

        //Add Team members
        var harper = new BaseballPlayer("B Harper","Right fielder");
        var marsh = new BaseballPlayer("B Marsh","Right fielder");
        phillies.addTeamMember(harper);
        phillies.addTeamMember(marsh);
        phillies.listMembers();
    }

    public static void scoreResult(BaseballTeam team1,int team1_score,
                                   BaseballTeam team2,int team2_score){
        String message = team1.setScore(team1_score,team2_score);
        team2.setScore(team2_score,team1_score);

        System.out.printf("%s %s %s %n",team1,message,team2);

    }
}
