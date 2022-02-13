import config.Config;
import database.Database;
import server.Server;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try{
            Config config = Config.getInstance(args[0]);
            Database.setDataSource(config.getDataSource());
            Server.init(config.getServerPort());
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Failed to load configuration file.");
        } catch (SQLException sql){
            sql.printStackTrace();
            System.out.println("Failed to initialize database.");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("An error occurred");
        }
    }


}
