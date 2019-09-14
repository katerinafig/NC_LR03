package buildings.net.client;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.factory.DwellingFactory;
import buildings.factory.HotelFactory;
import buildings.factory.OfficeFactory;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.net.server.sequental.SerialServer;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.lang.reflect.InvocationTargetException;
import java.net.*;

import java.io.*;

public class SerialClient {
    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);
        try (Socket socket = new Socket(addr, SerialServer.PORT)) {
            System.out.println("socket = " + socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader inDataBuildings = new BufferedReader(new FileReader("src\\fileOne.txt"));
            BufferedReader inTypeBuildings = new BufferedReader(new FileReader("src\\fileTwo.txt"));
            PrintWriter writerResult = new PrintWriter("src\\fileResultSerial.txt");
            Class currentBuildingClass=null;
            Class currentFloorClass=null;
            Class currentSpaceClass=null;
            String currentTypeBuilding;
            String currentDateBuilding;
            while ((currentDateBuilding = inDataBuildings.readLine()) != null) {
                FileWriter writerCurrentBuilding = new FileWriter("src\\fileCurrentBuilding.txt");
                writerCurrentBuilding.write(currentDateBuilding);
                writerCurrentBuilding.close();
                currentTypeBuilding = inTypeBuildings.readLine();
                switch (currentTypeBuilding) {
                    case "Dwelling": {
                        currentBuildingClass = Dwelling.class;
                        currentFloorClass = DwellingFloor.class;
                        currentSpaceClass = Flat.class;
                    }
                        break;
                    case "Office":{
                        currentBuildingClass = OfficeBuilding.class;
                        currentFloorClass = OfficeFloor.class;
                        currentSpaceClass = Office.class;}
                    break;
                    case "Hotel":{
                        currentBuildingClass = Hotel.class;
                        currentFloorClass = HotelFloor.class;
                        currentSpaceClass = Flat.class;}
                    break;
                }
                FileReader readerCurrentBuilding = new FileReader("src\\fileCurrentBuilding.txt");
                Building currentBuilding=Buildings.readBuilding(readerCurrentBuilding, currentBuildingClass, currentFloorClass, currentSpaceClass);
                readerCurrentBuilding.close();
                System.out.println(currentBuilding);
                Buildings.serializeBuilding(currentBuilding, out);
                writerResult.println(in.readInt());
            }
            out.close();
            writerResult.close();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            System.out.println("closing");

        }
    }
}