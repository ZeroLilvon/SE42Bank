/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

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
        System.out.println(getBar());
        String mail = "example@mail.net";
        User u = registerUser(mail);
        System.out.println(u.getEmail());
        User u2 = getUser(mail);
        System.out.println(u2.getEmail());
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
    
    
    
    
}
