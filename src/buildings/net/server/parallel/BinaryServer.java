package buildings.net.server.parallel;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.exception.BuildingUnderArrestExseption;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class BinaryServer {
    static Random r = new Random();
    public static int getPrice(Building building) throws BuildingUnderArrestExseption {
        if (r.nextInt(9) == 0)
            throw new BuildingUnderArrestExseption();
        int price = building.getAreaSpace();
        if (building instanceof Hotel) {
            price *= 2000;
        } else if (building instanceof OfficeBuilding) {
            price *= 1500;
        } else if (building instanceof Dwelling) {
            price *= 1000;
        }
        return price;
    }
    static class Runner implements Runnable {
        private Socket socet;
        Runner(Socket socket) {
            this.socet = socket;
        }

        @Override
        public void run() {

            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(socet.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = null;
            try {
                out = new PrintWriter(socet.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Class currentBuildingClass=null;
            Class currentFloorClass=null;
            Class currentSpaceClass=null;
            String input, output;
            try {
                while ((input = Objects.requireNonNull(in).readLine()) != null) {
                    if (input.equalsIgnoreCase("exit")) break;
                    char temp = ' ';
                    switch (input.charAt(0)) {
                        case 'D':{
                            currentBuildingClass = Dwelling.class;
                            currentFloorClass = DwellingFloor.class;
                            currentSpaceClass = Flat.class;
                        }break;
                        case 'O':{
                            currentBuildingClass = OfficeBuilding.class;
                            currentFloorClass = OfficeFloor.class;
                            currentSpaceClass = Office.class;}
                        break;
                        case 'H':{
                            currentBuildingClass = Hotel.class;
                            currentFloorClass = HotelFloor.class;
                            currentSpaceClass = Flat.class;}
                        break;
                    }
                    input = input.replace(input.charAt(0), ' ');
                    FileWriter fileWriter = new FileWriter("src/file.txt");
                    fileWriter.write(input);
                    fileWriter.close();
                    FileReader fileReader = new FileReader("src/file.txt");
                    Building bulding = Buildings.readBuilding(fileReader,currentBuildingClass, currentFloorClass, currentSpaceClass);
                    fileReader.close();
                    Objects.requireNonNull(out).println(getPrice(bulding));
                }
            } catch (IOException | BuildingUnderArrestExseption | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(in).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socet.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException, BuildingUnderArrestExseption {
        ServerSocket servers = new ServerSocket(4444);
        Socket socet=servers.accept();
        Thread t = new Thread(new Runner(socet));
        t.start();
        servers.close();
    }
}
