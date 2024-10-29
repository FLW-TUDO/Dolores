/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tilu
 */
public class MapCopier<K, V> {

    public Map<K, V> copyToMap(Map<K, V> src) {
        Map<K, V> dest = new HashMap<K, V>(src) {};
        return dest;
    }
}
