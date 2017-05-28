/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publish;

import javax.xml.ws.Endpoint;
import webservice.WebAuction;

/**
 *
 * @author Adriaan
 */
public class PublishAuctionWS {
    
    private static final String url = "http://localhost:1234/WebAuction";

    public static void main(String[] args) {
        Endpoint.publish(url, new WebAuction());
    }
    
}
