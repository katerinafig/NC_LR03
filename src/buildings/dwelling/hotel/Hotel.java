package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;


public class Hotel extends Dwelling {
    //Конструктор может принимать количество этажей и массив количества помещений по этажам
    public Hotel(int size,int [] countsFlat) {
    super(size,countsFlat);
    }
    //Конструктор может принимать массив этажей дома.
    public Hotel (Floor[] arrayFloors)
    {
        super(arrayFloors);
    }

    //Метод получения количества звезд отеля
    public int getCountStarsHotel()
    {
        int resultStarsHotel=0, currentStarsFloor;
        for(int i=0;i<getSize();i++)
        {
            if(getFloor(i).getClass()==HotelFloor.class)
            {
               currentStarsFloor =  ((HotelFloor)getFloor(i)).getCountStarsFloor();
                if(currentStarsFloor>resultStarsHotel)
                    resultStarsHotel=currentStarsFloor;
            }
        }
        return resultStarsHotel;
    }
    @Override
    public Space getBestSpace(){
        double [] coef = {0.25,0.5,1,1.25,1.5};
        int currentStarsFloor ;
        int resultSpaceNumber=0;
        double resultValue=0;
        for(int i=1;i<getSize();i++) {
            if(getFloor(i).getClass()==HotelFloor.class) {
                currentStarsFloor = ((HotelFloor) getFloor(i)).getCountStarsFloor();
                int currentAreaSpace = getFloor(i).getBestSpace().getArea();
                if (resultValue < currentAreaSpace * coef[currentStarsFloor-1]) {
                    resultValue = currentAreaSpace * coef[currentStarsFloor-1];
                    resultSpaceNumber = i;
                }
            }
        }
        return getSpace(resultSpaceNumber);
    }
    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append("HotelBuilding (").append(getSize()).append(", ");
        for (int i=0;i<getSize();i++) {
            finalString.append(getFloor(i).toString()).append(", ");
        }
        return finalString.delete(finalString.length()-2,finalString.length()).append(")").toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (!(obj instanceof Hotel)) {
            return false;
        }
        Hotel guest = (Hotel) obj;
        if(guest.getSize()!=getSize())return false;
        for (int i = 0; i < getSize(); i++) {
            if (!guest.getFloor(i).equals(getFloor(i))) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        int hashcode=getSize();
        for(int i=0;i<getSize();i++)
        {
            hashcode^=getFloor(i).hashCode();
        }
        return hashcode;
    }
}
