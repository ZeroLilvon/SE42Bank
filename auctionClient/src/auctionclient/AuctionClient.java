/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionclient;

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
    }

    private static String getBar() {
        webservice.WebAuctionService service = new webservice.WebAuctionService();
        webservice.WebAuction port = service.getWebAuctionPort();
        return port.getBar();
    }
    
    
    
}
