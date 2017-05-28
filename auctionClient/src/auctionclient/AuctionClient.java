/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

import webservice.Bid;
import webservice.Category;
import webservice.Item;
import webservice.User;

/**
 *
 * @author Adriaan
 */
public class AuctionClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Temporary Registration tests
        System.out.println(getBar());
        String mail = "example@mail.net";
        User u = registerUser(mail);
        System.out.println(u.getEmail());
        User u2 = getUser(mail);
        System.out.println(u2.getEmail());
        
        // Temporary Auction tests
        
        // Confirm no item with id 0 exists
        System.out.println(getItem(0L));
        
        // Add an item
        Item i = offerItem(u, new Category(), "A bar of soap");
        i.getCategory().setDescription("exampleCat");
        System.out.println(i.getCategory().getDescription());
    }

    
    // <editor-fold defaultstate="collapsed" desc=" Registration section ">
    private static String getBar() {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getBar();
    }
    
    private static User registerUser(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.registerUser(arg0);
    }
    
    private static User getUser(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getUser(arg0);
    }

// </editor-fold>

    private static Item getItem(java.lang.Long arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getItem(arg0);
    }
    
    private static java.util.List<webservice.Item> findItemByDescription(java.lang.String arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.findItemByDescription(arg0);
    }

    private static Bid newBid(webservice.Item arg0, webservice.User arg1, webservice.Money arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.newBid(arg0, arg1, arg2);
    }

    private static Item offerItem(webservice.User arg0, webservice.Category arg1, java.lang.String arg2) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.offerItem(arg0, arg1, arg2);
    }

    private static boolean revokeItem(webservice.Item arg0) {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.revokeItem(arg0);
    }
    
    
    
    
    
    
}
