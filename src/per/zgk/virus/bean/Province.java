package per.zgk.virus.bean;

public class Province {
private String num;
private String name;
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Province() {
	super();
	// TODO Auto-generated constructor stub
}
public Province(String num, String name) {
	super();
	this.num = num;
	this.name = name;
}
@Override
public String toString() {
	return "Province [num=" + num + ", name=" + name + "]";
}


}
