package com.jia.util;

import org.junit.Test;

import java.util.*;

public class Traverse {

    @Test
    public void listTraverseByIterator(){
        List<Integer> list = new ArrayList<Integer>();
        Random random = new Random();

        for(int i = 0; i < 10; i++){
            list.add(random.nextInt(100));
        }
        System.out.println(list);

        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer i = iterator.next();
            // 会抛 ConcurrentModificationException
//            util.remove(i);
            System.out.print(i + ", ");
        }

    }

    @Test
    public void mapTraverseByIterator(){
        Map<Integer, String> map = new HashMap<Integer, String>();
        Random random = new Random();
        Integer integer;
        for(int i = 0; i < 10; i++){
            integer = random.nextInt(100);
            map.put(integer, String.valueOf(integer));
        }

        System.out.println(map);

        // keySet
        Iterator<Integer> keySet = map.keySet().iterator();
        Integer key;
        while(keySet.hasNext()){
            key = keySet.next();
            System.out.println("key = " + key + ", name = " + map.get(key));
        }

        // entrySet
        Iterator<Map.Entry<Integer, String>> entry = map.entrySet().iterator();
        Map.Entry<Integer, String> e;
        while(entry.hasNext()){
            e = entry.next();
            System.out.println("key = " + e.getKey() + "， name = " + e.getValue());
        }
    }
}
