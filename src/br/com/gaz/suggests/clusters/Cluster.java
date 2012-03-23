
package br.com.gaz.suggests.clusters;

import br.com.gaz.suggests.clusters.filter.HTMLStripFilter;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.RemoveUseless;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class Cluster {

    private Instances instance;
    private boolean stripped = false;

    public Cluster(Instances instance) {
        this.instance = instance;
    }


    public Cluster withKMeans(int num) {
        this.stripTags();

        // Clusterizando
        SimpleKMeans group = new SimpleKMeans();

        try {
            group.setNumClusters(num);
            group.setDisplayStdDevs(true);

            this.instance = add_attribute(this.instance, "CLUSTER_SUGGEST", group);
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this;
    }


    public Instances toInstance() {
        return this.instance;
    }


    private static Instances add_attribute(Instances instances, String attribute_name, Clusterer cluster)
     throws Exception {
        // http://weka.wikispaces.com/Adding+attributes+to+a+dataset
        // Adicionando o atributo para os clusters
        Add attr = new Add();
        attr.setAttributeIndex("last");
        attr.setAttributeName(attribute_name);
        attr.setInputFormat(instances);
        instances = Filter.useFilter(instances, attr);

        cluster.buildClusterer(instances);

        // Adicionando o cluster na lista
        for(int x = 0; x < instances.numInstances(); x++) {
            instances.instance(x).setValue(
                instances.numAttributes() - 1,
                cluster.clusterInstance(instances.get(x))
            );
        }

        return instances;
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