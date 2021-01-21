package lessonone.taskthree;

public class Main {

    public static void main1(String[] args) {
        Box<Apple> appleBoxOne = new Box<>();
        appleBoxOne.add(new Apple());
        appleBoxOne.add(new Apple());

        Box<Apple> appleBoxTwo = new Box<>();
        appleBoxTwo.add(new Apple());
        appleBoxTwo.add(new Apple());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(new Orange());
        orangeBox.add(new Orange());

        System.out.println(appleBoxOne.compare(orangeBox));
        System.out.println(appleBoxOne.compare(appleBoxTwo));

        appleBoxOne.transferToBox(appleBoxTwo);
        System.out.println(appleBoxOne.compare(appleBoxTwo));
    }

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(o.hashCode());
        System.out.println(o.hashCode());
        System.out.println(o.hashCode());

        Object o1 = new Object();
        System.out.println(o1.hashCode());
        System.out.println(o1.hashCode());
        System.out.println(o1.hashCode());
    }

}
