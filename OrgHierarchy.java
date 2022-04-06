import java.io.*;
import java.util.*;

// Tree node
class Node {
	Vector<Node> childrens;
	int key;
	Node myParent;
	int level;

	public Node(int id){
		key = id;
		childrens = new Vector<>();
		myParent = null;
		level = 1;
	}
	public Node(int id, Node parent){
		key = id;
		childrens = new Vector<>();
		myParent = parent;
		level = myParent.level + 1;
	}

	public void setChild(Node child){

		childrens.add(child);
	}

//  public Node myParent(){
//  	return
//  }
}

class NodeB {
	Node gNode;
	NodeB parentNode;
	int value;
	NodeB leftChild, rightChild;
	int depth;
	int height;
	boolean hired;

	public NodeB(Node c){
		gNode = c;
		value = c.key;
		leftChild = null;
		rightChild = null;
		parentNode = null;
		depth = 0;
		height = 0;
		hired =true;
	}
	public NodeB(Node c, NodeB parent){
		gNode = c;
		value = c.key;
		leftChild = null;
		rightChild = null;
		parentNode = parent;
		depth = parent.depth +1;
		height = 0;
		hired = true;
	}


}


class myAVL {
	NodeB rootNode;



	public myAVL(){
		rootNode = null;
	}

	public myAVL(Node c){
		rootNode = new NodeB(c);
		rootNode.leftChild = null;
		rootNode.rightChild = null;
	}

	private int maximum( int x , int y){
		return (x > y) ? x : y;
	}
	/*public void print(String prefix, NodeB n, boolean isLeft) {
		if (n != null) {
			System.out.println (prefix + (isLeft ? "|-- " : "\\-- ") + n.value);
			print(prefix + (isLeft ? "|   " : "    "), n.leftChild, true);
			print(prefix + (isLeft ? "|   " : "    "), n.rightChild, false);
		}
	}

	 */

	public NodeB find(int id,NodeB root){
		NodeB answer = new NodeB(rootNode.gNode);

		if(id == root.value){
			answer = root;
		}
		if (id < root.value){
			return find(id,root.leftChild);
		}
		else if (id > root.value){
			return find(id,root.rightChild);
		}

		return answer;

	}
	public NodeB findMin(NodeB node){
		if(node == null){
			return null;
		}
		if(node.leftChild == null){
			return node;
		}
		return findMin(node.leftChild);
	}

	public NodeB findMax(NodeB node){
		if(node == null){
			return null;
		}
		if(node.rightChild == null){
			return node;
		}
		return findMax(node.rightChild);
	}

	private int updateHeight(NodeB node){
		int temp;
		if (node.leftChild != null && node.rightChild !=null) {

			temp = 1 + maximum(node.leftChild.height, node.rightChild.height);
		}
		else if (node.rightChild == null && node.leftChild != null){
			temp = 1 + node.leftChild.height;
		}
		else if (node.rightChild != null && node.leftChild == null){
			temp = 1 + node.rightChild.height;
		}
		else {
			temp = 0;

		}

		return temp;
	}

	private int balanceF(NodeB node){
		int temp;
		if (node.leftChild != null && node.rightChild !=null) {
			temp = node.leftChild.height - node.rightChild.height;
		}
		else if (node.rightChild == null && node.leftChild != null){

			temp = node.leftChild.height +1;
		}
		else if (node.rightChild != null && node.leftChild == null){

			temp = -1 - node.rightChild.height;
		}
		else {

			temp = 0;
		}

		return temp;
	}

	private NodeB rotateR(NodeB node){

		NodeB left = node.leftChild;
		NodeB leftR  = left.rightChild;

		left.rightChild = node;
		node.leftChild = leftR;

		node.height = updateHeight(node);
		left.height = updateHeight(left);

		return left;

	}

	private NodeB rotateL(NodeB node){

		NodeB right = node.rightChild;
		NodeB rightL = right.leftChild;

		right.leftChild = node;
		node.rightChild = rightL;

		node.height = updateHeight(node);
		right.height = updateHeight(right);

		return right;

	}

	private NodeB balanceTRee(NodeB myNode, Node c){
		int balanceF = balanceF(myNode);

		if (myNode.leftChild != null && balanceF > 1){
			if (c.key < myNode.leftChild.value){
				return rotateR(myNode);
			}
			else if (c.key > myNode.leftChild.value){
				myNode.leftChild = rotateL(myNode.leftChild);
				return rotateR(myNode);
			}


		}

		if (myNode.rightChild != null && balanceF < -1 ) {
			if (c.key > myNode.rightChild.value){
				return rotateL(myNode);
			}
			else if (c.key < myNode.rightChild.value){
				myNode.rightChild = rotateR(myNode.rightChild);
				return rotateL(myNode);

			}


		}
		return myNode;
	}

	public NodeB insert(NodeB myNode,Node c){
		if (myNode.leftChild == null && myNode.rightChild == null){
			if (myNode.value > c.key){
				myNode.leftChild = new NodeB(c,myNode);
			}
			else if (myNode.value <  c.key){
				myNode.rightChild = new NodeB(c,myNode);
			}
		}
		else if (myNode.leftChild != null && myNode.rightChild == null){
			if (myNode.value > c.key){
				myNode.leftChild = insert(myNode.leftChild, c);
			}
			else if (myNode.value < c.key){
				myNode.rightChild = new NodeB(c,myNode);
			}
		}
		else if (myNode.leftChild ==  null && myNode.rightChild != null){
			if (myNode.value > c.key){
				myNode.leftChild = new NodeB(c,myNode);
			}
			else if (myNode.value < c.key){
				myNode.rightChild = insert(myNode.rightChild, c);
			}
		}
		else {
			if (myNode.value > c.key){
				myNode.leftChild = insert(myNode.leftChild,c);
			}
			else if (myNode.value < c.key){
				myNode.rightChild = insert(myNode.rightChild, c);
			}
		}

		myNode.height = updateHeight(myNode);

		return balanceTRee(myNode,c);


	}

	public void remove(NodeB thisNode, NodeB root){
		thisNode.hired = false;

	}
}


public class OrgHierarchy implements OrgHierarchyInterface{


	Node root;
	myAVL treeAVL;

	Vector keepTrack = new Vector();


	public boolean isEmpty(){
		return (size() == 0);
	}

	public int size(){
		return keepTrack.size();
	}

	public int level(int id) throws IllegalIDException, EmptyTreeException{
		if (!keepTrack.contains(id)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {

			NodeB temp = treeAVL.find(id, treeAVL.rootNode);
			return temp.gNode.level;
		}
	}

	public void hireOwner(int id) throws NotEmptyException{
		if(root != null){
			throw new NotEmptyException("Owner already hired");
		}
		else {
			root = new Node(id);

			treeAVL = new myAVL(root);
			keepTrack.add(id);
			//treeAVL.print("idk", treeAVL.rootNode, true);
			//System.out.println(keepTrack + "Ownercheck");

		}

	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
		if (!keepTrack.contains(bossid)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {
			NodeB boss = treeAVL.find(bossid, treeAVL.rootNode);
			//System.out.println("rootnodeval  "+ treeAVL.rootNode.value);
			//System.out.println(boss.value);
			Node temp = new Node(id, boss.gNode);

			treeAVL.rootNode = treeAVL.insert(treeAVL.rootNode, temp);
			boss.gNode.setChild(temp);
			keepTrack.add(id);
			//treeAVL.print("idk", treeAVL.rootNode, true);
			//System.out.println(keepTrack + "Hirecheck");

		}
	}

	public void fireEmployee(int id) throws IllegalIDException,EmptyTreeException{
		if (!keepTrack.contains(id)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {
			NodeB temp = treeAVL.find(id, treeAVL.rootNode);

			Node temp2 = temp.gNode;
			temp2.myParent.childrens.remove(temp2);
			treeAVL.remove(temp, treeAVL.rootNode);
			//System.out.println("check root 1 "+ treeAVL.rootNode.value);
			keepTrack.removeElement(id);
			//treeAVL.print("idk", treeAVL.rootNode, true);
			//System.out.println(keepTrack + "Fire1check");

		}

	}
	public void fireEmployee(int id, int manageid) throws IllegalIDException,EmptyTreeException{
		if (!keepTrack.contains(id) || !keepTrack.contains(manageid)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {
			//System.out.println(keepTrack + "Fire2UPcheck");
			keepTrack.removeElement(id);
			NodeB temp = treeAVL.find(id, treeAVL.rootNode);
			NodeB newManager = treeAVL.find(manageid, treeAVL.rootNode);
			for (int i = 0; i < temp.gNode.childrens.size(); i++) {
				newManager.gNode.childrens.add(temp.gNode.childrens.get(i));
				temp.gNode.childrens.get(i).myParent = newManager.gNode;
			}
			temp.gNode.myParent.childrens.remove(temp.gNode);

			treeAVL.remove(temp, treeAVL.rootNode);


		}
	}

	public int boss(int id) throws IllegalIDException,EmptyTreeException{

		if (!keepTrack.contains(id)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {
			NodeB temp = treeAVL.find(id, treeAVL.rootNode);
			if (temp.gNode.key == root.key)
				return -1;
			else
				return temp.gNode.myParent.key;
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException,EmptyTreeException{
		if (!keepTrack.contains(id1) || !keepTrack.contains(id2)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {

			Node temp1 = treeAVL.find(id1, treeAVL.rootNode).gNode;
			Node temp2 = treeAVL.find(id2, treeAVL.rootNode).gNode;
			int myAnswer;
			if (temp1.key == temp2.key){
				myAnswer = temp1.myParent.key;
			}else {
				while (temp1.key != temp2.key) {
					if (temp1.level == temp2.level) {
						temp1 = temp1.myParent;
						temp2 = temp2.myParent;
					} else if (temp1.key > temp2.key) {
						temp1 = temp1.myParent;
					} else if (temp1.key < temp2.key) {
						temp2 = temp2.myParent;
					}
				}
				myAnswer = temp1.key;
			}
			return myAnswer;
		}

	}

	private Vector queueBUild(Vector<Node> c){
		Vector out = new Vector();
		for (int i = 0; i < c.size(); i++) {
			out.addAll(c.get(i).childrens);
		}
		return out;
	}

	public String toString(int id) throws IllegalIDException, EmptyTreeException{
		if (!keepTrack.contains(id)){
			throw  new IllegalIDException("Illegal ID");
		}
		else if (keepTrack.size() == 0){
			throw new EmptyTreeException("Empty tree");
		}
		else {

			Node startNode = treeAVL.find(id, treeAVL.rootNode).gNode;
			Vector<Node> myQueue = new Vector();
			myQueue.add(startNode);

			String myOut = startNode.key + ",";
			while (!myQueue.isEmpty()) {
				Vector temp = queueBUild(myQueue);
				//myQueue.remove(startNode);
				myQueue = temp;
				//myQueue.sort();
				for (int i = 0; i < myQueue.size(); i++) {
					myOut = myOut + myQueue.get(i).key + " ";

				}
				myOut = myOut + ",";

			}

			String newOut = new String();
			String[] strArr = myOut.split(",");
			String[] intArr;
			for (int i = 0; i < strArr.length; i++) {
				intArr = strArr[i].split(" ");
				Vector temp = new Vector<>();
				for (int j = 0; j < intArr.length; j++) {
					temp.insertElementAt(Integer.parseInt(intArr[j]), j);
				}
				Collections.sort(temp);

				for (int j = 0; j < temp.size() - 1; j++) {
					newOut = newOut + temp.get(j) + " ";
				}
				newOut += temp.get(temp.size() - 1) + ",";

			}

			newOut = newOut.substring(0, newOut.length() - 1);

			return newOut;
		}

	}

}
