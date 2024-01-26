package Fundamentals.control_flow.coding_exercises;

public class PaintJob {
    private static final int INVALID_VALUE = -1;

    public static void main(String[] args) {
        // System.out.println(getBucketCount(3.4, 2.1, 1.5, 2));
       //  System.out.println(getBucketCount(3.4, 2.1, 1.5));
      //   System.out.println(getBucketCount(7.25, 4.3, 2.35));


        System.out.println(getBucketCount(3.4, 1.5)); //3
        System.out.println(getBucketCount(6.26, 2.2)); // 3
        System.out.println(getBucketCount(3.26, 0.75)); //5
    }

    public static int getBucketCount(double width, double height, double areaPerBucket, int extraBuckets) {
        if (width <= 0 || height <= 0 || areaPerBucket <= 0 || extraBuckets < 0)
            return INVALID_VALUE;

        double areaToPaint = width * height;
        double areaWeCanPaint = areaPerBucket * extraBuckets;
        double areaLeftToPaint = areaToPaint - areaWeCanPaint;

        System.out.println(areaToPaint); // 3.4 * 2.1 = 7.14
        System.out.println(areaWeCanPaint); // 1.5 * 2 = 3.0
        System.out.println(areaLeftToPaint); // 7.14 - 3 = 4.14

        if (areaLeftToPaint < 0)
            return 0;
        else
            return ((int) Math.ceil(areaLeftToPaint / areaPerBucket)); // 4.14 / 1.5
    }

    public static int getBucketCount(double width,double height,double areaPerBucket){
        if(width <= 0 || height <= 0 || areaPerBucket <= 0)
            return INVALID_VALUE;

        double areaToPaint = width * height;
        double bucketsNeeded = areaToPaint / areaPerBucket;

        if( bucketsNeeded < 0)
            return 0;
        else
            return (int)Math.ceil((bucketsNeeded));
    }

    public static int getBucketCount(double area , double areaPerBucket){
        if(area <= 0 || areaPerBucket <= 0)
            return INVALID_VALUE;

        return (int)Math.ceil(area / areaPerBucket);

    }

}


