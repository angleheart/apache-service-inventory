package server;

import static config.Service.*;
import static server.GatewayClient.tryGetIssue;
import static server.GatewayClient.tryPostIssue;
import static spark.Spark.*;

public class Server {

    public static void init(int port) {
        port(port);

        get("/parts/:mfg/:number", (req, res) -> tryGetIssue(
                INVENTORY, "/parts/" + req.params(":mfg") + "/" + req.params(":number")
        ));

        post("/parts/update", (req, res) -> tryPostIssue(
                INVENTORY, "/parts/update", res.body()
        ));

        post("/customers", (req, res) -> tryPostIssue(
                CUSTOMER, "/customers", req.body()
        ));

        get("/customers/:query", (req, res) -> tryGetIssue(
                CUSTOMER, "/customers/" + req.params(":query")
        ));

        get("/vehicles/years", (req, res) -> tryGetIssue(
                VEHICLE, "/vehicles/years"
        ));

        get("/vehicles/makes/:year", (req, res) -> tryGetIssue(
                VEHICLE, "/vehicles/makes/" + req.params(":year")
        ));

        get("/vehicles/models/:year/:make", (req, res) -> tryGetIssue(
                VEHICLE, "/vehicles/models/" + req.params(":year") + "/" + req.params(":make")
        ));

        get("/vehicles/engines/:year/:make/:model", (req, res) -> tryGetIssue(
                VEHICLE,
                "/vehicles/engines/" +
                        req.params(":year") + "/" + req.params(":make") + "/" + req.params(":model")
        ));

        post("/vehicles", (req, res) -> tryPostIssue(
                VEHICLE, "/vehicles", req.body()
        ));

        post("/sequences", (req, res) -> tryPostIssue(
                SEQUENCE, "/sequences", req.body()
        ));

        get("/sequences", (req, res) -> tryGetIssue(
                SEQUENCE, "/sequences"
        ));

        post("/sequences/kill", (req, res) -> tryPostIssue(
                SEQUENCE, "/sequences/kill", req.body()
        ));

        System.out.println("[Gateway] listening on port " + port);
    }

}
