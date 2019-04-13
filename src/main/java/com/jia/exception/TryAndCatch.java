package com.jia.exception;

import org.junit.Test;

/**
 * 父类，子类异常之间的捕捉
 */

class A extends Exception{};
class B extends A{}

public class TryAndCatch {

    @Test
    public void tryAndCatch(){
        try {
            try {
                throw new B();
            }catch (A a){
                System.out.println("catch A");
                throw a;
            }
        }catch (B b){
            System.out.println("catch b");
        }catch (Exception e){
            System.out.println("catch e");
        }
        finally {
            System.out.println("finally");
        }
    }
}
