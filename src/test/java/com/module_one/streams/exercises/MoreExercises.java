package com.module_one.streams.exercises;

import org.junit.Test;

public class MoreExercises {

    @Test
    public void method1(){

        // Linked list : 1->2->3->2->1
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);


        print(head);
        Node rev = rev(head);
        print(rev);

    }

    private Node rev(Node head) {
        Node prev = null;
        while( head != null){
            Node temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;

        }

        return prev;

    }

    private void print(Node head) {

        while(head != null){
            System.out.println(head.val);
            head = head.next;

        }
    }


    public class Node {
        int val;
        Node next;
        Node() {}
        Node(int val) { this.val = val; }
        Node(int val, Node next) { this.val = val; this.next = next; }
    }
}


