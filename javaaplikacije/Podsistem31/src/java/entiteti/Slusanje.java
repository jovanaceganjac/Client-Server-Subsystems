/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "slusanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slusanje.findAll", query = "SELECT s FROM Slusanje s"),
    @NamedQuery(name = "Slusanje.findByIdSlu", query = "SELECT s FROM Slusanje s WHERE s.idSlu = :idSlu"),
    @NamedQuery(name = "Slusanje.findByIdKorisnika", query = "SELECT s FROM Slusanje s WHERE s.idKorisnika = :idKorisnika"),
    @NamedQuery(name = "Slusanje.findByIdAudioS", query = "SELECT s FROM Slusanje s WHERE s.idAudioS = :idAudioS"),
    @NamedQuery(name = "Slusanje.findByDatumOd", query = "SELECT s FROM Slusanje s WHERE s.datumOd = :datumOd"),
    @NamedQuery(name = "Slusanje.findByVremeOd", query = "SELECT s FROM Slusanje s WHERE s.vremeOd = :vremeOd"),
    @NamedQuery(name = "Slusanje.findBySekundeOd", query = "SELECT s FROM Slusanje s WHERE s.sekundeOd = :sekundeOd"),
    @NamedQuery(name = "Slusanje.findBySekundeUkupno", query = "SELECT s FROM Slusanje s WHERE s.sekundeUkupno = :sekundeUkupno")})
public class Slusanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdSlu")
    private Integer idSlu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKorisnika")
    private int idKorisnika;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdAudioS")
    private int idAudioS;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumOd")
    @Temporal(TemporalType.DATE)
    private Date datumOd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "VremeOd")
    private String vremeOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundeOd")
    private int sekundeOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundeUkupno")
    private int sekundeUkupno;

    public Slusanje() {
    }

    public Slusanje(Integer idSlu) {
        this.idSlu = idSlu;
    }

    public Slusanje(Integer idSlu, int idKorisnika, int idAudioS, Date datumOd, String vremeOd, int sekundeOd, int sekundeUkupno) {
        this.idSlu = idSlu;
        this.idKorisnika = idKorisnika;
        this.idAudioS = idAudioS;
        this.datumOd = datumOd;
        this.vremeOd = vremeOd;
        this.sekundeOd = sekundeOd;
        this.sekundeUkupno = sekundeUkupno;
    }

    public Integer getIdSlu() {
        return idSlu;
    }

    public void setIdSlu(Integer idSlu) {
        this.idSlu = idSlu;
    }

    public int getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(int idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public int getIdAudioS() {
        return idAudioS;
    }

    public void setIdAudioS(int idAudioS) {
        this.idAudioS = idAudioS;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public String getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(String vremeOd) {
        this.vremeOd = vremeOd;
    }

    public int getSekundeOd() {
        return sekundeOd;
    }

    public void setSekundeOd(int sekundeOd) {
        this.sekundeOd = sekundeOd;
    }

    public int getSekundeUkupno() {
        return sekundeUkupno;
    }

    public void setSekundeUkupno(int sekundeUkupno) {
        this.sekundeUkupno = sekundeUkupno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSlu != null ? idSlu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slusanje)) {
            return false;
        }
        Slusanje other = (Slusanje) object;
        if ((this.idSlu == null && other.idSlu != null) || (this.idSlu != null && !this.idSlu.equals(other.idSlu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Slusanje[ idSlu=" + idSlu + " ]";
    }
    
}
