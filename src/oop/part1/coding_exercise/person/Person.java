package oop.part1.coding_exercise.person;

public class Person {
    private String firstName;
    private String lastName;

    private int age;

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getAge(){
        return age;
    }

    public void setFirstName(String first_name){
        this.firstName = first_name;
    }

    public void setLastName(String last_name){
        this.lastName = last_name;
    }

    public void setAge(int age){
        if(age < 0 || age > 100){
            this.age = 0;
            return;
        }
        this.age = age;
    }

    public boolean isTeen(){
        return age > 12 && age < 20;
    }

    public String getFullName(){
        if(firstName.isEmpty() && lastName.isEmpty())
            return "";

        if(firstName.isEmpty())
            return lastName;

        if(lastName.isEmpty())
            return firstName;

        return firstName +" "+lastName;
    }

    public boolean isEmpty(){
        if(firstName =="" && lastName =="")
            return false;

        if(firstName == "")
            return false;

        if(lastName == "")
            return false;

        return true;
    }
}
