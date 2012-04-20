
package br.com.gaz.suggests;

import br.com.gaz.suggests.clusters.Cluster;
import br.com.gaz.suggests.clusters.Search;
import br.com.gaz.suggests.data.User;
import br.com.gaz.suggests.network.Builder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import norsys.netica.NeticaException;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        Search searching = new Search();
        Instances lista = searching.list(new User());

        Cluster cluster = new Cluster(lista);
        lista = cluster.withKMeans(5).toInstance();

        Builder network = new Builder(lista);


        FileWriter writer;

        try {
            network.create();


            writer = new FileWriter("./cluster_3.arff");
            writer.write(lista.toString());
            writer.flush();
            writer.close();
        }
        catch (NeticaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
