/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

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
@Path("Podsistem3")
public class Podsistem3 {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory cf;
    @Resource(lookup="ps3slanje")
    private Queue podsistem3slanje; // Red za primanje odgovora
    @Resource(lookup="ps3prijem")
    private Queue podsistem3prijem;  // Red za slanje zahteva

    @POST
    @Path("zahtevbr9")
    public Response obradiZahtevBr9(String naziv) {
       JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(naziv);
        try {
            tm.setIntProperty("zahtev", 9);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr10")
    public Response obradiZahtevBr10(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 10);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr11")
    public Response obradiZahtevBr11(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 11);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr12")
    public Response obradiZahtevBr12(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 12);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr13")
    public Response obradiZahtevBr13(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 13);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr14")
    public Response obradiZahtevBr14(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 14);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr15")
    public Response obradiZahtevBr15(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 15);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr16")
    public Response obradiZahtevBr16(String parametri) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 16);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(parametri);
        try {
            tm.setIntProperty("zahtev", 17);
            producer.send(podsistem3prijem, tm);
        } catch (JMSException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Greska u JMS komunikaciji.").build();
        }

        String poruka = "Nema poruke";
        try {
            Message response = consumer.receive(); // Dodaj tajmaut
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
    @Path("zahtevbr23")
    public Response obradiZahtevBr23() {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage("Dohvati sva mesta.");
        try {
            tm.setIntProperty("zahtev", 23);
            producer.send(podsistem3prijem, tm);
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
   @Path("zahtevbr24")
   public Response obradiZahtevBr24(@QueryParam("id") int IDkorisnik) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(String.valueOf(IDkorisnik));
        try {
            tm.setIntProperty("zahtev", 24);
            producer.send(podsistem3prijem, tm);
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
    @Path("zahtevbr25")
    public Response obradiZahtevBr25(@QueryParam("id") int IdAudiosnimak) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(String.valueOf(IdAudiosnimak));
        try {
            tm.setIntProperty("zahtev", 25);
            producer.send(podsistem3prijem, tm);
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
    @Path("zahtevbr26")
    public Response obradiZahtevBr26(@QueryParam("id") int IdAudiosnimak) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(String.valueOf(IdAudiosnimak));
        try {
            tm.setIntProperty("zahtev", 26);
            producer.send(podsistem3prijem, tm);
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
    @Path("zahtevbr27")
    public Response obradiZahtevBr27(@QueryParam("id") int IDkorisnik) {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(podsistem3slanje);
        TextMessage tm = context.createTextMessage(String.valueOf(IDkorisnik));
        try {
            tm.setIntProperty("zahtev", 27);
            producer.send(podsistem3prijem, tm);
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