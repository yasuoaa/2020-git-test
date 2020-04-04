interface ILink<E>{//���÷��ͱ��ⰲȫ����
	public void add(E e);//���ӽڵ�
	public int size();//�������������
	public boolean isEmpty();//�ж����Ƿ�Ϊ��
	public Object [] toArray();//������Ԫ��������ķ�ʽ����
	public E get(int index);
	public void change(int index,E e);//�޸Ľڵ�
	public boolean isNodeExit(E data);//�жϽڵ��Ƿ� ����
	public void delete(E e);//ɾ���ڵ�
	public void clean();
}
class LinkImpl<E> implements ILink<E>{
	private class Node{//����ڵ����ݹ�ϵ
		private E data;//��������
		private Node next;//������һ�����ã�
		//private int index;
		public Node(E data){
			this.data = data;
		}
		//��һ�ε��ã�this= LinkImpl.root;
		//�ڶ��ε��ã�this= LinkImpl.root.next;
		public void addNode(Node newNode){
			if(this.next == null){//��ǰ�ڵ����һ���ڵ�Ϊ��
				this.next = newNode;//������һ���ڵ㱣��Ϊ�½ڵ�
				//this.index++;
			}else{//�����ȥ����һ���ڵ㣬ֱ���ҵ��սڵ�ű���
				this.next.addNode(newNode);
			}
		}
		public void getNode(){
			LinkImpl.this.returnData[LinkImpl.this.foot++] = this.data;
			if(this.next != null){
				this.next.getNode();
			}
		}
		public E getIndexNode(int index){
			if(LinkImpl.this.foot++ == index){
				return this.data;
			}else{
				return this.next.getIndexNode(index);
			}
		}
		public void changeNode(int index,E e){
			if(LinkImpl.this.foot++ == index){
				this.data = e;
			}else{
				this.next.changeNode(index,e);
			}
		}
		public boolean contain(E data){
			if(this.data.equals(data))
				return true;
			else {
				if (this.next == null)
					return false;
				else
					return this.next.contain(data);
			}		
		}
		public void deleteNode(E data){
			if(this.next == null) return;
			if(this.next.data.equals(data)){
				this.next = this.next.next;
				LinkImpl.this.count--;
			}
			else 
				this.next.deleteNode(data);
		}
	}
	//------------����ΪLink���ж���ĳ�Ա-----------
	private Node root;//�����Ԫ��
	private int count;//����ڵ����
	private int foot;//������������Ľű�
	private Object[] returnData;//���ص�����
	//------------����ΪLink���ж���ķ���-----------
	public void add (E e){
		if(e == null) return;//������Ϊ�գ�������
		Node newNode = new Node(e);
		if(this.root == null){//û�и��ڵ㣬�򱣴��һ���ڵ�Ϊ���ڵ�
			this.root = newNode;
		}else{
			this.root.addNode(newNode);
		}
		this.count++;
	}
	public int size(){
		return this.count;
	}
	public boolean isEmpty(){
		return this.count ==0;
		//return this.root ==null;
	}
	public Object [] toArray(){
		if(this.isEmpty()) return null;//�ռ��Ϸ���null��
		this.foot = 0;//�ű����㣻
		this.returnData = new Object[this.count];//�������г��ȿ�������
		//��node ��ݹ���û�ȡ����
		this.root.getNode();
		return this.returnData;
	}
	public E get(int index){
		if(index >= this.count) return null;
		this.foot = 0;
		return this.root.getIndexNode(index);
	}
	public void change(int index,E e){
		if(index >= this.count) return  ;
		this.foot = 0;
		this.root.changeNode(index,e);
	}
	public boolean isNodeExit(E data){
		if(data == null) return false;
		return this.root.contain(data);
	}
	public void delete(E data){
		if(this.root.data.equals(data)){
			this.root = this.root.next;
			this.count--;
		}
		else
			this.root.deleteNode(data);
	}
	public void clean(){
		this.root = null;
		this.count =0;
	}
}

interface IGoods{//��Ʒ��׼
	public String getName();
	public double getPrice();
}
interface IShopCar{
	public void add(IGoods goods);//���ӽڵ�
	public void delete(IGoods goods);//ɾ���ڵ�
	public Object [] toArray();
}
class ShopCarImpl implements IShopCar{
	private ILink<IGoods> goodsLink = new LinkImpl<IGoods>();
	public void add(IGoods goods){
		this.goodsLink.add(goods);
	}
	public void delete(IGoods goods){
		this.goodsLink.delete(goods);
	}
	public Object [] toArray(){
		return this.goodsLink.toArray();
	}
}

class Cashier{
	private IShopCar shopCar;
	private double totalePrice;
	private int sum;
	public Cashier(IShopCar shopCar){
		this.shopCar = shopCar;
	}
	public int getNum(){
		return this.shopCar.toArray().length;
	}
	public double getTotalPrice(){
		Object [] result = this.shopCar.toArray();
		for(Object obj : result){
			IGoods goods = (IGoods) obj;
			this.totalePrice+=goods.getPrice();
		}
		return this.totalePrice;
	}
	
}

class Bag implements IGoods{//ʵ����Ʒ��׼
	private String name;
	private double price;
	public String getName(){
		return this.name;
	}
	public double getPrice(){
		return this.price;
	}
	public Bag(String name,double price){
			this.name=name;
			this.price = price;
	}
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!(obj instanceof Bag)){
			return false;
		}
		if(this == obj) return true;
		Bag cat = (Bag) obj;
		return this.name.equals(cat.name)&&this.price==cat.price;
	}
	public String toString(){
		return("��Ʒ���֣�"+ this.name+",��Ʒ�۸�"+ this.price);
	}
}

class Book implements IGoods{//ʵ����Ʒ��׼
	private String name;
	private double price;
	public String getName(){
		return this.name;
	}
	public double getPrice(){
		return this.price;
	}
	public Book(String name,double price){
			this.name=name;
			this.price = price;
	}
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!(obj instanceof Book)){
			return false;
		}
		if(this == obj) return true;
		Book cat = (Book) obj;
		return this.name.equals(cat.name)&&this.price==(cat.price);
	}
	public String toString(){
		return("��Ʒ���֣�"+ this.name+",��Ʒ�۸�"+ this.price);
	}
}

public class javaDemo{
	public static void main(String args []){
		IShopCar sh = new ShopCarImpl();
		sh.add(new Bag("����",13.4));
		sh.add(new Book("����",44.7));
		sh.add(new Book("����",23.5));
		Cashier cas = new Cashier(sh);
		System.out.println("�ܼۣ�"+cas.getTotalPrice()+ "����������"+cas.getNum());

	}
}