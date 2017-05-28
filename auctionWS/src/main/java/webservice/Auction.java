/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.util.List;
import nl.fontys.util.Money;

/**
 *
 * @author Adriaan
 */
public class Auction {
    
    private AuctionMgr aucMgr = new AuctionMgr();
    private SellerMgr sellMgr = new SellerMgr();
    
    public Item getItem(Long id){
        return aucMgr.getItem(id);
    }
    
    public List<Item> findItemByDescription(String description){
        return aucMgr.findItemByDescription(description);
    }
    
    public Bid newBid(Item item, User buyer, Money amount){
        return aucMgr.newBid(item, buyer, amount);
    }
    
    public Item offerItem(User seller, Category cat, String description){
        return sellMgr.offerItem(seller, cat, description);
    }
    
    public boolean revokeItem(Item item){
        return sellMgr.revokeItem(item);
    }
    
}
