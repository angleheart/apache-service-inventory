package server;

import database.Database;
import database.InvoiceDatabase;
import objects.Sequence;

import static spark.Spark.port;
import static spark.Spark.post;

public class Server {

    public static void init(int port) {
        port(port);

        post("/invoice/create/:code", (req, res) -> {
            System.out.println("[Accounting Server] POST /invoice/create/" + req.params(":code"));
            try (Database database = new Database()) {
                var invoiceBase = new InvoiceDatabase(database.getConnection());
                invoiceBase.releaseSequence(
                        Transformer.fromJson(Sequence.class, req.body()),
                        Integer.parseInt(req.params(":code"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });


        System.out.println("[Accounting Server] Listening on port " + port);
    }

}
