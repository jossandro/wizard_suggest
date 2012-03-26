
package br.com.gaz.suggests;

import br.com.gaz.suggests.clusters.Cluster;
import br.com.gaz.suggests.clusters.Search;
import br.com.gaz.suggests.clusters.data.News;
import br.com.gaz.suggests.clusters.data.User;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;

public class Main {

    public static void main(String[] args) {
        Search searching = new Search();
        Instances lista = searching.list(new User());

        Cluster cluster = new Cluster(lista);
        lista = cluster.strToVectorFilter().withKMeans(5).toInstance();

        
    }
}
