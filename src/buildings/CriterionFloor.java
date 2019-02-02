package buildings;

import buildings.interfaces.Floor;

import java.util.Comparator;

 public class CriterionFloor implements Comparator<Floor> {
        @Override
        public int compare(Floor o1, Floor o2) {
            return Integer.compare(o2.getSize(), o1.getSize());
        }
    }

