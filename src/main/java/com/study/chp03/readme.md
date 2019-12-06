
### chp3.查找

#### 3.1 符号表  
定义：   一种存储键值对的数据结构。  
支持插入：将一组新的键值对存入表中。查找：根据特定的键找到对应的值。    

性质：  
a.泛型，明确指定查找时键值类型以区分不同的角色。  
b.每个键只对应一个值，表中不允许重复的键  
c.插入的键发生冲突时，用新值替换旧的值  
d.不允许空键，规定不允许空值，遇到put空值，则相当于e的延迟删除。  
e.删除： 延迟删除，先置空后面调用delete删除；及时删除，直接delete。  默认延迟删除，但是不采用这种。  
f.迭代。keys()方法返回一组可迭代的键，方便遍历。  


#### 3.1.1 API定义

```
public class ST<Key,Value>{

	Value put(Key key,Value val)
	Value get(Key key)
	void delete(Key key)
	boolean contains(Key key)
	int size()
	Iterable<Key> keys()
}
```

#### 3.1.2 有序符号表
性质：键都是Comparable对象，实现两个键的大小比较。保持键的有序性实现更多实用的操作。  

API定义：  

```
public class ST<Key extends Comparable<Key>,Value>{
	void put(Key key,Value val)
	void delete(Key key) //删除key
	...
	Key max()
	Key min()
	Key floor(Key key) //小于等于key的最大键
	Key ceiling(Key key)//大于等于key的最小键
	int rank(Key key) //小于key的键的数量
	Key select(int k) //排名为k的键
	void deleteMin/Max(Key key) //删除最值键
	int size(Key lo,Key hi) //[lo...hi]之间键的数量
	Iterable<Key> keys(Key lo,Key hi> //[lo...hi]之间所有键，已排序
	Iterable<Key> keys()  //所有键的集合，已排序
}
```

键的等价性：a.compareTo(b)==0 和a.equals(b) 返回值相同。  
成本模型： 统计比较次数，没有比较的情况下，考虑访问数组的次数。  

#### 3.1.3 用例举例

性能测试，常规符号表性能对比  
接收一组输入 字符串，找出重复字符串个数和每个字符串出现的次数，并找出出现频率最高的字符串。

分别用不同大小的输入测试 不同方式实现的符号表实现该功能耗时。从而实现一种符号表实现快速查找插入和删除。

#### 3.1.4 无序链表的顺序查找
简单链表结构，每个节点存储一个键值对。用equals比较节点中的每个键

```
public class SequentialSearchST<Key,Value>{
	private Node first;
	class Node{
		Key key;
		Value val;
		Node next;
	}
	public Value get(Key key);
	void put(Key key,Value val);
}
```

#### 3.1.5 有序数组中的二分查找

** 代码实现 **  
`chp03.BinarySearchST` 

有序数组的符号表，其中键按照有序存入数组，其下标位置，就是值数组所在位置。  

** 分析 **  
rank()函数利用二分查找，实现元素在数组中的排名位置查找，进而实现元素键值的快速查找。  

a.如果表中存在该键，rank返回值就是该键在keys数组中的位置，也代表数组中小于它的键的数量；  
b.如果表中不存在该键，rank返回值依然代表表中小于它的键的数量。  



### 3.2二叉查找树
插入的灵活性和有序数组查找的高效性相结合的符号表实现。  

** 术语 **  
节点：构成key-value数据结构的抽象 ，key有顺序之分以实现快速查找  
链接：指向其他节点的链，节点包含的链接可以为空或者指向其他节点    

** 特点 **  
每个节点都只有左右两个链接，分别指向自己的左子节点和右子节点，每个链接指向了另一棵二叉树，这棵树的根节点就是被指向的节点。

**定义 **  
一棵二叉查找树是一棵二叉树，其中每个节点都含有一个Comparable的键且每个节点的键都大于其左子树中任意节点的键而小于右子树中所有节点的键。

#### 3.2.1 基本实现
** 3.2.1.1数据表示**  
每个节点含有一个键，一个值，一条左连接，一条右链接，一个节点计数器。安
节点计数器：代表了以该节点为根的子树结点总个数(包括该节点)，空连接的节点计数为0。  

一颗二查找树一组键(包括值)的集合，同一个集合可以用多颗不同的二叉树表示。

** 3.2.1.2 查找(属于减治法)**  
类似于二分查找  
命中：找到该键，并返回值。非命中：返回null。  

查找键的递归算法：  
如果树是空的，则查找未命中；如果被查找的键和根节点的键相等则查找命中，否则就递归的在适当的子树中继续查找。  
如果被查找的键较小，则在左子树中继续查找，否则在右子树中继续查找。查找结束的条件：找到一个节点的键和被查找的键相同或者当前子树变为空。

代码实现：  

```
public Value get(Key key){
	Node x=get(root,key);
	if(x!=null)
		return x.val;
	return null;
}
private Node get(Node x,Key key){
	if(x==null) return null;
	int cmp=key.compareTo(x.key);
	if(cmp==0) return x;
	else if(cmp>0) return get(x.right,key);
	else return get(x.left,key);
}
```
** 3.2.1.3 插入 **  
查找的键不在树中并结束于一条空连接，则把该空连接指向该新节点。
类似于递归查找。

** 3.2.1.4递归 **  
递归的理解：
递归调用前的代码理解为沿着树往下走，比较给定的键和每个节点的键，根据结果决定向左还是向右移动到下一个带比较的节点。  
递归后的代码理解为：沿着树向上爬，对于get()对应着一系列返回指令。对于put(),意味着重置搜索路径上父节点指向子节点的链接，  
并增加路径上每个节点中计数器的值。这里需要重置的链接就是最底层指向新节点的链接，重置更上层的链接可以通过比较语句来避免，只  
需要把路径上每个节点的计数器值加一即可。

```
public void put(Key key,Value val){
	root=put(root,key,val);
}
private Node put(Node x,Key key,Value val){
	if(x==null)return new Node(key,val,1);
	int cmp=key.compareTo(x.key);
	if(cmp>0) x.right=put(x.right,key,val);
	else if(cmp<0) x.left=put(x.left,key,val);
	else x.val=val;
	
	x.N=size(x.left)+size(x.right)+1;
	
	return x;
}
```

基本的二叉树的实现通常是非递归的，递归实现，为了为学习更复杂的实现做准备。  
#### 3.2.2 分析
最好情况是N个节点的二叉查找树是完全平衡的，每条空连接到根节点的距离都~ lgN.  
最坏情况下，搜索路径上有N个节点，退化成链表。  

插入键随机的二叉查找树和快速排序很类似，树的根节点就是快速排序中第一个划分出来的元素，因为它满足：  
左侧的键都比它小，右侧的键都比他大。 
这对于子树也是适用的，这和快速排序对子数组的递归排序（分治法）完全对应。

性质：再由N个随机构造的二叉查找树中，查找命中平均需要比较的次数为~2lnN (约 1.39lgN)

#### 3.2.3 有序性相关的方法与删除操作

** 3.2.3.1最大值和最小值**  

