/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Audiosnimak;
import entiteti.Kategorija;
import entiteti.Korisnik;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Korisnik
 */
public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory cf;

    @Resource(lookup = "ps2slanje")
    private static Queue podsistem2slanje;

    @Resource(lookup = "ps2prijem")
    private static Queue podsistem2prijem;

    private static EntityManagerFactory emf2;
    private static EntityManager em2;

    private static EntityManagerFactory emf1;
    private static EntityManager em1;

    public static void main(String[] args) {
        emf2 = Persistence.createEntityManagerFactory("Podsistem2PU");
        em2 = emf2.createEntityManager();

        emf1 = Persistence.createEntityManagerFactory("Podsistem1PU");
        em1 = emf1.createEntityManager();

        try (JMSContext context = cf.createContext()) {
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(podsistem2prijem);

            System.out.println("Podsistem 2 je spreman i ceka na zahteve...");
            while (true) {
                try {
                    Message msg = consumer.receive();
                    if (msg instanceof TextMessage) {
                        TextMessage tm = (TextMessage) msg;
                        int zadatak = tm.getIntProperty("zahtev");
                        TextMessage response = context.createTextMessage();
                        String parametri;
                        switch (zadatak) {
                            case 5:
                                String naziv = tm.getText();
                                if (naziv == null || naziv.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje kategorije: Naziv ne sme biti prazan.");
                                    System.out.println("Primljen prazan ili null naziv za kategoriju.");
                                } else if (kreirajKategoriju(naziv)) {
                                    response.setText("Uspesno kreirana kategorija: " + naziv);
                                } else {
                                    response.setText("Neuspesno kreiranje kategorije: Kategorija vec postoji ili je doslo do greske.");
                                }
                                break;

                            case 6:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje audio snimak: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za audio snimak.");
                                } else if (kreirajAudioSnimak(parametri)) {
                                    response.setText("Uspesno kreiran audio snimak.");
                                } else {
                                    response.setText("Neuspesno kreiranje audio snimka!");
                                }
                                break;
                            
                            case 7:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesna promena naziva audio snimku: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za naziv audio snimka.");
                                } else if (promeniNazivAS(parametri)) {
                                    response.setText("Uspesno promenjen naziv audio snimka.");
                                } else {
                                    response.setText("Neuspesna promena naziva audio snimka!");
                                }
                                break;
                             case 8:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Dodavanje kategorije audio snimku: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za audio snimak.");
                                } else if (dodajKategoriju(parametri)) {
                                    response.setText("Uspesno dodata kategorija audio snimku.");
                                } else {
                                    response.setText("Neuspesno dodavanje kategorije audio snimku!");
                                }
                                break;
                            case 17:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Brisanje audio snimka: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (obrisiAS(parametri)) {
                                    response.setText("Uspesno obrisan audio snimak.");
                                } else {
                                    response.setText("Neuspesno brisanje audio snimka!");
                                }
                                break;
                            
                            case 20:
                                response.setText(dohvatiSveKategorije());
                                break;
                            case 21:
                                response.setText(dohvatiSveAudioSnimke());
                                break;
                            case 22:
                               int brojAS = Integer.parseInt(tm.getText());
                                response.setText(dohvatiKategorijuZaAS(brojAS));
                                break;

                            default:
                                System.out.println("Nepoznati zadatak: " + zadatak);
                                response.setText("Nepoznat zahtev.");
                        }
                        producer.send(podsistem2slanje, response);
                        System.out.println("Poslata potvrda za zahtev " + zadatak);
                    } else {
                        System.out.println("Primljena poruka nije tipa TextMessage.");
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "JMS greska u obradi poruke", ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska u JMS kontekstu", ex);
        } finally {
            if (em2 != null) {
                em2.close();
            }
            if (emf2 != null) {
                emf2.close();
            }
            if (em1 != null) {
                em1.close();
            }
            if (emf1 != null) {
                emf1.close();
            }
        }
    }

    private static boolean kreirajKategoriju(String nazivKat) {
        if (nazivKat == null || nazivKat.trim().isEmpty()) {
            System.out.println("Neuspešno kreiranje kategorije: Naziv je null ili prazan.");
            return false;
        }

        System.out.println("Kreiranje kategorije: " + nazivKat);
        List<Kategorija> kategorija = em2.createQuery("SELECT k FROM Kategorija k WHERE k.naziv = :naziv", Kategorija.class)
                .setParameter("naziv", nazivKat.trim())
                .getResultList();

        if (!kategorija.isEmpty()) {
            System.out.println("Kategorija već postoji: " + nazivKat);
            return false;
        }

        EntityTransaction transaction = em2.getTransaction();
        try {
            transaction.begin();
            Kategorija nova = new Kategorija();
            nova.setNaziv(nazivKat.trim());
            em2.persist(nova);
            em2.flush();
            transaction.commit();
            System.out.println("Kreirana nova kategorija: " + nazivKat);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju kategorije: " + nazivKat, e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje kategorije: " + nazivKat);
            return false;
        }
    }

    private static boolean kreirajAudioSnimak(String parametri) {
        if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za kreiranje audio snimka.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 5) {
            System.out.println("Primljeni neispravni parametri za audio snimak: " + parametri);
            return false;
        }

        int IdKorisnik;
        try {
            IdKorisnik = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID korisnika: " + delovi[0]);
            return false;
        }

        String naziv = delovi[1].trim();
        int trajanje;
        try {
            trajanje = Integer.parseInt(delovi[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravno trajanje: " + delovi[2]);
            return false;
        }

        String dat = delovi[3].trim();
        if (dat.isEmpty()) {
            System.out.println("Neispravan datum: prazan unos.");
            return false;
        }

        Date datum;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            datum = dateFormat.parse(dat);
        } catch (ParseException e) {
            System.out.println("Neispravan format datuma: " + dat);
            return false;
        }

        String vreme = delovi[4].trim();
        if (IdKorisnik <= 0 || naziv.isEmpty() || trajanje < 0 || vreme.isEmpty()) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        List<Korisnik> korisnici = em1.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnik = :IdKorisnik", Korisnik.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .getResultList();

        if (korisnici.isEmpty()) {
            System.out.println("Korisnik sa zadatim Id Korisnik ne postoji");
            return false;
        }

        EntityTransaction transaction = em2.getTransaction();
        try {
            transaction.begin();
            Audiosnimak noviAudioSnimak = new Audiosnimak();
            noviAudioSnimak.setIdKorisnik(IdKorisnik);
            noviAudioSnimak.setNaziv(naziv);
            noviAudioSnimak.setTrajanje(trajanje);
            noviAudioSnimak.setDatum(datum);
            noviAudioSnimak.setVreme(vreme);

            em2.persist(noviAudioSnimak);
            em2.flush(); 
            transaction.commit();
            System.out.println("Kreiran novi audio snimak: " + naziv);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju audio snimka: " + naziv, e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje audio snimka: " + naziv);
            return false;
        }
    }
    
    private static boolean promeniNazivAS(String parametri) {
         String[] delovi = parametri.split("!");
         if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri za promenu email adrese!");
            return false;
        }

        int IDAudioSnimka;
        try {
            IDAudioSnimka = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan Id audio snimka!");
            return false;
        }
        String noviNaziv = delovi[1].trim();
        if (IDAudioSnimka<=0 || noviNaziv.isEmpty()) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        List<Audiosnimak> audiosnimci = em2.createQuery("SELECT a FROM Audiosnimak a WHERE a.idAS = :IDAudioSnimka", Audiosnimak.class)
                .setParameter("IDAudioSnimka", IDAudioSnimka)
                .getResultList();

        if (audiosnimci.isEmpty()) {
            System.out.println("Ne postoji audio snimak sa zadatim Id!");
            return false;
        }
        
        EntityTransaction transaction = em2.getTransaction();
        try {
            transaction.begin();
            Audiosnimak audioSnimak=audiosnimci.get(0);
            audioSnimak.setNaziv(noviNaziv);
            em2.persist(audioSnimak);
            em2.flush(); 
            transaction.commit();
            System.out.println("Promenjen naziv audio snimku sa Id: "+ IDAudioSnimka);
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešna promena naziva audio snimku!");
            return false;
        }
    }
    
    
    
    
    private static String dohvatiSveKategorije() {
    try {
        List<Kategorija> kategorije = em2.createQuery("SELECT k FROM Kategorija k", Kategorija.class).getResultList();
        if (kategorije.isEmpty()) {
            System.out.println("Nema kategorija u bazi.");
            return "Nema kategorija u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Kategorija k : kategorije) {
            response.append("ID: ").append(k.getIdKat())
                    .append(", Naziv: ").append(k.getNaziv())
                    .append("\n");
        }
        System.out.println("Dohvaćene sve kategorije: " + response.toString());
        return response.toString();
    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greška pri dohvatanju kategorija", e);
        return "Greška pri dohvatanju kategorija.";
    }
}

private static String dohvatiSveAudioSnimke() {
    try {
        List<Audiosnimak> audiosnimci = em2.createQuery("SELECT a FROM Audiosnimak a", Audiosnimak.class).getResultList();
        if (audiosnimci.isEmpty()) {
            System.out.println("Nema audio snimaka u bazi.");
            return "Nema audio snimaka u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Audiosnimak a : audiosnimci) {
            response.append("ID: ").append(a.getIdAS())
                    .append(", ID Korisnik: ").append(a.getIdKorisnik())
                    .append(", Naziv: ").append(a.getNaziv())
                    .append(", Trajanje: ").append(a.getTrajanje())
                    .append(", Datum: ").append(a.getDatum())
                    .append(", Vreme: ").append(a.getVreme())
                    .append("\n");
        }
        System.out.println("Dohvaćeni svi audio snimci: " + response.toString());
        return response.toString();
    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greška pri dohvatanju audio snimaka", e);
        return "Greška pri dohvatanju audio snimaka.";
    }
}

private static boolean dodajKategoriju(String parametri) {
    if (parametri == null || parametri.trim().isEmpty()) {
        System.out.println("Primljeni neispravni parametri za dodavanje kategorije.");
        return false;
    }

    String[] delovi = parametri.split("!");
    if (delovi.length != 2) {
        System.out.println("Primljeni neispravni parametri!");
        return false;
    }

    int IdAudioSnimak;
    try {
        IdAudioSnimak = Integer.parseInt(delovi[0].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID audio snimka: " + delovi[0]);
        return false;
    }

    int IdKategorija;
    try {
        IdKategorija = Integer.parseInt(delovi[1].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID kategorije: " + delovi[1]);
        return false;
    }

    List<Audiosnimak> audiosnimci = em2.createQuery(
        "SELECT a FROM Audiosnimak a WHERE a.idAS = :IdAudioSnimak", Audiosnimak.class)
        .setParameter("IdAudioSnimak", IdAudioSnimak)
        .getResultList();

    if (audiosnimci.isEmpty()) {
        System.out.println("Audio snimak sa zadatim ID-jem ne postoji.");
        return false;
    }

    List<Kategorija> kategorije = em2.createQuery(
        "SELECT k FROM Kategorija k WHERE k.idKat = :IdKategorija", Kategorija.class)
        .setParameter("IdKategorija", IdKategorija)
        .getResultList();

    if (kategorije.isEmpty()) {
        System.out.println("Kategorija sa zadatim ID-jem ne postoji.");
        return false;
    }

    Audiosnimak snimak = audiosnimci.get(0);
    Kategorija novaKategorija = kategorije.get(0);

    // Provera da li snimak već ima tu kategoriju
    if (snimak.getKategorijaList().contains(novaKategorija)) {
        System.out.println("Audio snimak već ima ovu kategoriju.");
        return false;
    }

    EntityTransaction transaction = em2.getTransaction();
    try {
        transaction.begin();
        snimak.getKategorijaList().add(novaKategorija);

        em2.merge(snimak);  
        transaction.commit();

        System.out.println("Dodata kategorija audio snimku sa ID: " + IdAudioSnimak);
        return true;
    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri dodavanju kategorije!", e);
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        System.out.println("Neuspešno dodavanje kategorije audio snimku sa ID: " + IdAudioSnimak);
        return false;
    }
}

   private static boolean obrisiAS(String parametri) {
    if (parametri == null || parametri.trim().isEmpty()) {
        System.out.println("Primljeni neispravni parametri za brisanje audio snimka.");
        return false;
    }

    String[] delovi = parametri.split("!");
    if (delovi.length != 2) {
        System.out.println("Primljeni neispravni parametri!");
        return false;
    }

    int IdAudioSnimak;
    try {
        IdAudioSnimak = Integer.parseInt(delovi[0].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID audio snimka: " + delovi[0]);
        return false;
    }

    int IdKorisnik;
    try {
        IdKorisnik = Integer.parseInt(delovi[1].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID korisnika: " + delovi[1]);
        return false;
    }

    List<Audiosnimak> audiosnimci = em2.createQuery(
        "SELECT a FROM Audiosnimak a WHERE a.idAS = :IdAudioSnimak AND a.idKorisnik = :IdKorisnik", Audiosnimak.class)
        .setParameter("IdAudioSnimak", IdAudioSnimak)
        .setParameter("IdKorisnik", IdKorisnik)
        .getResultList();

    if (audiosnimci.isEmpty()) {
        System.out.println("Audio snimak sa zadatim ID-jem ne postoji za tog korisnika.");
        return false;
    }

    Audiosnimak snimak = audiosnimci.get(0);

    EntityTransaction transaction = em2.getTransaction();
    try {
        transaction.begin();
        if (!em2.contains(snimak)) {
            snimak = em2.merge(snimak);
        }
        em2.remove(snimak);
        transaction.commit();

        System.out.println("Obrisan audio snimak sa ID: " + IdAudioSnimak);
        return true;
    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri brisanju audio snimka!", e);
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        System.out.println("Neuspešno brisanje audio snimka sa ID: " + IdAudioSnimak);
        return false;
    }
}

private static String dohvatiKategorijuZaAS(int IdAS) {
    try {
        Audiosnimak audio = em2.find(Audiosnimak.class, IdAS);

        if (audio == null) {
            return "Audio snimak sa datim ID ne postoji.";
        }

        List<Kategorija> kategorije = audio.getKategorijaList();

        if (kategorije == null || kategorije.isEmpty()) {
            return "Audio snimak nema kategorije.";
        }

        StringBuilder response = new StringBuilder("Kategorije za audio snimak " + IdAS + ":\n");

        for (Kategorija k : kategorije) {
            response.append(" - ID: ")
                    .append(k.getIdKat())
                    .append(", Naziv: ")
                    .append(k.getNaziv())
                    .append(System.lineSeparator());
        }

        return response.toString().trim();

    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
                "Greška pri dohvatanju kategorija za audio snimak", e);
        return "Greška pri dohvatanju kategorija.";
    }
}

}



