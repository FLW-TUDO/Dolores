/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import flw.business.store.ArticleDynamics;
import flw.business.store.Storage;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author tilu
 */
@Entity
public class SerializableInteger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int valueInt = 0;
    @ManyToOne
    private Storage storage;
    @ManyToOne
    private ArticleDynamics articleDynamics;

    public SerializableInteger()
    {
	
    }

    public SerializableInteger(int value)
    {
	this();
	this.valueInt = value;
    }
    
    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
    
    public void setArticleDynamics(ArticleDynamics articleDynamics)
    {
        this.articleDynamics = articleDynamics;
    }

    public int getValue()
    {
	return valueInt;
    }

    public void setValue(int valueInt)
    {
	this.valueInt = valueInt;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "flw.business.util.SerializableInteger[ id=" + id + " ]";
    }
    
}
