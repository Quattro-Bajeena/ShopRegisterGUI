package fantasyshop;

public class Item {
    public int id;
    public String code;
    public String name;
    public int price;

    public Item(String code, String name, int price){
        this.code = code;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString(){
        return code + "|" + name + "|" + price;
    }

}
