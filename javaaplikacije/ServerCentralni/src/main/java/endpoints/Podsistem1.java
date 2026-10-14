package endpoints;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
/**
 *
 * @author Korisnik
 */
@Path("Podsistem1")
public class Podsistem1 {
   
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory cf;
    @Resource(lookup="ps1slanje")
    //na ovaj red Server prima odgovore iz Podsistema1
    private Queue podsistem1slanje;
    @Resource(lookup="ps1prijem")
    //na ovaj red se salju zahtevi iz Servera na Podsistem1
    private Queue podsistem1prijem;
   
    @POST
    @Path("zahtevbr1")
    public Response obradiZahtevBr1(String naziv) {
        //ZahtevBr1: Napravi mesto
        if(naziv==null){
            
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska!").build();
        }
        JMSContext context=cf.createContext();
        JMSProducer producer=context.createProducer();
        JMSConsumer consumer=context.createConsumer(podsistem1slanje);
       
        TextMessage tm = context.createTextMessage(naziv);
 
        try {
            tm.setIntProperty("zahtev", 1);
        } catch (JMSException ex) {
            Logger.getLogger(Podsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(podsistem1prijem, tm);
       
        String poruka="Nema poruke";
        Message response = consumer.receive();
        if(response instanceof TextMessage){
            TextMessage response2 = (TextMessage) response;
            try {
                poruka=response2.getText();
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
        return Response.ok(poruka).build();
    }
   
    @POST
    @Path("zahtevbr2")
    public Response obradiZahtevBr2(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem1slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 2);
            producer.send(podsistem1prijem, tm);
        } catch (JMSException ex) {
            Logger.getLogger(Podsistem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(5000); // Dodaj tajmaut
            if (response instanceof TextMessage) {
                poruka = ((TextMessage) response).getText();
            }
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u primanju odgovora.").build();
        } finally {
            context.close();
        }

        return Response.ok(poruka).build();
    }
    
    @PUT
    @Path("zahtevbr3")
    public Response obradiZahtevBr3(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem1slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 3);
            producer.send(podsistem1prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(5000); // Dodaj tajmaut
            if (response instanceof TextMessage) {
                poruka = ((TextMessage) response).getText();
            }
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u primanju odgovora.").build();
        } finally {
            context.close();
        }

        return Response.ok(poruka).build();
    }
    
    @PUT
    @Path("zahtevbr4")
    public Response obradiZahtevBr4(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem1slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 4);
            producer.send(podsistem1prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(5000); // Dodaj tajmaut
            if (response instanceof TextMessage) {
                poruka = ((TextMessage) response).getText();
            }
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u primanju odgovora.").build();
        } finally {
            context.close();
        }

        return Response.ok(poruka).build();
    }
    
    @GET
    @Path("zahtevbr18")
    public Response obradiZahtevBr18() {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem1slanje);
        TextMessage tm = context.createTextMessage("Dohvati sva mesta.");
        try {
            tm.setIntProperty("zahtev", 18);
            producer.send(podsistem1prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(5000); // Dodaj tajmaut
            if (response instanceof TextMessage) {
                poruka = ((TextMessage) response).getText();
            }
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u primanju odgovora.").build();
        } finally {
            context.close();
        }

        return Response.ok(poruka).build();
    }
    
    @GET
    @Path("zahtevbr19")
    public Response obradiZahtevBr19() {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem1slanje);
        TextMessage tm = context.createTextMessage("Dohvati sve korisnike.");
        try {
            tm.setIntProperty("zahtev", 19);
            producer.send(podsistem1prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(5000); // Dodaj tajmaut
            if (response instanceof TextMessage) {
                poruka = ((TextMessage) response).getText();
            }
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u primanju odgovora.").build();
        } finally {
            context.close();
        }

        return Response.ok(poruka).build();
    }
}
