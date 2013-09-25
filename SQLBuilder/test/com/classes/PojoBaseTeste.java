/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import com.annotations.Column;



/**
 *
 * @author paulo.gomes
 */
public abstract class PojoBaseTeste {

    @Column(name = "ID_SERVIDOR")
    protected Integer idServidor;
    
    @Column(name = "VERSION", nullable = false)
    protected Integer version;
    
    @Column(name = "SYNC")
    protected Boolean sync;

    public abstract Integer getId();

    public abstract void setId(Integer id);

    public Integer getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(Integer idServidor) {
        this.idServidor = idServidor;
    }

    public Integer getVersion() {
        if (version == null) {
            version = 0;
        }
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Boolean getSync() {
        if (sync == null) {
            sync = false;
        }
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }
}
