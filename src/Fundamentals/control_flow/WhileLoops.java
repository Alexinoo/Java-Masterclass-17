package Fundamentals.control_flow;

public class WhileLoops {
    public static void main(String[] args) {

        System.out.println("======== For Loop (normal)==========");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }

        System.out.println("======== While Loop (basic) ==========");
        int j = 1;
        while(j <= 5){
            System.out.println(j);
            j++;
        }

        System.out.println("======== While Loop (advanced) ==========");
        int k = 1;
        while(true){
            if(k > 5)
                break;
            System.out.println(k);
            k++;
        }

        System.out.println("========do While Loop (basic) ==========");

        int counter = 1;
        boolean isReady = false;
        do {
            if(counter > 5) break;

            System.out.println(counter);
            counter++;
        }while(isReady);

        System.out.println("========do While Loop (advanced) ==========");

        int count = 1;
        boolean isContinue = false;
        do {
            if(count > 5) break;

            System.out.println(count);
            count++;
            isContinue = (count > 0);
        }while(isContinue);


        System.out.println("========Another while loop ==========");
        int number = 0;
        while(number < 50){
            number+=5;
            System.out.print(number+"_");
        }

        System.out.println();
        System.out.println("========continue in while loop ==========");
        int num = 0;
        while(num < 50){
            num+=5;
            if(num % 25 == 0){
                continue;
            }
            System.out.print(num+"_");
        }

    }
}
