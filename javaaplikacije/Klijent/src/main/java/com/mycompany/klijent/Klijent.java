package com.mycompany.klijent;

import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Klijent {
    private static String osnovniURL1 = "http://localhost:8080/ServerCentralni/resources/Podsistem1/";
    private static String osnovniURL2 = "http://localhost:8080/ServerCentralni/resources/Podsistem2/";
    private static String osnovniURL3 = "http://localhost:8080/ServerCentralni/resources/Podsistem3/";
    private static Client klijent;
    private static Scanner skener;

    public static void main(String[] args) {
        klijent = ClientBuilder.newClient();
        skener = new Scanner(System.in);

        int izbor;

        while (true) {
            System.out.println("=============== MENI ===============");
            System.out.println("1. Kreiranje grada");
            System.out.println("2. Kreiranje korisnika");
            System.out.println("3. Promena email adrese za korisnika");
            System.out.println("4. Promena mesta za korisnika");
            System.out.println("5. Kreiranje kategorije");
            System.out.println("6. Kreiranje audio snimka");
            System.out.println("7. Promena naziva audio snimka");
            System.out.println("8. Dodavanje kategorije audio snimku");
            System.out.println("9. Kreiranje paketa");
            System.out.println("10. Promena mesečne cene za paket");
            System.out.println("11. Kreiranje pretplate korisnika na paket");
            System.out.println("12. Kreiranje slušanja audio snimka od strane korisnika");
            System.out.println("13. Dodavanje audio snimka u omiljene od strane korisnika");
            System.out.println("14. Kreiranje ocene korisnika za audio snimak");
            System.out.println("15. Menjanje ocene korisnika za audio snimak");
            System.out.println("16. Brisanje ocene korisnika za audio snimak");
            System.out.println("17. Brisanje audio snimka od strane korisnika koji ga je kreirao");
            System.out.println("18. Dohvatanje svih mesta");
            System.out.println("19. Dohvatanje svih korisnika");
            System.out.println("20. Dohvatanje svih kategorija");
            System.out.println("21. Dohvatanje svih audio snimaka");
            System.out.println("22. Dohvatanje kategorija za određeni audio snimak");
            System.out.println("23. Dohvatanje svih paketa");
            System.out.println("24. Dohvatanje svih pretplata za korisnika");
            System.out.println("25. Dohvatanje svih slušanja za audio snimak");
            System.out.println("26. Dohvatanje svih ocena za audio snimak");
            System.out.println("27. Dohvatanje liste omiljenih audio snimaka za korisnika");
            System.out.println("0. Izlaz");
            System.out.print("Izaberite opciju:\n");

            if (!skener.hasNextInt()) {
                System.out.println("Uneli ste neispravan unos! Pokušajte ponovo.");
                skener.next();
                continue;
            }

            izbor = skener.nextInt();
            skener.nextLine();

            if (izbor < 0 || izbor > 27) {
                System.out.println("Izabrali ste nepostojeću opciju. Pokušajte ponovo.");
                continue;
            }

            if (izbor == 0) {
                System.out.println("Izlaz iz programa...");
                break;
            }

            switch (izbor) {
                case 1: izbor1(); break;
                case 2: izbor2(); break;
                case 3: izbor3(); break;
                case 4: izbor4(); break;
                case 5: izbor5(); break;
                case 6: izbor6(); break;
                case 7: izbor7(); break;
                case 8: izbor8(); break;
                case 9: izbor9(); break;
                case 10: izbor10(); break;
                case 11: izbor11(); break;
                case 12: izbor12(); break;
                case 13: izbor13(); break;
                case 14: izbor14(); break;
                case 15: izbor15(); break;
                case 16: izbor16(); break;
                case 17: izbor17(); break;
                case 18: izbor18(); break;
                case 19: izbor19(); break;
                case 20: izbor20(); break;
                case 21: izbor21(); break;
                case 22: izbor22(); break;
                case 23: izbor23(); break;
                case 24: izbor24(); break;
                case 25: izbor25(); break;
                case 26: izbor26(); break;
                case 27: izbor27(); break;
                default:
                    System.out.println("Nevažeća opcija! Pokušajte ponovo.");
                    break;
            }
        }

        klijent.close();
        skener.close();
    }

    private static String zahtev1(String naziv, Client klijent) {
    try {
        Response odgovor = klijent
            .target(osnovniURL1 + "zahtevbr1")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(naziv));

        return odgovor.getStatus() == 200
            ? odgovor.readEntity(String.class)
            : "Greška: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);

    } catch (Exception e) {
        return "Greška u komunikaciji sa serverom: " + e.getMessage();
    }
}


    private static String zahtev2(String ime, String email, int godiste, String pol, String mesto) {
        String parametri = ime + "!" + email + "!" + godiste + "!" + pol + "!" + mesto;
         Response odgovor = klijent
            .target(osnovniURL1 + "zahtevbr2")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev3(int IDKorisnika, String email) {
        String parametri=IDKorisnika + "!" + email;
        Response odgovor = klijent
            .target(osnovniURL1 + "zahtevbr3")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev4(int IDKorisnika, String mesto) {
        String parametri=IDKorisnika + "!" + mesto;
        Response odgovor = klijent
            .target(osnovniURL1 + "zahtevbr4")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev5(String naziv) {
       try {
        Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr5")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(naziv));

        return odgovor.getStatus() == 200
            ? odgovor.readEntity(String.class)
            : "Greška: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);

    } catch (Exception e) {
        return "Greška u komunikaciji sa serverom: " + e.getMessage();
    }
    }

    private static String zahtev6(int IdKorisnik, String naziv, int trajanje, String datum, String vreme ) {
        String parametri = IdKorisnik + "!" + naziv + "!" + trajanje + "!" + datum + "!" + vreme;
         Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr6")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev7(int IDAudioSnimak, String noviNaziv) {
        String parametri=IDAudioSnimak + "!" + noviNaziv;
        Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr7")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev8(int IDAudioSnimak, int IDKategorija) {
        String parametri=IDAudioSnimak + "!" + IDKategorija;
        Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr8")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev9(String nazivPaketa, double cena) {
       String parametri = nazivPaketa + "!" + cena;
         Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr9")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev10(int IDpaketa, double novaCena) {
        String parametri = IDpaketa + "!" + novaCena;
         Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr10")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev11(int IDkorisnika, int IDpaketa, String datumPoc, String vremePoc, double cena) {
        String parametri = IDkorisnika + "!" + IDpaketa + "!" + datumPoc + "!" + vremePoc + "!" + cena;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr11")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev12(int IDkorisnika, int IDAudiosnimak, String datumOd, String vremeOd, int sekundPocetka, int sekundiOdslusano) {
        String parametri = IDkorisnika + "!" + IDAudiosnimak + "!" + datumOd + "!" + vremeOd + "!" + sekundPocetka + "!" + sekundiOdslusano;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr12")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev13(int IDAudiosnimak, int IDkorisnika) {
       String parametri = IDAudiosnimak  + "!" + IDkorisnika;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr13")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev14(String broj, String datum, String vreme, int IdKorisnik, int IdAudiosnimak) {
        String parametri = broj  + "!" + datum + "!" + vreme + "!" + IdKorisnik + "!" + IdAudiosnimak ;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr14")
            .request(MediaType.TEXT_PLAIN)
            .post(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev15(String novaOcena, int IdKorisnik, int IdAudiosnimak) {
        String parametri = novaOcena  + "!" + IdKorisnik + "!" + IdAudiosnimak ;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr15")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev16(int IdKorisnik, int IdAudiosnimak) {
        String parametri = IdKorisnik + "!" + IdAudiosnimak ;
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr16")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev17(int IdAudiosnimak, int IdKorisnik) {
        String parametri = IdAudiosnimak +  "!" + IdKorisnik ;
        Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr17")
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.text(parametri));
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }
    }

    private static String zahtev18() {
        Response odgovor = klijent
        .target(osnovniURL1 + "zahtevbr18")
        .request(MediaType.TEXT_PLAIN)
        .get(); 
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }   


    private static String zahtev19() {
        Response odgovor = klijent
            .target(osnovniURL1 + "zahtevbr19")
            .request(MediaType.TEXT_PLAIN)
            .get();
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev20() {
         Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr20")
            .request(MediaType.TEXT_PLAIN)
            .get();
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev21() {
        Response odgovor = klijent
            .target(osnovniURL2 + "zahtevbr21")
            .request(MediaType.TEXT_PLAIN)
            .get();
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

    private static String zahtev22(int IdAudiosnimak) {
    Response odgovor = klijent
        .target(osnovniURL2 + "zahtevbr22")
        .queryParam("id", IdAudiosnimak) 
        .request(MediaType.TEXT_PLAIN)
        .get();

    if (odgovor.getStatus() == 200) {
        return odgovor.readEntity(String.class);
    } else {
        return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
    }
}

    private static String zahtev23() {
        Response odgovor = klijent
            .target(osnovniURL3 + "zahtevbr23")
            .request(MediaType.TEXT_PLAIN)
            .get();
        if (odgovor.getStatus() == 200) {
            return odgovor.readEntity(String.class);
        } else {
            return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
        }  
    }

   private static String zahtev24(int IDkorisnik) {
    Response odgovor = klijent
        .target(osnovniURL3 + "zahtevbr24")
        .queryParam("id", IDkorisnik)
        .request(MediaType.TEXT_PLAIN)
        .get();

    if (odgovor.getStatus() == 200) {
        return odgovor.readEntity(String.class); // vraća odgovor kao string
    } else {
        return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
    }
}


    private static String zahtev25(int IdAudiosnimak) {
       Response odgovor = klijent
        .target(osnovniURL3 + "zahtevbr25")
        .queryParam("id", IdAudiosnimak)
        .request(MediaType.TEXT_PLAIN)
        .get();

    if (odgovor.getStatus() == 200) {
        return odgovor.readEntity(String.class); // vraća odgovor kao string
    } else {
        return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
    }
    }

    private static String zahtev26(int IdAudiosnimak) {
       Response odgovor = klijent
        .target(osnovniURL3 + "zahtevbr26")
        .queryParam("id", IdAudiosnimak)
        .request(MediaType.TEXT_PLAIN)
        .get();

    if (odgovor.getStatus() == 200) {
        return odgovor.readEntity(String.class); // vraća odgovor kao string
    } else {
        return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
    }
    }

    private static String zahtev27(int IdKorisnik) {
        Response odgovor = klijent
        .target(osnovniURL3 + "zahtevbr27")
        .queryParam("id", IdKorisnik)
        .request(MediaType.TEXT_PLAIN)
        .get();

    if (odgovor.getStatus() == 200) {
        return odgovor.readEntity(String.class); // vraća odgovor kao string
    } else {
        return "Greska: " + odgovor.getStatus() + " - " + odgovor.readEntity(String.class);
    }
    }

    private static void izbor1() {
        System.out.println("Unesite naziv mesta koje zelite da kreirate (String): ");
        String naziv = skener.nextLine();
        System.out.println(zahtev1(naziv, klijent));
    }

    private static void izbor2() {
        System.out.println("Unesite ime korisnika (String): ");
        String ime = skener.nextLine();
        System.out.println("Unesite email korisnika (String): ");
        String email = skener.nextLine();
        System.out.println("Unesite godište korisnika (int): ");
        int godiste = skener.nextInt();
        System.out.println("Unesite pol korisnika (M/Z): ");

        skener.nextLine();
        String pol = skener.nextLine();
        System.out.println("Unesite naziv mesta korisnika (String): ");
        String mesto = skener.nextLine();
        System.out.println(zahtev2(ime, email, godiste, pol, mesto));
    }

    private static void izbor3() {
        System.out.println("Unesite ID korisnika (int): ");
        int IDKorisnika = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite novi email (String): ");
        String email = skener.nextLine();
        System.out.println(zahtev3(IDKorisnika, email));
    }

    private static void izbor4() {
        System.out.println("Unesite ID korisnika (int): ");
        int IDKorisnika = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite novo mesto (String): ");
        String mesto = skener.nextLine();
        System.out.println(zahtev4(IDKorisnika, mesto));
    }

    private static void izbor5() {
        System.out.println("Unesite naziv kategorije (String): ");
        String naziv = skener.nextLine();
        System.out.println(zahtev5(naziv));
    }

    private static void izbor6() {
        System.out.println("Unesite IdKorisnik (int): ");
         while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za IdKorisnik: ");
            skener.next();
        }
        int IdKorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite naziv audio snimka: ");
        String naziv = skener.nextLine();
        System.out.println("Unesite trajanje audio snimka (int, sekunde): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za trajanje: ");
            skener.next();
        }
        int trajanje = skener.nextInt();
        skener.nextLine();
        
        System.out.println("Unesite datum (u formatu yyyy-MM-dd): ");
        String datum = skener.nextLine();
        
        System.out.println("Unesite vreme (u formatu HH:mm:ss): ");
        String vreme = skener.nextLine();
        System.out.println(zahtev6(IdKorisnik, naziv, trajanje, datum, vreme));
    }

    private static void izbor7() {
        System.out.println("Unesite ID audio snimka (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID: ");
            skener.next();
        }
        int IDAudioSnimak = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite novi naziv audio snimka (String): ");
        String noviNaziv = skener.nextLine();
        System.out.println(zahtev7(IDAudioSnimak, noviNaziv));
    }

    private static void izbor8() {
        System.out.println("Unesite ID audio snimka (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio snimka: ");
            skener.next();
        }
        int IDAudioSnimak = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID kategorije (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID kategorije: ");
            skener.next();
        }
        int IDkategorija = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev8(IDAudioSnimak, IDkategorija));
    }

    private static void izbor9() {
        System.out.println("Unesite naziv paketa (String): ");
        String nazivPaketa = skener.nextLine();
        System.out.println("Unesite cenu paketa (double): ");
        while (!skener.hasNextDouble()) {
            System.out.println("Neispravan unos! Unesite decimalni broj za cenu: ");
            skener.next();
        }
        double cena = skener.nextDouble();
        skener.nextLine();
        System.out.println(zahtev9(nazivPaketa, cena));
    }

    private static void izbor10() {
        System.out.println("Unesite ID paketa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID paketa: ");
            skener.next();
        }
        int IDpaketa = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite novu cenu paketa (double): ");
        while (!skener.hasNextDouble()) {
            System.out.println("Neispravan unos! Unesite decimalni broj za cenu: ");
            skener.next();
        }
        double novaCena = skener.nextDouble();
        skener.nextLine();
        System.out.println(zahtev10(IDpaketa, novaCena));
    }

   private static void izbor11() {
    System.out.println("Unesite ID korisnika (int): ");
    while (!skener.hasNextInt()) {
        System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
        skener.next();
    }
    int IDkorisnika = skener.nextInt();
    skener.nextLine(); // pojede newline

    System.out.println("Unesite ID paketa (int): ");
    while (!skener.hasNextInt()) {
        System.out.println("Neispravan unos! Unesite ceo broj za ID paketa: ");
        skener.next();
    }
    int IDpaketa = skener.nextInt();
    skener.nextLine();

    System.out.println("Unesite datum pocetka pretplate (u formatu YYYY-MM-DD): ");
    String datumPoc = skener.nextLine();

    System.out.println("Unesite vreme pocetka pretplate (u formatu HH:MM:SS): ");
    String vremePoc = skener.nextLine();

    System.out.println("Unesite cenu pretplate: ");
    while (!skener.hasNextDouble()) {
        System.out.println("Neispravan unos! Unesite realan broj za cenu pretplate: ");
        skener.next();
    }
    double cena = skener.nextDouble();
    skener.nextLine();

    System.out.println(zahtev11(IDpaketa, IDkorisnika, datumPoc, vremePoc, cena));
}


    private static void izbor12() {
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IDkorisnika = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID audio snimka (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio snimka: ");
            skener.next();
        }
        int IDAudiosnimak = skener.nextInt();
        skener.nextLine();
        
        System.out.println("Unesite datum pocetka slusanja (u formatu YYYY-MM-DD): ");
        String datumOd = skener.nextLine();

        System.out.println("Unesite vreme pocetka slusanja (u formatu HH:MM:SS): ");
        String vremeOd = skener.nextLine();

        System.out.println("Unesite sekund početka slušanja (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za sekund početka: ");
            skener.next();
        }
        int sekundPocetka = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite broj odslušanih sekundi (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za odslušane sekunde: ");
            skener.next();
        }
        int sekundiOdslusano = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev12(IDkorisnika, IDAudiosnimak, datumOd, vremeOd, sekundPocetka, sekundiOdslusano));
    }

    private static void izbor13() {
        System.out.println("Unesite ID audio snimka (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio snimka: ");
            skener.next();
        }
        int IDAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IDkorisnika = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev13( IDAudiosnimak, IDkorisnika));
    }

    private static void izbor14() {
        System.out.println("Unesite ocenu: ");
        String broj = skener.nextLine();
        
        System.out.println("Unesite datum dodavanja ocene (u formatu YYYY-MM-DD): ");
        String datum = skener.nextLine();

        System.out.println("Unesite vreme dodavanja ocene(u formatu HH:MM:SS): ");
        String vreme = skener.nextLine();
        
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IdKorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IdAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev14(broj, datum, vreme, IdKorisnik, IdAudiosnimak));
   }
    private static void izbor15() {
        System.out.println("Unesite ocenu: ");
        String novaOcena = skener.nextLine();
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IdKorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IdAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev15(novaOcena, IdKorisnik, IdAudiosnimak));
    }

    private static void izbor16() {
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IDkorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IDAudiozapis = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev16(IDkorisnik, IDAudiozapis));
    }

    private static void izbor17() {
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IDAudiozapis = skener.nextInt();
        skener.nextLine();
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IDkorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev17(IDAudiozapis, IDkorisnik));
    }

    private static void izbor18() {
        System.out.println(zahtev18());
    }

    private static void izbor19() {
        System.out.println(zahtev19());
    }

    private static void izbor20() {
        System.out.println(zahtev20());
    }

    private static void izbor21() {
        System.out.println(zahtev21());
    }

    private static void izbor22() {
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IdAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev22(IdAudiosnimak));
    }

    private static void izbor23() {
         System.out.println(zahtev23());
    }

    private static void izbor24() {
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IdKorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev24(IdKorisnik));
    }

    private static void izbor25() {
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IdAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev25(IdAudiosnimak));
    }

    private static void izbor26() {
        System.out.println("Unesite ID audio zapisa (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID audio zapisa: ");
            skener.next();
        }
        int IdAudiosnimak = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev26(IdAudiosnimak));
    }

    private static void izbor27() {
        System.out.println("Unesite ID korisnika (int): ");
        while (!skener.hasNextInt()) {
            System.out.println("Neispravan unos! Unesite ceo broj za ID korisnika: ");
            skener.next();
        }
        int IdKorisnik = skener.nextInt();
        skener.nextLine();
        System.out.println(zahtev27(IdKorisnik));
    }
}