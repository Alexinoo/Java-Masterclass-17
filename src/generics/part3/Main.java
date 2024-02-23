package generics.part3;

public class Main {

    /*
        Generic class
        ==============

          - Declaration
            - A generic class declaration has angle brackets with a T in them, directly after the class name

            - T is the placeholder for a type that will be specified later
            - This is called a Type identifier and it can be any letter or word but T which is short
              for Type is commonly used

            - Many of Java's libraries are written using generic classes and interfaces


           -  So we have a baseball team application and le's imagine it sold really well, so well that a football team is
              interested in using it . What do we do..?

                - Basically we can have a class that can handle Football team as well

                    - Option 1 - copy and paste BaseballTeam and rename everything for FootballTeam and also create
                                 FootballPlayer record (rarely recommended approach)

                    - Option 2 - Use a Player Interface - We could change BaseballTeam to simply Team and use an interface type
                                 (or abstract or base class) called Player
                               - This is a better design than Option1 but it still got problems

                               - Let's explore it in this part2
                - And now we've got a team that supports each kind of player.

                - But this team has a problem
                    - First, there is no type checking when it comes to adding Team members

                    e.g
                        var Jordan = new BaseballPlayer("Michael Jordan","Center Fielder")
                    - Add him to Liverpool club but we know we created him as a BaseballPlayer
                      liverpool.addTeamMember(mohSalah);
                - This is not exactly what we would want

                - We could leave the rules to whoever is using this code or we could build in some rules

                    - Option 3 - Generics gives us this solution, by creating a generic team, meaning a team class
                                 which has a type parameter

                               - Let's copy SportsTeam class and make it generic

                               - The first thing we need to do to make a class generic is to set up Type parameters in the angle brackets

                               - Using T is just a convention , short for whatever type you want to use this Team class for

                               - But you can put anything you want there

                               - Single letters are the convention, and they are a lot easier to spot in the class code

                               - You can have more than 1 type parameter i.e T1,T2,T3 but for readability , its easier to read the code
                                 with alternate letter selections as shown below

                                    public class Team<T1, T2, T3>{} instead do public class Team<T, S, U>{}

                               - A few letters are reserved for special use cases

                               - The most commonly used type parameter identifiers are:-

                                    E - for Element (used extensively by the Java Collections Framework)
                                    K - for Key (used for mapped types)
                                    N - for Number
                                    T - for Type
                                    V - for Value
                                    S,U,V etc for 2nd , 3rd,4th types.

                    - So let's copy SportsTeam and create a Team Generic class
                    - Next , find replace all Player Types to T

                    - Then on the main method add 2 teams - Man-utd vs Arsenal and set their score
                    - Then Duplicate scoreResult and update parameterized params to Team in his case.

                    - The code compiles but we've got warning - This means we are implementing it with the raw
                      use of the class

                     - Raw Usage of Generic Class

                        - When you use generic classes, either referencing them or instantiating them, it's definitely
                          recommended that you include type parameter

                        - But you can use them without specifying one which is referred to as use of Raw Use of the reference type

                      - Generics allow the compiler to do compile-time checking when adding and processing elements in the list

                      - Generics simplify code, because we don't have to do our own type checking and casting as we would, if
                        the type of our elements was object

                     - We can't use a generic class, any generic class with primitive data types

                     - Luckily we have autoboxing and this isn't a big problem - We can use Wrapper classes instead

                     - now let's say, we really don't want Team to be used for any class under the sun

                     - Instead, we want it to only work for things that implement the Player interface

                     - This requires a small change to the Team class

                     - After the generic type T, we can add extends Player still in the angle brackets

                     - B4 we talk about this, in our main method , we now have a compiler error where we used String and Integer class
                       that says Type is not within it's bound and should implement Player instead

                     - The extends keyword doesn't have the same meaning as extends when it's used in a class declaration .. like it isn't
                       saying our type T extends Player, although it could

                     - Rather it is saying that the parameterized type T , has to be a Player, or a subtype of Player

                     -Now Player in this case could have been either a class or an interface, the syntax would be the same

                     - This declaration establishes what is called an upper bound, on the types that are allowed to be used with this class
                     - This means that only subtypes of Player or a Player itself( if it were a class or an instance) be used with this class

          */

    public static void main(String[] args) {
        var philly = new Affiliation("city","Philadephia, PA","US");

        BaseballTeam phillies1 = new BaseballTeam("Philadelphia Phillies");
        BaseballTeam astros1 = new BaseballTeam("Houston Astros");
        scoreResult(phillies1 ,3 , astros1 , 5);

        SportsTeam phillies2 = new SportsTeam("Philadelphia Phillies");
        SportsTeam astros2 = new SportsTeam("Houston Astros");
        scoreResult(phillies2 ,3 , astros2 , 5);

        Team<BaseballPlayer, Affiliation> phillies = new Team<>("Philadelphia Phillies",philly);
        Team<BaseballPlayer, Affiliation> astros = new Team<>("Houston Astros");
        scoreResult(phillies ,3 , astros , 5);

        var harper = new BaseballPlayer("B Harper","Right Fielder");
        var marsh = new BaseballPlayer("B Marsh","Right Fielder");
        phillies.addTeamMember(harper);
        phillies.addTeamMember(marsh);
        var guthrie = new BaseballPlayer("D Guthrie","Center Fielder");
        phillies.addTeamMember(guthrie);
        phillies.listMembers();

        SportsTeam afc1 = new SportsTeam("Adelaide Crows");
        Team<FootballPlayer, String> afc = new Team<>("Adelaide Crows","City of Adelaide, South Australia, in AU");
        var tex = new FootballPlayer("Tex Walker","Center half forward");
        afc.addTeamMember(tex);
        var rory = new FootballPlayer("Rory Laird","Midfield");
        afc.addTeamMember(rory);
        afc.listMembers();

        ////// Generics PART 3 ////////////////
        // Add Team Adelaide Storm
        // Add Type as a String - Compile and runs
        Team<VolleyballPlayer, Affiliation> adelaide = new Team<>("Adelaide Storm");
        adelaide.addTeamMember(new VolleyballPlayer("N Roberts","Setter"));
        adelaide.listMembers();

        // Add Team Canberra Heat
        // Specify type on the left Type as a String - Compile and runs
        var canberra = new Team<VolleyballPlayer, Affiliation>("Canberra Heat");
        canberra.addTeamMember(new VolleyballPlayer("B Black","opposite"));
        canberra.listMembers();
        scoreResult(canberra,0,adelaide,1);

        //Can't use generic class with primitive data types
        //Team<int> melbourneVB = new Team<>("Melbourne Vipers");
        //Team<Integer> melbourneVB = new Team<>("Melbourne Vipers");

    }
    //Baseball phillies vs Astros

    public static void scoreResult(BaseballTeam team1, int team1_score,
                                   BaseballTeam team2, int team2_score){
        String message = team1.setScore(team1_score,team2_score);
        team2.setScore(team2_score,team1_score);

        System.out.printf("%s %s %s %n",team1,message,team2);

    }

    //SportsTeam Adelaide Crows

    public static void scoreResult(SportsTeam team1, int team1_score,
                                   SportsTeam team2, int team2_score){
        String message = team1.setScore(team1_score,team2_score);
        team2.setScore(team2_score,team1_score);

        System.out.printf("%s %s %s %n",team1,message,team2);

    }

    //Team

    public static void scoreResult(Team team1, int team1_score,
                                   Team team2, int team2_score){
        String message = team1.setScore(team1_score,team2_score);
        team2.setScore(team2_score,team1_score);

        System.out.printf("%s %s %s %n",team1,message,team2);

    }
}
