package com.shpp.p2p.cs.bcimbal.assignment16;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Assignment16Part1 {

    public static void main(String[] args) {

        LinkedList<String> q = new LinkedList<>();
        QQueue<String> qq = new QQueue<>();
        qq.add("123");
        qq.add("234");

        String tmp;
        tmp = qq.peek();
        qq.poll();
        tmp = qq.peek();
        qq.poll();
        qq.remove();

System.out.println(tmp );

    }
}
