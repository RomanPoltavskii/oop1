package ru.vsu.cs.course2.g9.oop.poltavskii_r_a.oop_task1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        Collectionss.readOnlyList(list);
        list.add(6);
    }
}