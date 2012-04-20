
package br.com.gaz.suggests.clusters;

import br.com.gaz.suggests.clusters.filter.HTMLStripFilter;
import br.com.gaz.suggests.clusters.util.Attribute;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class Cluster {

    private Instances instance;
    private boolean stripped = false;

    public Cluster(Instances instance) {
        this.instance = instance;
    }


    public Cluster withKMeans(int num) {
        // Clusterizando
        SimpleKMeans group = new SimpleKMeans();
        Attribute new_attr = new Attribute("CLUSTER_SUGGEST");

        try {
            group.setNumClusters(num);
            group.setDisplayStdDevs(true);

            this.instance = new_attr.addWithClustererInInstance(this.instance, group);
        }
        catch (Exception ex) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }


    public Cluster strToVectorFilter() {
        this.stripTags();

        // Tratando strings para vetores...
        StringToWordVector vector = new StringToWordVector();

        try {
            vector.setInputFormat(this.instance);
            vector.setUseStoplist(true);
            vector.setOutputWordCounts(true);

            this.instance = Filter.useFilter(this.instance, vector);
        }
        catch (Exception ex) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }


    public Instances toInstance() {
        return this.instance;
    }


    private Instances stripTags() {
        if(this.stripped) {
            return this.instance;
        }

        try {
            HTMLStripFilter strip = new HTMLStripFilter();
            strip.setInputFormat(this.instance);

            this.instance = Filter.useFilter(this.instance, strip);

            this.stripped = true;

        } catch (Exception ex) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.instance;
    }
}