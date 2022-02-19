package server;

import database.Database;
import database.SequenceDatabase;
import objects.Sequence;
import org.apache.http.client.methods.CloseableHttpResponse;

import static server.Service.*;
import static server.Transformer.fromJson;
import static server.Transformer.toJson;
import static spark.Spark.*;

public class Server {

    public static void init(int port) {
        port(port);

        get("/sequences", (req, res) -> {
            System.out.println("[Sequence Server] GET /sequences");
            try (Database database = new Database()) {
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                return toJson(sequenceDatabase.getAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        post("/sequences", (req, res) -> {
            System.out.println("[Sequence Server] POST /sequences");
            try (Database database = new Database()) {
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                sequenceDatabase.hold(fromJson(Sequence.class, req.body()));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });

        post("/sequences/kill", (req, res) -> {
            System.out.println("[Sequence Server] POST /sequences/kill/");
            try (Database database = new Database()) {
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                sequenceDatabase.kill(fromJson(Sequence.class, req.body()).getSequenceName());
            }
            return null;
        });

        post("/sequences/release", (req, res) -> {
            System.out.println("[Sequence Server] POST /sequences/release/");

            try (var inventoryClient = new SequenceClient(INVENTORY, "/parts/invoice/push");
                 var accountingClient = new SequenceClient(ACCOUNTING, "/invoice/create");
                 Database database = new Database()) {

                inventoryClient.issuePost(req.body());
//                String response = accountingClient.issuePost(req.body());

                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                sequenceDatabase.kill(fromJson(Sequence.class, req.body()).getSequenceName());

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        });

        System.out.println("[Sequence Server] Listening on port " + port);
    }

}
