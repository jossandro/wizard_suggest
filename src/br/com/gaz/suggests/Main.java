
package br.com.gaz.suggests;

import br.com.gaz.suggests.clusters.Cluster;
import br.com.gaz.suggests.clusters.Search;
import br.com.gaz.suggests.clusters.data.News;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        Search searching = new Search();
        Instances lista = searching.list(new News());

        Cluster cluster = new Cluster(lista);
        lista = cluster.strToVectorFilter().withKMeans(5).toInstance();

        FileWriter writer;

        try {
            writer = new FileWriter("./cluster_2.arff");
            writer.append(lista.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
