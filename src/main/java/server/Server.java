package server;

import database.Database;
import database.SequenceDatabase;
import objects.Sequence;

import static server.Transformer.fromJson;
import static server.Transformer.toJson;
import static spark.Spark.*;

public class Server {

    public static void init(int port) {
        port(port);

        get("/sequences", (req, res) -> {
            System.out.println("[Sequence Server] GET /sequences");
            try(Database database = new Database()){
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                return toJson(sequenceDatabase.getAll());
            }
        });

        post("/sequences", (req, res) -> {
            System.out.println("[Sequence Server] POST /sequences");
            try(Database database = new Database()){
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                sequenceDatabase.hold(fromJson(Sequence.class, req.body()));
                return true;
            }
        });
        
        post("/sequences/kill", (req, res) -> {
            System.out.println("[Sequence Server] POST /sequences/kill/" + req.params(":name"));
            try(Database database = new Database()){
                SequenceDatabase sequenceDatabase = new SequenceDatabase(database.getConnection());
                sequenceDatabase.kill(fromJson(Sequence.class, req.body()).getSequenceName());
                return true;
            }
        });

        System.out.println("[Sequence Server] Listening on port " + port);
    }

}
