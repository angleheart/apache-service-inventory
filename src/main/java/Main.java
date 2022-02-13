import config.Config;
import server.Server;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        try {
            Config config = Config.getInstance(args[0]);
            Server.init(config.getServerPort());
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Failed to load configuration file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred");
        }
    }

}
