package server;

import database.CustomerDatabase;
import database.Database;
import objects.Customer;

import java.sql.SQLException;

import static server.Transformer.fromJson;
import static server.Transformer.toJson;
import static spark.Spark.*;

public class Server {

    public static void init(int port){
        port(port);

        get("/customers/:query", (req, res) -> {
            try(Database database = new Database()){
                CustomerDatabase customerDatabase = new CustomerDatabase(database.getConnection());
                return toJson(
                        customerDatabase.query(req.params(":query"))
                );
            }catch(SQLException sql){
                sql.printStackTrace();
                return null;
            }
        });

        post("/customers", (req, res) -> {
            try(Database database = new Database()){
                CustomerDatabase customerDatabase = new CustomerDatabase(database.getConnection());
                customerDatabase.add(fromJson(Customer.class, req.body()));
                return true;
            }catch(SQLException sql){
                sql.printStackTrace();
                return null;
            }
        });

    }

}
