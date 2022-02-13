package server;

import database.Database;
import database.InventoryDatabase;
import objects.Invoice;
import objects.Part;

import java.sql.SQLException;

import static server.Transformer.fromJson;
import static server.Transformer.toJson;
import static spark.Spark.*;

public class Server {

    public static void init(int port){
        port(port);

        get("/parts/:mfg/:number", (req, res) -> {
            try(Database database = new Database()){
                InventoryDatabase inventoryDatabase = new InventoryDatabase(database.getConnection());
                return toJson(
                        inventoryDatabase.getParts(req.params(":mfg"), req.params(":number"))
                );
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });

        post("/parts/update", (req, res) -> {
            try(Database database = new Database()){
                InventoryDatabase inventoryDatabase = new InventoryDatabase(database.getConnection());
                return toJson(
                        inventoryDatabase.updatePart(fromJson(Part.class, req.body()))
                );
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });

        post("/parts/invoice/push", (req, res) -> {
            try(Database database = new Database()){
                InventoryDatabase inventoryDatabase = new InventoryDatabase(database.getConnection());
                inventoryDatabase.updateForInvoice(fromJson(Invoice.class, req.body()));
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });

        post("/parts/invoice/pull", (req, res) -> {
            try(Database database = new Database()){
                InventoryDatabase inventoryDatabase = new InventoryDatabase(database.getConnection());
                inventoryDatabase.updateForInvoice(fromJson(Invoice.class, req.body()), true);
                return true;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });

    }

}
