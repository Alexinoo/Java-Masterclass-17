package final_classes.part17_final_section_challenge.pirate;

public final class Soldier extends Combatant{
    public Soldier(String name, Weapon weapon) {
        super(name);
        setCurrentWeapon(weapon);
    }
}
