package podsistem1;

import entiteti.*;
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

public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory cf;

    @Resource(lookup = "ps1slanje")
    private static Queue podsistem1slanje;

    @Resource(lookup = "ps1prijem")
    private static Queue podsistem1prijem;

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        em = emf.createEntityManager();

        try (JMSContext context = cf.createContext()) {
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(podsistem1prijem);

            // Očistimo red pre nego što počnemo da primamo poruke
            purgeQueue(context, podsistem1prijem);

            System.out.println("Podsistem1 started, listening for requests...");
            while (true) {
                try {
                    Message msg = consumer.receive();
                    if (msg instanceof TextMessage) {
                        TextMessage tm = (TextMessage) msg;
                        int zadatak = tm.getIntProperty("zahtev");
                        TextMessage response = context.createTextMessage();
                        String parametri;
                        switch (zadatak) {
                            case 1:
                                String naziv = tm.getText();
                                if (naziv == null || naziv.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje grada: Naziv ne sme biti prazan.");
                                    System.out.println("Primljen prazan ili null naziv za grad.");
                                } else if (kreirajGrad(naziv)) {
                                    response.setText("Uspesno kreiran grad: " + naziv);
                                } else {
                                    response.setText("Neuspesno kreiranje grada: Grad vec postoji ili je doslo do greske.");
                                }
                                break;

                            case 2:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesno kreiranje korisnika: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za korisnika.");
                                } else if (kreirajKorisnika(parametri)) {
                                    response.setText("Uspesno kreiran korisnik.");
                                } else {
                                    response.setText("Neuspesno kreiranje korisnika!");
                                }
                                break;

                            case 3:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesna promena email adrese: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za email adresu.");
                                } else if (promeniEmail(parametri)) {
                                    response.setText("Uspesno promenjena email adresa.");
                                } else {
                                    response.setText("Neuspesna promena email adrese!");
                                }
                                break;

                            case 4:
                                parametri = tm.getText();
                                if (parametri == null || parametri.trim().isEmpty()) {
                                    response.setText("Neuspesna promena mesta: Parametri ne smeju biti prazni.");
                                    System.out.println("Primljeni prazni parametri za mesto.");
                                } else if (promeniMesto(parametri)) {
                                    response.setText("Uspesno promenjeno mesto.");
                                } else {
                                    response.setText("Neuspesna promena mesta!");
                                }
                                break;

                            case 18:
                                response.setText(dohvatiSvaMesta());
                                break;
                            case 19:
                                response.setText(dohvatiSveKorisnike());
                                break;

                            default:
                                System.out.println("Nepoznati zadatak: " + zadatak);
                                response.setText("Nepoznat zahtev.");
                        }
                        producer.send(podsistem1slanje, response);
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
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void purgeQueue(JMSContext context, Queue queue) {
       
            JMSConsumer tempConsumer = context.createConsumer(queue);
            Message msg;
            while ((msg = tempConsumer.receiveNoWait()) != null) {
                // samo čitamo i odbacujemo sve poruke
            }
            tempConsumer.close();
            System.out.println("Red je očišćen pre pokretanja Podsistem1.");
        
    }

    private static boolean kreirajGrad(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            System.out.println("Neuspešno kreiranje grada: Naziv je null ili prazan.");
            return false;
        }

        System.out.println("Kreiranje grada: " + naziv);
        List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m WHERE m.naziv = :naziv", Mesto.class)
                .setParameter("naziv", naziv.trim())
                .getResultList();

        if (!mesta.isEmpty()) {
            System.out.println("Mesto već postoji: " + naziv);
            return false;
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Mesto novo = new Mesto();
            novo.setNaziv(naziv.trim());
            em.persist(novo);
            em.flush(); 
            transaction.commit();
            System.out.println("Kreirano novo mesto: " + naziv);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju grada: " + naziv, e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje mesta: " + naziv);
            return false;
        }
    }

    private static boolean kreirajKorisnika(String parametri) {
        if (parametri == null || parametri.trim().isEmpty()) {
            System.out.println("Primljeni neispravni parametri za kreiranje korisnika.");
            return false;
        }

        String[] delovi = parametri.split("!");
        if (delovi.length != 5) {
            System.out.println("Primljeni neispravni parametri za kreiranje korisnika: " + parametri);
            return false;
        }

        String ime = delovi[0].trim();
        String email = delovi[1].trim();
        int godiste;
        try {
            godiste = Integer.parseInt(delovi[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravno godište: " + delovi[2]);
            return false;
        }
        String pol = delovi[3].trim();
        String mestoNaziv = delovi[4].trim();

        if (ime.isEmpty() || email.isEmpty() || mestoNaziv.isEmpty()) {
            System.out.println("Neispravni parametri: ime, email ili mesto su prazni.");
            return false;
        }

        List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k WHERE k.email = :email", Korisnik.class)
                .setParameter("email", email)
                .getResultList();

        if (!korisnici.isEmpty()) {
            System.out.println("Korisnik sa emailom već postoji: " + email);
            return false;
        }

        List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m WHERE m.naziv = :naziv", Mesto.class)
                .setParameter("naziv", mestoNaziv)
                .getResultList();

        if (mesta.isEmpty()) {
            System.out.println("Mesto ne postoji: " + mestoNaziv);
            return false;
        }

        Mesto mesto = mesta.get(0);
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Korisnik noviKorisnik = new Korisnik();
            noviKorisnik.setIme(ime);
            noviKorisnik.setEmail(email);
            noviKorisnik.setGodiste(godiste);
            noviKorisnik.setPol(pol.charAt(0));
            noviKorisnik.setIdMesto(mesto);
            em.persist(noviKorisnik);
            em.flush();
            transaction.commit();
            System.out.println("Kreiran novi korisnik: " + ime);
            return true;
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri kreiranju korisnika: " + ime, e);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešno kreiranje korisnika: " + ime);
            return false;
        }
    }

    private static boolean promeniEmail(String parametri) {
        String[] delovi = parametri.split("!");
        if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri za promenu email adrese!");
            return false;
        }

        int IDKorisnika;
        try {
            IDKorisnika = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan Id korisnika!");
            return false;
        }
        String email = delovi[1].trim();
        if (IDKorisnika <= 0 || email.isEmpty()) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnik = :IDKorisnika", Korisnik.class)
                .setParameter("IDKorisnika", IDKorisnika)
                .getResultList();

        if (korisnici.isEmpty()) {
            System.out.println("Ne postoji korisnik sa zadatim Id!");
            return false;
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Korisnik korisnik = korisnici.get(0);
            korisnik.setEmail(email);
            em.persist(korisnik);
            em.flush();
            transaction.commit();
            System.out.println("Promenjen email korisniku sa Id: " + IDKorisnika);
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešna promena email adrese!");
            return false;
        }
    }

    private static boolean promeniMesto(String parametri) {
        String[] delovi = parametri.split("!");
        if (delovi.length != 2) {
            System.out.println("Primljeni neispravni parametri za promenu mesta!");
            return false;
        }

        int IDKorisnika;
        try {
            IDKorisnika = Integer.parseInt(delovi[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Neispravan Id korisnika!");
            return false;
        }
        String mesto = delovi[1].trim();
        if (IDKorisnika <= 0 || mesto.isEmpty()) {
            System.out.println("Neispravni parametri!");
            return false;
        }

        List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnik = :IDKorisnika", Korisnik.class)
                .setParameter("IDKorisnika", IDKorisnika)
                .getResultList();

        if (korisnici.isEmpty()) {
            System.out.println("Ne postoji korisnik sa zadatim Id!");
            return false;
        }

        List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m WHERE m.naziv = :mesto", Mesto.class)
                .setParameter("mesto", mesto)
                .getResultList();

        if (mesta.isEmpty()) {
            System.out.println("Mesto ne postoji: " + mesto);
            return false;
        }

        Mesto novoMesto = mesta.get(0);

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Korisnik korisnik = korisnici.get(0);
            korisnik.setIdMesto(novoMesto);
            em.persist(korisnik);
            em.flush();
            transaction.commit();
            System.out.println("Promenjeno mesto korisniku sa Id: " + IDKorisnika);
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Neuspešna promena mesta");
            return false;
        }
    }

    private static String dohvatiSvaMesta() {
        try {
            List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m", Mesto.class).getResultList();
            if (mesta.isEmpty()) {
                System.out.println("Nema mesta u bazi.");
                return "Nema mesta u bazi.";
            }

            StringBuilder response = new StringBuilder();
            for (Mesto m : mesta) {
                response.append("ID: ").append(m.getIdMesto())
                        .append(", Naziv: ").append(m.getNaziv())
                        .append("\n");
            }
            return response.toString();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greška pri dohvatanju mesta", e);
            return "Greška pri dohvatanju mesta.";
        }
    }

    private static String dohvatiSveKorisnike() {
        try {
            List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class).getResultList();
            if (korisnici.isEmpty()) {
                System.out.println("Nema korisnika u bazi.");
                return "Nema korisnika u bazi.";
            }

            StringBuilder response = new StringBuilder();
            for (Korisnik k : korisnici) {
                response.append("ID: ").append(k.getIdKorisnik())
                        .append(", Ime: ").append(k.getIme())
                        .append(", Email: ").append(k.getEmail())
                        .append(", Godište: ").append(k.getGodiste())
                        .append(", Pol: ").append(k.getPol())
                        .append(", Mesto: ").append(k.getIdMesto().getNaziv())
                        .append("\n");
            }
            return response.toString();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greška pri dohvatanju korisnika", e);
            return "Greška pri dohvatanju korisnika.";
        }
    }
}
