package buildings.net.server.sequental;

//: c15:JabberServer.java
// Очень простой сервер, который просто отсылает
// назад все, что посылает клиент.
// {RunByHand}
import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.exception.BuildingUnderArrestExseption;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.office.OfficeBuilding;

import java.io.*;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.Random;

public class BinaryServer {
    // Выбираем порт вне пределов 1-1024:
    public static final int PORT = 8070;
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
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            try (Socket socket = s.accept()) {
                System.out.println("Connection accepted: " + socket);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Building currentBuilding;
                while ((currentBuilding = Buildings.inputBuilding(in)) != null) {
                    try {
                        int price = getPrice(currentBuilding);
                        System.out.println(price);
                        out.writeInt(price);
                        out.close();
                    } catch (BuildingUnderArrestExseption e) {
                        System.out.println("Арест");
                        out.writeInt(0);
                    }

                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                System.out.println("closing");
            }

        }
        finally {
            s.close();
        }
    }
}