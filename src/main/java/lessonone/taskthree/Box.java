package lessonone.taskthree;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {

    private final List<T> fruitList = new ArrayList<>();

    public double getWeight() {
        return fruitList.stream().mapToDouble(Fruit::getWeight).sum();
    }

    public boolean compare(Box<? extends Fruit> fruit) {
        return Double.compare(getWeight(), fruit.getWeight()) == 0;
    }

    public void add(T fruit) {
        fruitList.add(fruit);
    }

    public void transferToBox(Box<T> toBox) {
        if (this == toBox) {
            return;
        }
        fruitList.forEach(toBox::add);
        fruitList.clear();
    }

}
