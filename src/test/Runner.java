package test;

import Model.BinaryTree;

public class Runner {
    public static void main(String[] args) {
        BinaryTree<Integer> bts = new BinaryTree<>((n1, n2) -> Integer.compare(n1, n2));

        bts.addNode(50);
        bts.addNode(60);
        bts.addNode(5);
        bts.addNode(34);
        bts.addNode(40);
        bts.addNode(18);
        bts.addNode(69);
        bts.addNode(57);
        bts.addNode(63);

        System.out.println("Arbol ordenado InSort\n");
        bts.listInsort().forEach(System.out::println);
        System.out.println("\n---------------------------------\n");
        System.out.println("Es balanceado\n");
        System.out.println(bts.isBalanced());
        System.out.println("\n---------------------------------\n");
        System.out.println("Comprobar si el nodo 18 y 69 son hojas\n");
        System.out.println(bts.isLeaf(bts.findNode(18)));
        System.out.println(bts.isLeaf(bts.findNode(69)));
        System.out.println("\n---------------------------------\n");
        System.out.println("Comprobar cuantas hojas hay en en nivel 2 y 3\n");
        System.out.println(bts.countLeafsLevel(2));
        System.out.println(bts.countLeafsLevel(3));
        System.out.println("\n---------------------------------\n");
        System.out.println("Comprobar si el arbol es estrictamente binario\n");
        System.out.println(bts.isStrictBinary());
        System.out.println("\n---------------------------------\n");
        System.out.println("Obtiene una lista con todads las hojas del arbol\n");
        bts.getLeafs().forEach(System.out::println);
        System.out.println("\n---------------------------------\n");
        System.out.println("Comprueba si el arbol es perfecto\n");
        System.out.println(bts.isPerfect());

        for(int bs: bts.listAmplitude()){
            System.out.println(bs);
        }

    }
}
