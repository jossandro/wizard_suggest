
package br.com.gaz.suggests.network;

import norsys.netica.Environ;
import norsys.netica.Net;
import norsys.netica.NeticaException;
import norsys.neticaEx.aliases.Node;
import weka.core.Instances;
import weka.core.Attribute;

public class Builder {
    private Instances instance;


    public Builder(Instances instance) {
        this.instance = instance;
    }


    public Instances create()
     throws NeticaException {

        Node.setConstructorClass("norsys.neticaEx.aliases.Node");

        Environ env = new Environ("+BalardinJ/UNISC/Ex12-11-01,121/45019");
        Net network = new Net();
        network.setName("NetworkOne");

        Node node_attr = null;
        String values[] = null;

        for(int idx = 0; idx < this.instance.numAttributes(); idx++) {
            Attribute attr = this.instance.attribute(idx);

            if(attr.isNominal()) {
                System.out.println(attr.name());

                for(int key = 0; key < attr.numValues(); key++) {
                    if(!node_attr.getStateNames().contains(attr.name())) {
                        node_attr.setStateNames(attr.name());
                    }
                }

                node_attr = new Node(attr.name(), 0, network);

                System.out.println(node_attr.getStateNames());
            }
        }

        return this.instance;
    }
}
