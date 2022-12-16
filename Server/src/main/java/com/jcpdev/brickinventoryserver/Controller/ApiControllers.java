package com.jcpdev.brickinventoryserver.Controller;

import com.jcpdev.brickinventoryserver.Models.Items;
import com.jcpdev.brickinventoryserver.Repo.ItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiControllers {

    @Autowired
    private ItemsRepo itemsRepo;

    @CrossOrigin
    @GetMapping(value = "/")
    public List<Items> loadAllItems(){
        return itemsRepo.findAll();
    }

    @CrossOrigin
    @GetMapping(value = "/{barcode}")
    public Items getItem(@PathVariable long barcode){
        return itemsRepo.findById(barcode).get();
    }



    @CrossOrigin
    @GetMapping(value = "/additem")
    public void addItem(){
    Items newItems = new Items();
    newItems.setPhotoUrl("default.avif");
    itemsRepo.save(newItems);
    }

    @CrossOrigin
    @GetMapping(value ="/changequantity/{barcode}/{quantity}")
    public void changeQuantity(@PathVariable long barcode, @PathVariable int quantity){
        Items updatedItem = itemsRepo.findById(barcode).get();
        updatedItem.setQuantity(quantity);
        itemsRepo.save(updatedItem);
    }

    @CrossOrigin
    @GetMapping(value = "/{barcode}/quantity")
    public long getQuantity(@PathVariable long barcode){
        return itemsRepo.findById(barcode).get().getQuantity();
    }

    @CrossOrigin
    @GetMapping (value = "/{barcode}/delete")
    public void deleteItem(@PathVariable long barcode){
        Items deleteItem = itemsRepo.findById(barcode).get();
        itemsRepo.delete(deleteItem);
    }

    @CrossOrigin
    @GetMapping (value = "/{barcode}/changephoto/{photourl}")
    public void changePhoto(@PathVariable long barcode, @PathVariable String photourl){
        Items Item = itemsRepo.findById(barcode).get();
        Item.setPhotoUrl("https://brickphotos.s3.amazonaws.com/"+photourl);
        itemsRepo.save(Item);
    }



}
