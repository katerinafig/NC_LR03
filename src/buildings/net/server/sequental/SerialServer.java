package buildings.net.server.sequental;

import buildings.Buildings;
import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.exception.BuildingUnderArrestExseption;
import buildings.interfaces.Building;
import buildings.office.OfficeBuilding;

import java.io.*;

import java.net.*;
import java.util.Random;

public class SerialServer {
    // Выбираем порт вне пределов 1-1024:
    public static final int PORT = 8080;
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
        try {//цикл
            Socket socket = s.accept();
            try {
                System.out.println("Connection accepted: " + socket);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Building currentBuilding ;
                while ((currentBuilding = Buildings.deserializeBuilding(in))!=null) {
                    try {
                        int price = getPrice(currentBuilding);
                        System.out.println(price);
                        out.writeInt(price);
                    }
                    catch (BuildingUnderArrestExseption e){
                        System.out.println("Арест");
                        out.writeInt(-1);
                    }

                }

            }
            catch (ClassNotFoundException e){
                System.out.println(e.getException());
                socket.close();
            }
            finally {
                System.out.println("closing");
                socket.close();
            }
        }
        finally {
            s.close();
        }
    }
}
