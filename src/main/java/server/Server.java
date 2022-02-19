package server;

import database.Database;

import static spark.Spark.port;
import static spark.Spark.post;

public class Server {

    public static void init(int port){
        port(port);

        post("/invoice/create/:code", (req, res) -> {
            System.out.println("[Accounting Server] POST /invoice/create/" + req.params(":code"));
            try (Database database = new Database()) {




            }
            return null;
        });


        System.out.println("[Accounting Server] Listening on port " + port);
    }

}
