package com.jia.ds;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by jia on 2019/10/10 21:23
 * peek()
 * pop()
 * push()
 * size()
 * isEmpty()
 **/
public final class Stack<E> {

    private Object[] elementData;
    private int count;
    private int capacity;
    private final int DEFAULT_CAPACITY = 8;
    private final Object[] EMPTY_ELEMENT_DATA = {};

    /**
     * 无参数构造函数，容量为默认容量，数组为空数组
     *
     */
    public Stack(){
        this.capacity = DEFAULT_CAPACITY;
        this.elementData = EMPTY_ELEMENT_DATA;
        this.count = 0;
    }

    /**
     * 指定容量，数组为空数组，插入第一个元素时再扩容
     * @param capacity 初始容量
     */
    public Stack(int capacity){
        this.elementData = new Object[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    /**
     * 将集合的元素压入栈中
     * 这种方式意义不大
     * @param c 指定集合
     */
    public Stack(Collection<E> c){
        elementData = c.toArray();

        if((count = elementData.length) != 0){
            // toArray() 可能返回的不是 Object[] 类型的值
            if(elementData.getClass() != Object[].class){
                elementData = Arrays.copyOf(elementData, count, Object[].class);
            }
        }else{
            elementData = EMPTY_ELEMENT_DATA;
        }

    }

    /**
     * 入栈
     * @param e 入栈元素
     */
    public void push(E e) {
        if(count == elementData.length){
            ensureCapacity(count + 1);
        }

        if (e != null) {
            elementData[count++] = e;
        }
    }

    @SuppressWarnings("unchecked")
    public E pop(){
        return (E)elementData[--count];
    }

    @SuppressWarnings("unchecked")
    public E peek(){
        return (E)elementData[count - 1];
    }

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder("stack: up >> ");
        for(int i = elementData.length - 1; i >= 0; i--){
            res.append(elementData[i]);
            if(i != 0){
                res.append(", ");
            }else{
                res.append(" >> down");
            }
        }

        return res.toString();
    }

    /**
     * 扩容
     * @param minCapacity 最小容量
     */
    private void ensureCapacity(int minCapacity){
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + oldCapacity >> 2;

        if(elementData == EMPTY_ELEMENT_DATA){
            newCapacity = DEFAULT_CAPACITY;
        }

        if(newCapacity < minCapacity){
            newCapacity = minCapacity;
        }

        elementData = Arrays.copyOf(elementData, newCapacity);
    }
}
 