
package br.com.gaz.suggests.clusters;

import java.util.HashMap;
import org.jsoup.Jsoup;
import weka.core.Instance;


public class Create {
    private HashMap content;
    private String[] attributes;

    public Create(HashMap content, String attributes[]) {
        this.content = content;
        this.attributes = attributes;

        for(String attr : this.attributes) {
            if(this.content.get(attr) != null) {
                System.out.println(Jsoup.parse((String) this.content.get(attr)).text());
            }
        }
    }


    public Instance toInstance() {
        throw new RuntimeException("");
    }
}
