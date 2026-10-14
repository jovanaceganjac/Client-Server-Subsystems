/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Korisnik
 */
@Entity
@Table(name = "audiosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audiosnimak.findAll", query = "SELECT a FROM Audiosnimak a"),
    @NamedQuery(name = "Audiosnimak.findByIdAS", query = "SELECT a FROM Audiosnimak a WHERE a.idAS = :idAS"),
    @NamedQuery(name = "Audiosnimak.findByIdKorisnik", query = "SELECT a FROM Audiosnimak a WHERE a.idKorisnik = :idKorisnik"),
    @NamedQuery(name = "Audiosnimak.findByNaziv", query = "SELECT a FROM Audiosnimak a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Audiosnimak.findByTrajanje", query = "SELECT a FROM Audiosnimak a WHERE a.trajanje = :trajanje"),
    @NamedQuery(name = "Audiosnimak.findByDatum", query = "SELECT a FROM Audiosnimak a WHERE a.datum = :datum"),
    @NamedQuery(name = "Audiosnimak.findByVreme", query = "SELECT a FROM Audiosnimak a WHERE a.vreme = :vreme")})
public class Audiosnimak implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdAS")
    private Integer idAS;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKorisnik")
    private int idKorisnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Vreme")
    private String vreme;
    @JoinTable(name = "audiokategorija", joinColumns = {
        @JoinColumn(name = "IdAS", referencedColumnName = "IdAS")}, inverseJoinColumns = {
        @JoinColumn(name = "IdKat", referencedColumnName = "IdKat")})
    @ManyToMany
    private List<Kategorija> kategorijaList;

    public Audiosnimak() {
    }

    public Audiosnimak(Integer idAS) {
        this.idAS = idAS;
    }

    public Audiosnimak(Integer idAS, int idKorisnik, String naziv, int trajanje, Date datum, String vreme) {
        this.idAS = idAS;
        this.idKorisnik = idKorisnik;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datum = datum;
        this.vreme = vreme;
    }

    public Integer getIdAS() {
        return idAS;
    }

    public void setIdAS(Integer idAS) {
        this.idAS = idAS;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAS != null ? idAS.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audiosnimak)) {
            return false;
        }
        Audiosnimak other = (Audiosnimak) object;
        if ((this.idAS == null && other.idAS != null) || (this.idAS != null && !this.idAS.equals(other.idAS))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Audiosnimak[ idAS=" + idAS + " ]";
    }
    
}
