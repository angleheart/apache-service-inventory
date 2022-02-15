package server;

import database.Database;
import database.VehicleDatabase;
import objects.Vehicle;

import java.sql.SQLException;

import static server.Transformer.fromJson;
import static server.Transformer.toJson;
import static spark.Spark.*;

public class Server {

    public static void init(int port) {
        port(port);

        get("/vehicles/years", (req, res) -> {
            System.out.println("[Vehicle Server] GET /vehicles/years");
            try (Database database = new Database()) {
                VehicleDatabase vehicleDatabase = new VehicleDatabase(database.getConnection());
                return toJson(
                        vehicleDatabase.getYears()
                );
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return null;
            }
        });

        get("/vehicles/makes/:year", (req, res) -> {
            System.out.println("[Vehicle Server] GET /vehicles/makes/" + req.params(":year"));
            try (Database database = new Database()) {
                VehicleDatabase vehicleDatabase = new VehicleDatabase(database.getConnection());
                return toJson(
                        vehicleDatabase.getMakes(req.params(":year"))
                );
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return null;
            }
        });

        get("/vehicles/models/:year/:make", (req, res) -> {
            System.out.println("[Vehicle Server] GET /vehicles/models/" +
                    req.params(":year") + "/" + req.params(":make"));
            try (Database database = new Database()) {
                VehicleDatabase vehicleDatabase = new VehicleDatabase(database.getConnection());
                return toJson(
                        vehicleDatabase.getModels(req.params(":year"), req.params(":make"))
                );
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return null;
            }
        });

        get("/vehicles/engines/:year/:make/:model", (req, res) -> {
            System.out.println("[Vehicle Server] GET /vehicles/engines/" +
                    req.params(":year") + "/" + req.params(":make") + "/" + req.params(":model"));
            try (Database database = new Database()) {
                VehicleDatabase vehicleDatabase = new VehicleDatabase(database.getConnection());
                return toJson(
                        vehicleDatabase.getEngines(req.params(":year"), req.params(":make"), req.params(":model"))
                );
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return null;
            }
        });

        post("/vehicles", (req, res) -> {
            try (Database database = new Database()) {
                VehicleDatabase vehicleDatabase = new VehicleDatabase(database.getConnection());
                vehicleDatabase.addVehicle(fromJson(Vehicle.class, req.body()));
                return true;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                return null;
            }
        });

        System.out.println("[Vehicle Server] Listening on port " + port);
    }

}
