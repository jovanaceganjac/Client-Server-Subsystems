/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Korisnik
 */
@Embeddable
public class OmiljenepesmePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IdAudio")
    private int idAudio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKor")
    private int idKor;

    public OmiljenepesmePK() {
    }

    public OmiljenepesmePK(int idAudio, int idKor) {
        this.idAudio = idAudio;
        this.idKor = idKor;
    }

    public int getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(int idAudio) {
        this.idAudio = idAudio;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAudio;
        hash += (int) idKor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OmiljenepesmePK)) {
            return false;
        }
        OmiljenepesmePK other = (OmiljenepesmePK) object;
        if (this.idAudio != other.idAudio) {
            return false;
        }
        if (this.idKor != other.idKor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.OmiljenepesmePK[ idAudio=" + idAudio + ", idKor=" + idKor + " ]";
    }
    
}
