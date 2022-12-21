package com.jcpdev.brickinventoryserver.Controller;

import com.jcpdev.brickinventoryserver.Models.Items;
import com.jcpdev.brickinventoryserver.Repo.ItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;

import static com.jcpdev.brickinventoryserver.Constants.*;

@RestController
public class ApiControllers {


    @Autowired
    private ItemsRepo itemsRepo;


    @CrossOrigin
    @GetMapping(value = "/login/{password}")
    public String logIn(@PathVariable String password) {
        if (password.equals(PASSWORD)) {

            return BASEURL;

        } else {
            return "Wrong password";
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{token}")
    public List<Items> loadAllItems(@PathVariable int token) {
        if (token == TOKEN) {
            List<Items> List = itemsRepo.findAll();
            Collections.sort(List);
            return List;
        } else return null;

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{partnumber}")
    public Items getItemByPartNumber(@PathVariable int token, @PathVariable long partnumber) {
        if (token == TOKEN) {
            return itemsRepo.findByPartNumber(partnumber);
        } else return null;

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/changepartnumber/{newPartNumber}")
    public String changePartNumber(@PathVariable int token, @PathVariable long barcode, @PathVariable long newPartNumber) {
        if (token == TOKEN) {
            Items updateItem = itemsRepo.findById(barcode).get();
            if (!itemsRepo.existsByPartNumber(newPartNumber)) {
                updateItem.setPartNumber(newPartNumber);
                itemsRepo.save(updateItem);
                return "success";
            } else {
                return "Duplicate part number";
            }
        }else {
            return "Not logged in";
        }

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/additem")
    public void addItem(@PathVariable int token) {
        if (token == TOKEN) {
            Items newItem = new Items();
            newItem.setPhotoUrl("default.jpg");
            newItem.setName("item name");
            itemsRepo.save(newItem);
            Items updatedItem = itemsRepo.findById(newItem.getBarcode()).get();
            updatedItem.setPartNumber(newItem.getBarcode());
            itemsRepo.save(updatedItem);

        }

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/changequantity/{quantity}")
    public void changeQuantity(@PathVariable int token, @PathVariable long barcode, @PathVariable int quantity){
        if (token == TOKEN) {
            Items updatedItem = itemsRepo.findById(barcode).get();
            updatedItem.setQuantity(quantity);
            itemsRepo.save(updatedItem);
        }

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/setminquantity/{minquantity}")
    public void setMinQuantity(@PathVariable int token, @PathVariable long barcode, @PathVariable int minquantity) {
        if (token == TOKEN) {
            Items updatedItem = itemsRepo.findById(barcode).get();
            updatedItem.setMinimumQuantity(minquantity);
            itemsRepo.save(updatedItem);
        }

    }
    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/getminquantity/")
    public int getMinQuantity(@PathVariable int token, @PathVariable long barcode) {
        if (token == TOKEN) {
            Items Item = itemsRepo.findById(barcode).get();
            return Item.getMinimumQuantity();
        }
    return 0;
    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/quantity")
    public long getQuantity(@PathVariable int token, @PathVariable long barcode) {
        if (token == TOKEN) {
            return itemsRepo.findById(barcode).get().getQuantity();
        } else return 0;

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/delete")
    public void deleteItem(@PathVariable int token, @PathVariable long barcode) {
        if (token == TOKEN) {
            Items deleteItem = itemsRepo.findById(barcode).get();
            itemsRepo.delete(deleteItem);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/changephoto/{photourl}")
    public void changePhoto(@PathVariable int token, @PathVariable long barcode, @PathVariable String photourl) {
        if (token == TOKEN) {
            Items Item = itemsRepo.findById(barcode).get();
            Item.setPhotoUrl(PHOTOSURl + photourl);
            itemsRepo.save(Item);
        }

    }

    @CrossOrigin
    @GetMapping(value = "/{token}/{barcode}/changename/{name}")
    public void changeName(@PathVariable int token, @PathVariable long barcode, @PathVariable String name) {
        if (token == TOKEN) {
            Items Item = itemsRepo.findById(barcode).get();
            Item.setName(name);
            itemsRepo.save(Item);
        }

    }


}
