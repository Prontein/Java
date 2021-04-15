package ru.geekbrains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


class lesson22Test {

    @ParameterizedTest
    @MethodSource("generateIntArrays")
    void operation (int[] array, int [] result) {

        Assertions.assertArrayEquals(result , lesson22.operation(array));
    }

    private static Stream<Arguments> generateIntArrays() {

        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[] {1,2,3,4,5,6,7,8,9,10}, new int[] {5,6,7,8,9,10}));
        list.add(Arguments.arguments(new int[] {2,2,3,4,5,6,4,8,9,7}, new int[] {8,9,7}));
        list.add(Arguments.arguments(new int[] {2,2,3,4,5,6,4,8,9,1,5,3,8,9}, new int[] {8,9,1,5,3,8,9}));
        list.add(Arguments.arguments(new int[] {4,2,3,1,5,6,0,8,9,1,5,3,8,9}, new int[] {2,3,1,5,6,0,8,9,1,5,3,8,9}));
        list.add(Arguments.arguments(new int[] {4,2,3,1,5,6,0,8,9,1,5,3,8,9,4}, new int[] {}));

        return list.stream();
    }

    @Test
    void operationException () {

        int [] myArray2 = {2,2,3,1,5,6,7,8,9,0};
        Assertions.assertThrows(RuntimeException.class, () -> lesson22.operation(myArray2));
    }


    @Test
    void arrayContents() {

        int [] myArray = {1,2,3,4,5,6,7,8,9};
        Assertions.assertTrue(lesson22.arrayContents(myArray));
    }

    @Test
    void arrayContentsFalse() {

        int [] myArray = {1,2,3,0,5,6,7,8,9};
        Assertions.assertFalse(lesson22.arrayContents(myArray));
    }

    //    @Test
//    void operation () {
//
//        int [] myArray1 = {1,2,3,4,5,6,7,8,9,10};
//        int [] result1 = {5,6,7,8,9,10};
//
//        Assertions.assertArrayEquals(result1 , lesson22.operation(myArray1));
//    }
//    @Test
//    void operation2 () {
//
//        int [] myArray3 = {2,2,3,4,5,6,4,8,9,7};
//        int [] result3 = {8,9,7};
//
//        Assertions.assertArrayEquals(result3 , lesson22.operation(myArray3));
//    }
//
//    @Test
//    void operation3 () {
//
//        int [] myArray4 = {2,2,3,4,5,6,4,8,9,1,5,3,8,9};
//        int [] result4 = {8,9,1,5,3,8,9};
//
//        Assertions.assertArrayEquals(result4 , lesson22.operation(myArray4));
//    }
//
//    @Test
//    void operation4 () {
//
//        int [] myArray5 = {4,2,3,1,5,6,0,8,9,1,5,3,8,9};
//        int [] result5 = {2,3,1,5,6,0,8,9,1,5,3,8,9};
//
//        Assertions.assertArrayEquals(result5 , lesson22.operation(myArray5));
//    }
//
//    @Test
//    void operation5 () {
//
//        int [] myArray6 = {4,2,3,1,5,6,0,8,9,1,5,3,8,9,4};
//        int [] result6 = {};
//
//        Assertions.assertArrayEquals(result6 , lesson22.operation(myArray6));
//    }
//


}