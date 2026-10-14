/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem31;
import entiteti.Audiosnimak;
import entiteti.Korisnik;
import entiteti.Ocena;
import entiteti.Omiljenepesme;
import entiteti.OmiljenepesmePK;
import entiteti.Paket;
import entiteti.Pretplata;
import entiteti.Slusanje;
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

    @Resource(lookup = "ps3slanje")
    private static Queue podsistem3slanje;

    @Resource(lookup = "ps3prijem")
    private static Queue podsistem3prijem;

    private static EntityManagerFactory emf3;
    private static EntityManager em3;

    private static EntityManagerFactory emf1;
    private static EntityManager em1;
    
     private static EntityManagerFactory emf2;
    private static EntityManager em2;

    public static void main(String[] args) {
        emf3 = Persistence.createEntityManagerFactory("Podsistem31PU");
        em3 = emf3.createEntityManager();
        
        emf1 = Persistence.createEntityManagerFactory("Podsistem1PU");
        em1 = emf1.createEntityManager();
        
        emf2 = Persistence.createEntityManagerFactory("Podsistem2PU");
        em2 = emf2.createEntityManager();
        

        try (JMSContext context = cf.createContext()) {
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(podsistem3prijem);

            System.out.println("Podsistem3 started, listening for requests...");
            while (true) {
                try {
                    Message msg = consumer.receive();
                    if (msg instanceof TextMessage) {
                        TextMessage tm = (TextMessage) msg;
                        int zadatak = tm.getIntProperty("zahtev");
                        TextMessage response = context.createTextMessage();
                        String parametri;
                        switch (zadatak) {
                            case 9:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje paketa: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za paket.");
                                } else if (kreirajPaket(parametri)) {
                                    response.setText("Uspesno kreiran paket.");
                                } else {
                                    response.setText("Neuspesno kreiranje paketa!");
                                }
                                break;
                            case 10:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesna promena cene paketa: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (promeniCenuPaketa(parametri)) {
                                    response.setText("Uspesno promenjena cena paketa.");
                                } else {
                                    response.setText("Neuspesna promena cene paketa!");
                                }
                                break;
                            case 11:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje pretplate: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (kreirajPretplatu(parametri)) {
                                    response.setText("Uspesno kreirana pretplata.");
                                } else {
                                    response.setText("Neuspesno kreiranje pretplate!");
                                }
                                break;
                                
                            case 12:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje slusanja: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (kreirajSlusanje(parametri)) {
                                    response.setText("Uspesno kreirano slusanje.");
                                } else {
                                    response.setText("Neuspesno kreiranje slusanja!");
                                }
                                break;
                                
                             case 13:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno dodavanje audio snimka u omiljene: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (dodajASUOmiljene(parametri)) {
                                    response.setText("Uspesno dodavanje audio snimka u omiljene.");
                                } else {
                                    response.setText("Neuspesno dodavanje audio snimka u omiljene!");
                                }
                                break;
                            case 14:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje ocene za audio snimak: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (dodajOcenuAS(parametri)) {
                                    response.setText("Uspesno dodavanje ocene audio snimku.");
                                } else {
                                    response.setText("Neuspesno dodavanje ocene audio snimku!");
                                }
                                break;
                                
                             case 15:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesna promena ocene za audio snimak: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (promeniOcenuAS(parametri)) {
                                    response.setText("Uspesna promena ocene audio snimku.");
                                } else {
                                    response.setText("Neuspesna promena ocene audio snimku!");
                                }
                                break;   
                                
                            case 16:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno brisanje ocene za audio snimak: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri.");
                                } else if (obrisiOcenu(parametri)) {
                                    response.setText("Uspesno brisanje ocene audio snimku.");
                                } else {
                                    response.setText("Neuspesno brisanje ocene audio snimku!");
                                }
                                break;        
                                
                            case 23:
                                response.setText(dohvatiSvePakete());
                                break;
                                
                            case 24:
                                int IDKorisnika=Integer.parseInt(tm.getText());
                                response.setText(dohvatiPretplate(IDKorisnika));
                                break;
                                
                            case 25:
                                int IdAudiosnimka=Integer.parseInt(tm.getText());
                                response.setText(dohvatiSlusanja(IdAudiosnimka));
                                break;
                                
                            case 26:
                                int IdAudiosnimak=Integer.parseInt(tm.getText());
                                response.setText(dohvatiOcene(IdAudiosnimak));
                                break;
                            
                            case 27:
                                int IdKorisnik=Integer.parseInt(tm.getText());
                                response.setText(dohvatiOmiljene(IdKorisnik));
                                break;
                                
                          
                            default:
                                System.out.println("Nepoznati zadatak: " + zadatak);
                                response.setText("Nepoznat zahtev.");
                        }
                        producer.send(podsistem3slanje, response);
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
            if (em3 != null) {
                em3.close();
            }
            if (emf3 != null) {
                emf3.close();
            }
        }
    }

   

    private static boolean kreirajPaket(String parametri) {
        if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za kreiranje paketa.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri za kreiranje paketa: " + parametri);
            return false;
        }

        String naziv = delovi[0].trim();
       
        double cena;
        try {
            cena = Double.parseDouble(delovi[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravna cena: " + delovi[2]);
            return false;
        }
      
        if (naziv.isEmpty() || cena<0) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Paket noviPaket = new Paket();
            noviPaket.setNaziv(naziv);
            noviPaket.setCena(cena);
            em3.persist(noviPaket);
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Kreiran novi paket: " + naziv);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju paketa: " + naziv, e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje paketa: " + naziv);
            return false;
        }
    }

    private static boolean promeniCenuPaketa(String parametri) {
        String[] delovi = parametri.split("!");
         if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri za promenu cene paketa!");
            return false;
        }

        int IDPaketa;
        try {
            IDPaketa = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan Id paketa!");
            return false;
        }
        double novaCena;
        try {
            novaCena = Double.parseDouble(delovi[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravna cena!");
            return false;
        }
        if (IDPaketa<=0 || novaCena<0) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        List<Paket> paketi = em3.createQuery("SELECT p FROM Paket p WHERE p.idPaket = :IDPaketa", Paket.class)
                .setParameter("IDPaketa", IDPaketa)
                .getResultList();

        if (paketi.isEmpty()) {
            System.out.println("Ne postoji paket sa zadatim Id!");
            return false;
        }
        
        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Paket paket=paketi.get(0);
            paket.setCena(novaCena);
            em3.persist(paket);
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Promenjena cena paketa sa Id: "+ IDPaketa);
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešna promena cene paketa!");
            return false;
        }

    }
    
    
    private static String dohvatiSvePakete() {
    try {
        List<Paket> paketi = em3.createQuery("SELECT p FROM Paket p", Paket.class).getResultList();
        if (paketi.isEmpty()) {
            System.out.println("Nema paketa u bazi.");
            return "Nema paketa u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Paket p : paketi) {
            response.append("ID: ").append(p.getIdPaket())
                    .append(", Naziv: ").append(p.getNaziv())
                    .append(", Cena: ").append(p.getCena())
                    .append("\n");
        }
        return response.toString();
    } catch (Exception e) {
        return "Greška pri dohvatanju paketa.";
    }
}

    private static boolean kreirajPretplatu(String parametri) {
      if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za kreiranje pretplate.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 5) {
            System.out.println("Primljeni neispravni parametri za pretplatu: " + parametri);
            return false;
        }

        int IdKorisnik;
        try {
            IdKorisnik = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID korisnika: " + delovi[0]);
            return false;
        }
        
        int IdPaket;
        try {
            IdPaket = Integer.parseInt(delovi[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID paketa: " + delovi[1]);
            return false;
        }

        String dat = delovi[2].trim();
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

        String vreme = delovi[3].trim();
        double cena = Double.parseDouble(delovi[4].trim());
        if (IdKorisnik <= 0 || IdPaket<=0 || vreme.isEmpty() || cena<0) {
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
        
        List<Paket> paketi = em3.createQuery("SELECT p FROM Paket p WHERE p.idPaket = :IdPaket", Paket.class)
                .setParameter("IdPaket", IdPaket)
                .getResultList();

        if (paketi.isEmpty()) {
            System.out.println("Paket sa zadatim Id Paket ne postoji");
            return false;
        }

        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Pretplata novaPretplata=new Pretplata();
            novaPretplata.setIdKorisnik(IdKorisnik);
            novaPretplata.setIdPaket(paketi.get(0));
            novaPretplata.setDatumPoc(datum);
            novaPretplata.setVremePoc(vreme);
            novaPretplata.setCena(cena);
            
          
            em3.persist(novaPretplata); 
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Kreirana nova pretplata.");
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju pretplate. ");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje pretplate." );
            return false;
        }
    }

    private static boolean dodajASUOmiljene(String parametri) {
         if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za dodavanje audio snimka u omiljene.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri.");
            return false;
        }
        
        int IDAudiosnimak;
        try {
            IDAudiosnimak = Integer.parseInt(delovi[0].trim());
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
        
        List<Korisnik> korisnici = em1.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnik = :IdKorisnik", Korisnik.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .getResultList();

        if (korisnici.isEmpty()) {
            System.out.println("Korisnik sa zadatim Id Korisnik ne postoji");
            return false;
        }
        
        List<Audiosnimak> audiosnimci = em2.createQuery("SELECT a FROM Audiosnimak a WHERE a.idAS = :IDAudiosnimak", Audiosnimak.class)
                .setParameter("IDAudiosnimak", IDAudiosnimak)
                .getResultList();

        if (audiosnimci.isEmpty()) {
            System.out.println("Audio snimak sa zadatim Id AudioSnimak ne postoji");
            return false;
        }
        
         List<Omiljenepesme> omiljenepesme = em3.createQuery("SELECT o FROM Omiljenepesme o WHERE o.omiljenepesmePK.idKor = :IdKorisnik AND o.omiljenepesmePK.idAudio=:IDAudiosnimak", Omiljenepesme.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .setParameter("IDAudiosnimak", IDAudiosnimak)
                .getResultList();

        if (!(omiljenepesme.isEmpty())) {
            System.out.println("Ovaj audio snimak se vec nalazi u omiljenim pesmama zadatog korisnika.");
            return false;
        }
        
        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            OmiljenepesmePK omiljenePK=new OmiljenepesmePK(IDAudiosnimak, IdKorisnik);
            Omiljenepesme omiljene=new Omiljenepesme(omiljenePK);
            
       
          
            em3.persist(omiljene);
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Kreirana nova pretplata.");
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju pretplate. ");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje pretplate." );
            return false;
        }
        

      

        
    
    }

    private static boolean dodajOcenuAS(String parametri) {
        if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za dodavanje ocene audio snimku.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 5) {
            System.out.println("Primljeni neispravni parametri za dodavanje ocene audio snimku: " + parametri);
            return false;
        }
        

       String brojString=delovi[0].trim();
       int broj=Integer.parseInt(brojString);
       if(broj<=0 || broj>5){
            System.out.println("Vrednost ocene treba da bude u opsegu 1-5.");
            return false;
       }
       
       
        String dat = delovi[1].trim();
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

        String vreme = delovi[2].trim();
       

        int IdKorisnik;
        try {
            IdKorisnik = Integer.parseInt(delovi[3].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID korisnika: " + delovi[3]);
            return false;
        }
        
        
        int IDAudiosnimak;
        try {
            IDAudiosnimak = Integer.parseInt(delovi[4].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID audio snimka: " + delovi[4]);
            return false;
        }
        
        List<Korisnik> korisnici = em1.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnik = :IdKorisnik", Korisnik.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .getResultList();

        if (korisnici.isEmpty()) {
            System.out.println("Korisnik sa zadatim Id Korisnik ne postoji");
            return false;
        }
        
        List<Audiosnimak> audiosnimci = em2.createQuery("SELECT a FROM Audiosnimak a WHERE a.idAS = :IDAudiosnimak", Audiosnimak.class)
                .setParameter("IDAudiosnimak", IDAudiosnimak)
                .getResultList();

        if (audiosnimci.isEmpty()) {
            System.out.println("Audio snimak sa zadatim Id AudioSnimak ne postoji");
            return false;
        }
       
       

        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Ocena novaocena=new Ocena();
            novaocena.setBroj(brojString);
            novaocena.setDatum(datum);
            novaocena.setVreme(vreme);
            novaocena.setIdKorisnik(IdKorisnik);
            novaocena.setIdAS(IDAudiosnimak);
            em3.persist(novaocena);
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Kreirana nova ocena za audio snimak sa ID: " + IDAudiosnimak);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri dodavanju ocene audio snimku. " );
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno dodavanje ocene audio snimku. ");
            return false;
        }
    }

    private static boolean promeniOcenuAS(String parametri) {
       if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za promenu ocene audio snimku.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 3) {
            System.out.println("Primljeni neispravni parametri za promenu ocene audio snimku: " + parametri);
            return false;
        }
        

       String brojString=delovi[0].trim();
       int broj=Integer.parseInt(brojString);
       if(broj<=0 || broj>5){
            System.out.println("Vrednost ocene treba da bude u opsegu 1-5.");
            return false;
       }
       
        int IdKorisnik;
        try {
            IdKorisnik = Integer.parseInt(delovi[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID korisnika: " + delovi[1]);
            return false;
        }
        
        
        int IDAudiosnimak;
        try {
            IDAudiosnimak = Integer.parseInt(delovi[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID audio snimka: " + delovi[2]);
            return false;
        }
        
        List<Ocena> ocene = em3.createQuery("SELECT o FROM Ocena o WHERE o.idKorisnik = :IdKorisnik AND o.idAS = :IDAudiosnimak ", Ocena.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .setParameter("IDAudiosnimak", IDAudiosnimak)
                .getResultList();

        if (ocene.isEmpty()) {
            System.out.println("Ocena koju je dodelio korisnik sa zadatim ID za zadati audio snimak ne postoji");
            return false;
        }
        
       
       

        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Ocena mojaOcena=ocene.get(0);
            mojaOcena.setBroj(brojString);
            
            em3.persist(mojaOcena);
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Promenjena ocena za audio snimak sa ID: " + IDAudiosnimak);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri promeni ocene za audio snimak. " );
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešna promena ocene audio snimku. ");
            return false;
        }
    }

   private static String dohvatiPretplate(int IDKorisnika) {
    try {
        List<Pretplata> pretplate = em3.createQuery(
                "SELECT p FROM Pretplata p WHERE p.idKorisnik = :IDKorisnika", Pretplata.class)
                .setParameter("IDKorisnika", IDKorisnika)
                .getResultList();

        if (pretplate.isEmpty()) {
            System.out.println("Nema pretplata za ovog korisnika u bazi.");
            return "Nema pretplata za ovog korisnika u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Pretplata p : pretplate) {
            response.append("ID: ").append(p.getIdPretplata())
                    .append(", ID korisnika: ").append(p.getIdKorisnik())
                    .append(", ID paketa: ").append(p.getIdPaket())
                    .append(", Datum pocetka: ").append(p.getDatumPoc())
                    .append(", Vreme pocetka: ").append(p.getVremePoc())
                    .append(", Cena: ").append(p.getCena())
                    .append("\n");
        }
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace(); 
        return "Greška pri dohvatanju pretplata.";
    }
}

    private static boolean kreirajSlusanje(String parametri) {
       if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za kreiranje slusanja.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 6) {
            System.out.println("Primljeni neispravni parametri za slusanje: " + parametri);
            return false;
        }

        int IdKorisnik;
        try {
            IdKorisnik = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID korisnika: " + delovi[0]);
            return false;
        }
        
        int IdAudioSnimak;
        try {
            IdAudioSnimak = Integer.parseInt(delovi[1].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan ID audio snimka: " + delovi[1]);
            return false;
        }

        String dat = delovi[2].trim();
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

        String vremeOd = delovi[3].trim();
        
        int sekundeOd;
        try {
            sekundeOd = Integer.parseInt(delovi[4].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravno sekunde pocetka: " + delovi[4]);
            return false;
        }
        
        
        int sekundeOdslusano;
        try {
            sekundeOdslusano = Integer.parseInt(delovi[5].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravno sekunde odslusano: " + delovi[5]);
            return false;
        }
        
        if (IdKorisnik <= 0 || IdAudioSnimak<=0 || vremeOd.isEmpty() || sekundeOd<0 || sekundeOdslusano<0) {
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
        
        List<Audiosnimak> audiosnimci = em2.createQuery("SELECT a FROM Audiosnimak a WHERE a.idAS = :IdAudioSnimak", Audiosnimak.class)
                .setParameter("IdAudioSnimak", IdAudioSnimak)
                .getResultList();

        if (audiosnimci.isEmpty()) {
            System.out.println("Audio snimak sa zadatim Id AudioSnimak ne postoji");
            return false;
        }
        
        EntityTransaction transaction = em3.getTransaction();
        try {
            transaction.begin();
            Slusanje novoSlusanje=new Slusanje();
            novoSlusanje.setIdKorisnika(IdKorisnik);
            novoSlusanje.setIdAudioS(IdAudioSnimak);
            novoSlusanje.setDatumOd(datum);
            novoSlusanje.setVremeOd(vremeOd);
            novoSlusanje.setSekundeOd(sekundeOd);
            novoSlusanje.setSekundeUkupno(sekundeOdslusano);
            
          
            em3.persist(novoSlusanje); 
            em3.flush(); // Ensure the entity is written to the database
            transaction.commit();
            System.out.println("Kreirano novo slusanje.");
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju slusanja. ");
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje slusanja." );
            return false;
    }
  }

    private static boolean obrisiOcenu(String parametri) {
        if (parametri == null || parametri.trim().isEmpty()) {
        System.out.println("Primljeni neispravni parametri za brisanje audio snimka.");
        return false;
    }

    String[] delovi = parametri.split("!");
    if (delovi.length != 2) {
        System.out.println("Primljeni neispravni parametri!");
        return false;
    }
    
    int IdKorisnik;
    try {
        IdKorisnik = Integer.parseInt(delovi[0].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID korisnika: " + delovi[0]);
        return false;
    }
    
    int IdAudioSnimak;
    try {
        IdAudioSnimak = Integer.parseInt(delovi[1].trim());
    } catch (NumberFormatException e) {
        System.out.println("Neispravan ID audio snimka: " + delovi[1]);
        return false;
    }

   

    List<Ocena> ocene = em3.createQuery(
       "SELECT o FROM Ocena o WHERE o.idAS = :idAS AND o.idKorisnik = :idKorisnik", Ocena.class)
       .setParameter("idAS", IdAudioSnimak)
       .setParameter("idKorisnik", IdKorisnik)
       .getResultList();

    if (ocene.isEmpty()) {
        System.out.println("Ocena audiosnimka sa zadatim ID-jem ne postoji za tog korisnika.");
        return false;
    }

    Ocena ocena = ocene.get(0);

    EntityTransaction transaction = em3.getTransaction();
    try {
        transaction.begin();
        if (!em3.contains(ocena)) {
            ocena = em3.merge(ocena);
        }
        em3.remove(ocena);
        transaction.commit();

        System.out.println("Obrisana ocena za audio snimak sa ID: " + IdAudioSnimak);
        return true;
    } catch (Exception e) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri brisanju ocene audio snimka!", e);
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        System.out.println("Neuspešno brisanje ocene za audio snimak sa ID: " + IdAudioSnimak);
        return false;
    }
    }

    private static String dohvatiSlusanja(int IdAudiosnimka) {
        try {
        List<Slusanje> slusanja = em3.createQuery(
                "SELECT s FROM Slusanje s WHERE s.idAudioS = :IdAudiosnimka", Slusanje.class)
                .setParameter("IdAudiosnimka", IdAudiosnimka)
                .getResultList();

        if (slusanja.isEmpty()) {
            System.out.println("Nema slsuanja za ovaj audio snimak u bazi.");
            return "Nema slusanja za ovaj audio snimak u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Slusanje s : slusanja) {
            response.append("ID: ").append(s.getIdSlu())
                    .append(", ID korisnika: ").append(s.getIdKorisnika())
                    .append(", ID audio snimka: ").append(s.getIdAudioS())
                    .append(", Datum pocetka: ").append(s.getDatumOd())
                    .append(", Vreme pocetka: ").append(s.getVremeOd())
                    .append(", Sekunda pocetak: ").append(s.getSekundeOd())
                    .append(", Odslusano sekundi: ").append(s.getSekundeUkupno())
                    .append("\n");
        }
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace(); 
        return "Greška pri dohvatanju slusanja.";
    }
    }

    private static String dohvatiOcene(int IdAudiosnimak) {
       try {
        List<Ocena> ocene = em3.createQuery(
                "SELECT o FROM Ocena o WHERE o.idAS = :IdAudiosnimak", Ocena.class)
                .setParameter("IdAudiosnimak", IdAudiosnimak)
                .getResultList();

        if (ocene.isEmpty()) {
            System.out.println("Nema ocena za ovaj audio snimak u bazi.");
            return "Nema ocena za ovaj audio snimak u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Ocena o : ocene) {
            response.append("ID: ").append(o.getIdOcena())
                    .append(", Ocena: ").append(o.getBroj())
                    .append(", Datum: ").append(o.getDatum())
                    .append(", Vreme: ").append(o.getVreme())
                    .append(", ID korisnika: ").append(o.getIdKorisnik())
                    .append(", ID audio snimka: ").append(o.getIdAS())
                    .append("\n");
        }
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace(); 
        return "Greška pri dohvatanju ocena.";
    }
    }

    private static String dohvatiOmiljene(int IdKorisnik) {
        try {
        List<Omiljenepesme> omiljenepesme = em3.createQuery(
                "SELECT op FROM Omiljenepesme op WHERE op.omiljenepesmePK.idKor = :IdKorisnik", Omiljenepesme.class)
                .setParameter("IdKorisnik", IdKorisnik)
                .getResultList();

        if (omiljenepesme.isEmpty()) {
            System.out.println("Nema omiljenih pesama za ovog korisnika.");
            return "Nema omiljenih pesama za ovog korisnika u bazi.";
        }

        StringBuilder response = new StringBuilder();
        for (Omiljenepesme o : omiljenepesme) {
            int idASOmiljeni=o.getOmiljenepesmePK().getIdAudio();
            List <Audiosnimak> audio=em2.createQuery( "SELECT asnimak FROM Audiosnimak asnimak WHERE asnimak.idAS= :idASOmiljeni", Audiosnimak.class)
                .setParameter("idASOmiljeni", idASOmiljeni)
                .getResultList();
            if(!audio.isEmpty()){
                Audiosnimak audiosnimak=audio.get(0);
                response.append("Naziv: ").append(audiosnimak.getNaziv())
                        .append("\n");
            }
        }
        return response.toString();
    } catch (Exception e) {
        e.printStackTrace(); 
        return "Greška pri dohvatanju omiljenih pesama.";
    }
    }
}
