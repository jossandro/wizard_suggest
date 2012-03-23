
package br.com.gaz.suggests.clusters.filter;

import org.jsoup.Jsoup;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.SimpleBatchFilter;
import weka.filters.unsupervised.attribute.NominalToString;

public class HTMLStripFilter extends SimpleBatchFilter {

    @Override
    public boolean setInputFormat(Instances instanceInfo)
     throws Exception {
        NominalToString toStr = new NominalToString();
        toStr.setInputFormat(instanceInfo);
        instanceInfo = Filter.useFilter(instanceInfo, toStr);

        return super.setInputFormat(instanceInfo);
    }


    @Override
    protected Instances determineOutputFormat(Instances i)
     throws Exception {
        return i;
    }


    @Override
    public Capabilities getCapabilities() {
        Capabilities result = super.getCapabilities();

        result.enable(Capability.NO_CLASS);
        result.enable(Capability.STRING_ATTRIBUTES);
        result.enable(Capability.NOMINAL_ATTRIBUTES);
        result.enable(Capability.MISSING_VALUES);

        return result;
    }


    @Override
    protected Instances process(Instances instances)
     throws Exception {
        for(int idx = 0; idx < instances.numInstances(); idx++) {
            Instance instance = instances.get(idx);

            for(int x = 0; x < instance.numAttributes(); x++) {
                if(instances.attribute(x).isString()) {
                    instance.setValue(x, Jsoup.parse(instance.stringValue(x)).text());
                }
            }
        }

        return instances;
    }


    @Override
    public String globalInfo() {
        return "Strip HTML tags";
    }
};