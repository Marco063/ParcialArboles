package Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinaryTree <T> {
    private TreeNode<T> root;
    private Comparator<T> comparator;
    private ArrayList<T> out;
    private int cont;

    public BinaryTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void addNode( T info ) {
        TreeNode<T> node = new TreeNode<>(info);
        if ( isEmpty() ) {
            root = node;
        } else {
            TreeNode<T> aux = root;
            TreeNode<T> ant = null;
            while ( aux != null ) {
                ant = aux;
                aux = comparator.compare( info, aux.getInfo()) < 0 ? aux.getLeft() : aux.getRight();
            }
            if ( comparator.compare( info, ant.getInfo()) < 0 ) {
                ant.setLeft( node );
            } else {
                ant.setRight( node );
            }
        }
    }

    public TreeNode<T> findNode( T info ) {
        TreeNode<T> aux = root;
        while ( aux != null ) {
            if ( comparator.compare( info, aux.getInfo()) == 0 ){
                return aux;
            }
            aux = comparator.compare( info, aux.getInfo()) < 0 ? aux.getLeft() : aux.getRight();
        }

        return null;
    }

    public ArrayList<T> listPresort(){
        out = new ArrayList<>();
        presort(root);

        return out;
    }

    private void presort(TreeNode<T> root) {
        if ( root != null ) {
            out.add( root.getInfo() );
            presort( root.getLeft() );
            presort( root.getRight() );
        }
    }

    public ArrayList<T> listInsort() {
        out = new ArrayList<>();
        insort(root);

        return out;
    }

    private void insort(TreeNode<T> root) {
        if ( root != null ) {
            insort( root.getLeft() );
            out.add( root.getInfo() );
            insort( root.getRight() );
        }
    }

    public ArrayList<T> listAmplitude(){
        out = new ArrayList<>();
        ArrayDeque<TreeNode> tail = new ArrayDeque<>();
        tail.addLast( root );
        while( !tail.isEmpty( ) ){
            TreeNode<T> aux = tail.pop();
            if( aux.getLeft() != null ){
                tail.addLast( aux.getLeft());
            }
            if( aux.getRight() != null ){
                tail.addLast( aux.getRight());
            }
            out.add( aux.getInfo());
        }

        return out;
    }

    public boolean isLeaf( TreeNode<T> node ){

        return node.getRight() == null && node.getLeft() == null;
    }

    public TreeNode<T> findFather( TreeNode<T> node){
        if ( node == root ){
            return null;
        }else{
            TreeNode<T> aux = root;
            while( aux.getLeft() != node && aux.getRight() != node ){
                aux = comparator.compare(node.getInfo(), aux.getInfo()) < 0 ? aux.getLeft() : aux.getRight();
            }

            return aux;
        }
    }

    public int levelNode( TreeNode<T> node){

        return node == root ? 0 : levelNode( findFather( node)  ) + 1;
    }

    public int heigthNode( TreeNode<T> node){
        cont = 0;
        heigth( node, 0 );

        return cont;
    }

    private void heigth(TreeNode<T> node, int i) {
        if( node != null ){
            heigth( node.getLeft(), i + 1 );
            cont = i > cont ? i : cont;
            heigth( node.getRight(), i + 1 );
        }
    }

    public int heigthTree(){

        return heigthNode( root );
    }

    public int getGradeNode(TreeNode<T> node){
        if( node.getRight() != null && node.getLeft() != null )
            return 2;
        else if( node.getRight() != null || node.getLeft() != null )
            return 1;
        else
            return 0;
    }

    public T deleteNode( TreeNode<T> node){
        switch ( getGradeNode( node ) ){
            case 0 : return deleteLeaf( node );

            case 1 : return deleteWithSon( node );

            default: return deleteWithSons( node );
        }
    }

    private T deleteWithSons(TreeNode<T> node) {
        T out = node.getInfo();
        TreeNode<T> sustitute = node.getRight();
        TreeNode<T> fatherSustitute = null;
        while ( sustitute.getLeft() != null ) {
            fatherSustitute = sustitute;
            sustitute = sustitute.getLeft();
        }
        if ( fatherSustitute != null ) {
            fatherSustitute.setLeft( sustitute.getRight() );
            sustitute.setRight( node.getRight() );
        }
        sustitute.setLeft( node.getLeft() );

        TreeNode<T> father = findFather( node );
        if ( father == null ) {
            root = sustitute;
        } else if ( father.getLeft() == node ) {
            father.setLeft( sustitute );
        } else {
            father.setRight( sustitute );
        }

        return out;
    }

    private T deleteWithSon(TreeNode<T> node) {
        T out = node.getInfo();
        if ( node == root ) {
            root = root.getLeft() != null ? root.getLeft() : root.getRight();
        } else {
            TreeNode<T> father = findFather( node );
            if ( father.getLeft() == node ) {
                father.setLeft( node.getLeft() != null ? node.getLeft() : node.getRight() );
            } else {
                father.setRight( node.getLeft() != null ? node.getLeft() : node.getRight() );
            }
        }
        ///25:36
        return out;
    }

    private T deleteLeaf(TreeNode<T> node) {
        T out = node.getInfo();

        if( node == root ){
            root = null;
        }else{
            TreeNode<T> father = findFather( node );
            if( father.getLeft() == node ){
                father.setLeft( null );
            }else{
                father.setRight( null );
            }
        }

        return out;
    }

    public int weightTree(){

        return listInsort().size();
    }


    public int countNodesLevel(int level) {
        int aux = 0;
        for (int i = 0; i < listInsort().size(); i++) {
            T n = listInsort().get(i);
            if ( levelNode( findNode( n ) ) == level) {
                aux++;
            }
        }
        return aux;
    }



    public int greatGrandSon(TreeNode<T> node) {
        int aux = 0;
        if (!isLeaf(node)) {
            if (node.getLeft() != null) {
                aux += numberSon( node.getLeft().getLeft() );
                aux += numberSon( node.getLeft().getRight() );
            }
            if (node.getRight() != null) {
                aux += numberSon( node.getRight().getLeft() );
                aux += numberSon( node.getRight().getRight() );
            }
        }
        return aux;
    }

    private int numberSon(TreeNode<T> node) {

        return node != null ? 1 : 0;
    }

    public int weightNode( TreeNode<T> node) {
        return weightN(node);
    }

    private int weightN(TreeNode<T> node) {
        return node != null ? weightN(node.getLeft()) + weightN(node.getRight()) +1 : 0;
    }

    public boolean isBalanced(){
        TreeNode<T> auxr = root;
        TreeNode<T> auxl = root;
        int contr = 0;
        int contl = 0;
        while(auxr.getRight() != null){
            contr += 1;
            auxr = auxr.getRight();
        }
       while(auxl.getLeft() != null){
           contl += 1;
           auxl =auxl.getLeft();
       }
        cont = contl-contr;
        if (cont < 2  && cont > -2){
        return true;
        }
        return false;
    }

    public int countLeafsLevel (int level){
        int aux = 0;
        for (int i = 0; i < listInsort().size(); i++) {
            T n = listInsort().get(i);
            if ( levelNode( findNode( n ) ) == level) {
                if (isLeaf(findNode( n ))){
                    aux++;
                }
            }
        }
        return aux;
    }

    public boolean isStrictBinary(){

    TreeNode<T> aux;
        for (int i = 0; i < listInsort().size(); i++) {
            T n = listInsort().get(i);
            aux = findNode(n);
            if ( (aux.getLeft() == null && aux.getRight() != null) || (aux.getRight() == null && aux.getLeft() != null)) {
                return false;
            }
        }
        return true;
    }


    public boolean isPerfect(){
        int aux =heigthTree();
        for (int i = 0; i < listInsort().size(); i++) {
            T n = listInsort().get(i);
                if (isLeaf(findNode( n ))){
                    if (aux != levelNode(findNode( n ))){
                        return false;
                    }

            }
        }
        return true;
    }

    public ArrayList<T> getLeafs(){
        ArrayList<T> o = new ArrayList<>();
        for (int i = 0; i < listInsort().size(); i++) {
            T n = listInsort().get(i);
            if (isLeaf(findNode(n))) {
                o.add(n);
            }
        }

        return o;
    }

}