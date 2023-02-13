public class Laptop {
    String brand;
    int ram;
    int hdd;
    String os;
    String color;
    int serialNum;

    @Override
    public String toString() {
        return String.format("Brand: %s, RAM: %d ГБ, HDD: %d ГБ, OS: %s, color: %s, serialNum: %d", brand, ram, hdd, os,
                color, serialNum);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Laptop)) {
            return false;
        }
        Laptop laptop = (Laptop) obj;
        return brand.equals(laptop.brand) && ram == laptop.ram && hdd == laptop.hdd && os.equals(laptop.os) &&
                color.equals(laptop.color) && serialNum == laptop.serialNum;
    }

    @Override
    public int hashCode() {
        return brand.hashCode() + ram + hdd + os.hashCode() + color.hashCode() + serialNum;
    }
}
