interface ILink<E>{//设置泛型避免安全隐患
	public void add(E e);//增加节点
	public int size();//返回链表个个数
	public boolean isEmpty();//判断链是否为空
	public Object [] toArray();//将集合元素以数组的方式返回
	public E get(int index);
	public void change(int index,E e);//修改节点
	public boolean isNodeExit(E data);//判断节点是否 存在
	public void delete(E e);//删除节点
	public void clean();
}
class LinkImpl<E> implements ILink<E>{
	private class Node{//保存节点数据关系
		private E data;//保存数据
		private Node next;//保存下一个引用；
		//private int index;
		public Node(E data){
			this.data = data;
		}
		//第一次调用：this= LinkImpl.root;
		//第二次调用：this= LinkImpl.root.next;
		public void addNode(Node newNode){
			if(this.next == null){//当前节点的下一个节点为空
				this.next = newNode;//，则下一个节点保存为新节点
				//this.index++;
			}else{//否则就去找下一个节点，直到找到空节点才保存
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
	//------------以下为Link类中定义的成员-----------
	private Node root;//保存根元素
	private int count;//保存节点个数
	private int foot;//描述操作数组的脚标
	private Object[] returnData;//返回的数据
	//------------以下为Link类中定义的方法-----------
	public void add (E e){
		if(e == null) return;//若数据为空，不保存
		Node newNode = new Node(e);
		if(this.root == null){//没有根节点，则保存第一个节点为根节点
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
		if(this.isEmpty()) return null;//空集合返回null；
		this.foot = 0;//脚标清零；
		this.returnData = new Object[this.count];//根据已有长度开辟数组
		//利node 类递归调用获取数据
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

interface IGoods{//商品标准
	public String getName();
	public double getPrice();
}
interface IShopCar{
	public void add(IGoods goods);//增加节点
	public void delete(IGoods goods);//删除节点
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

class Bag implements IGoods{//实现商品标准
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
		return("商品名字："+ this.name+",商品价格："+ this.price);
	}
}

class Book implements IGoods{//实现商品标准
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
		return("商品名字："+ this.name+",商品价格："+ this.price);
	}
}

public class javaDemo{
	public static void main(String args []){
		IShopCar sh = new ShopCarImpl();
		sh.add(new Bag("背包",13.4));
		sh.add(new Book("蓝书",44.7));
		sh.add(new Book("黄书",23.5));
		Cashier cas = new Cashier(sh);
		System.out.println("总价："+cas.getTotalPrice()+ "购买数量："+cas.getNum());

	}
}