/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Korisnik
 */
@Path("Podsistem2")
public class Podsistem2 {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory cf;
    @Resource(lookup="ps2slanje")
    //na ovaj red Server prima odgovore iz Podsistema2
    private Queue podsistem2slanje;
    @Resource(lookup="ps2prijem")
    //na ovaj red se salju zahtevi iz Servera na Podsistem2
    private Queue podsistem2prijem;
   
    @POST
    @Path("zahtevbr5")
    public Response obradiZahtevBr5(String naziv) {
        //ZahtevBr5: Napravi kategoriju
        if(naziv==null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Greska!").build();
        }
        JMSContext context=cf.createContext();
        JMSProducer producer=context.createProducer();
        JMSConsumer consumer=context.createConsumer(podsistem2slanje);
       
        TextMessage tm = context.createTextMessage(naziv);
 
        try {
            tm.setIntProperty("zahtev", 5);
        } catch (JMSException ex) {
            Logger.getLogger(Podsistem2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(podsistem2prijem, tm);
       
        String poruka="Nema poruke";
        Message response = consumer.receive();
        if(response instanceof TextMessage){
            TextMessage response2 = (TextMessage) response;
            try {
                poruka=response2.getText();
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
        return Response.ok(poruka).build();
    }
    
    @POST
    @Path("zahtevbr6")
    public Response obradiZahtevBr6(String parametri) {
        //Kreiranje audio snimka
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 6);
            producer.send(podsistem2prijem, tm);
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
    @Path("zahtevbr7")
    public Response obradiZahtevBr7(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 7);
            producer.send(podsistem2prijem, tm);
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
   
    @POST
    @Path("zahtevbr8")
    public Response obradiZahtevBr8(String parametri) {
        //Dodavanje kategorije audio snimku 
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 8);
            producer.send(podsistem2prijem, tm);
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
    @Path("zahtevbr17")
    public Response obradiZahtevBr17(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 17);
            producer.send(podsistem2prijem, tm);
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
    @Path("zahtevbr20")
    public Response obradiZahtevBr20() {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage();
        try {
            tm.setIntProperty("zahtev", 20);
            producer.send(podsistem2prijem, tm);
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
    @Path("zahtevbr21")
    public Response obradiZahtevBr21() {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage();
        try {
            tm.setIntProperty("zahtev", 21);
            producer.send(podsistem2prijem, tm);
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
    @Path("zahtevbr22")
    public Response obradiZahtevBr22(@QueryParam("id") Integer IdAS) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem2slanje);
        TextMessage tm = context.createTextMessage(String.valueOf(IdAS));
        try {
            tm.setIntProperty("zahtev", 22);
            producer.send(podsistem2prijem, tm);
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
