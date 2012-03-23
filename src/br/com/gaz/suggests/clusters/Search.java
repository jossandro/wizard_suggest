package br.com.gaz.suggests.clusters;

import br.com.gaz.suggests.clusters.data.Type;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * Buscará os dados do banco de dados, através da interface dados.Tipox
 */
public class Search {

    public Instances list(Type type) {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("DatabaseUtils.props"));

            InstanceQuery query = new InstanceQuery();
            query.setUsername(properties.getProperty("database.user"));
            query.setPassword(properties.getProperty("database.pass"));

            query.setQuery(type.search());
            query.getSparseData();

            return query.retrieveInstances();
        } catch (Exception ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}