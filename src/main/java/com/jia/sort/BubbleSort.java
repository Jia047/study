package com.jia.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class BubbleSort {

    @Test
    public void sortTest(){
        int n = 100;
        Integer[] arr = new Integer[100];
        for(int i = 0; i < 100; i++){
           arr[i] = (int)(Math.random() * 100);
        }
        Integer[] arr2 = new Integer[100];
        System.arraycopy(arr, 0, arr2, 0, arr2.length);
        System.out.println(Arrays.toString(arr));

        long start1 = System.currentTimeMillis();
        BubbleSort.sort(arr);
        long end1 = System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        System.out.println("sort(T[] arr) : " + (end1 - start1));

        long start2 = System.currentTimeMillis();
        BubbleSort.sort(arr2, Comparator.comparingInt(o -> o));
        long end2 = System.currentTimeMillis();
        System.out.println(Arrays.toString(arr2));
        System.out.println("sort(T[] arr, Comparator<T> comp) : " + (end2 - start2));


    }

    public static <T extends Comparable<T>> void sort(T[] arr){
        boolean change = true;

        for(int i = 0; i < arr.length && change; i++){
            change = false;
            for(int j = 0; j < arr.length - i - 1; j++){
                if(arr[j].compareTo(arr[j + 1]) > 0){
                    swap(arr, j, j + 1);
                    change = true;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] arr, Comparator<T> comparator){
        boolean change = true;
        for(int i = 0; i < arr.length && change; i++){
            change = false;
            for(int j = 0; j < arr.length - i - 1; j++){
                if(comparator.compare(arr[j], arr[j + 1]) > 0){
                    swap(arr, j , j + 1);
                    change = true;
                }
            }
        }
    }

    private static <T extends Comparable<T>> void swap(T[] arr, int i, int j){
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

}
