
package br.com.gaz.suggests.clusters.util;

import weka.clusterers.Clusterer;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;


public class Attribute {
    private String name;

    public Attribute(String attribute_name) {
        name = attribute_name;
    }

    public Instances addWithClustererInInstance(Instances instances, Clusterer cluster)
     throws Exception {
        // http://weka.wikispaces.com/Adding+attributes+to+a+dataset
        // Adicionando o atributo para os clusters
        Add attr = new Add();
        attr.setAttributeIndex("last");
        attr.setAttributeName(name);
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
}
