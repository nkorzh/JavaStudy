import com.sun.jdi.ObjectReference;

import java.io.*;
import java.util.*;

public class Main {

    public static Animal[] deserializeAnimalArray(byte[] data) throws java.lang.IllegalArgumentException {

        Animal[] animals;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {

            int animalsAmount = objectInputStream.readInt();
            animals = new Animal[animalsAmount];

            for (int i = 0; i < animalsAmount; i++) {
                animals[i] = (Animal) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException | java.lang.ClassCastException e) {
            throw new java.lang.IllegalArgumentException();
        }
        return animals;
    }

    

    public static void main(String[] args) throws IOException {
        Collection<?> collection = new ArrayList<>();
        Object object = 1;
        collection.remove(object);

        /// Generics Pair test
        Pair<Integer, String> pair = Pair.of(1, "hello");
        Integer i = pair.getFirst(); // 1
        String s = pair.getSecond(); // "hello"

        Pair<Integer, String> pair2 = Pair.of(1, "hello");
        boolean mustBeTrue = pair.equals(pair2); // true!
        boolean mustAlsoBeTrue = pair.hashCode() == pair2.hashCode(); // true!
        /// deserialize test
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeInt(3);
        Animal a1 = new Animal("zebra");
        Animal a2 = new Animal("horse");
        objectOutputStream.writeObject(a1);
//        objectOutputStream.writeObject(a2);
        Animal arr[] = deserializeAnimalArray(outputStream.toByteArray());
        for (Animal animal : arr) {
            System.out.println(animal);
        }
    }
}

