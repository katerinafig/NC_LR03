package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.interfaces.Space;

public class HotelFloor extends DwellingFloor {
    private int countStarsFloor;
    private static final int STAR_DEF = 1;

    //Конструктор может принимать количество помещений на этаже.
    public HotelFloor(int countFlats) {
        super(countFlats);
        this.countStarsFloor=STAR_DEF;
    }

    //Конструктор может принимать массив помещений этажа.
    public HotelFloor(Space[] arrayFlat) {
        super(arrayFlat);
        this.countStarsFloor=STAR_DEF;
    }

    //Метод получения количества звезд
    public int getCountStarsFloor() {
        return countStarsFloor;
    }

    //Метод изменения количества звезд
    public void setCountStarsFloor(int countStarsFloor) {
        this.countStarsFloor = countStarsFloor;
    }

    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("HotelFloor (").append(getSize()).append(", ");
        for (int i=0;i<getSize();i++) {
            finalString.append(getSpace(i).toString()).append(", ");
        }
        finalString.delete(finalString.length()-2,finalString.length()).append(")");
        return finalString.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof HotelFloor)) {
            return  false;
        }
        HotelFloor guest = (HotelFloor) obj;
        if(guest.getSize()!=getSize())return false;
        for (int i = 0; i < getSize(); i++) {
            if (!(guest.getSpace(i).equals(getSpace(i))) || !(guest.getCountStarsFloor()==countStarsFloor)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        int hashcode=getSize()^countStarsFloor;
        for(int i=0;i<getSize();i++)
        {
            hashcode^=getSpace(i).hashCode();
        }
        return hashcode;
    }
}
