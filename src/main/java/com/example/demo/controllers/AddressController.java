package com.example.demo.controllers;

import com.example.demo.exceptions.address.InvalidAddressException;
import com.example.demo.models.user.Address;
import com.example.demo.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping(value = "address", method = RequestMethod.POST)
    public void addAddress(@RequestBody Address address) {
        addressService.addAddressToUser(address);
    }

    @RequestMapping(value = "address", method = RequestMethod.GET)
    public List<Address> getAddresses() {
        return addressService.getAddresses();
    }

    @RequestMapping(value = "address", method = RequestMethod.PUT)
    public void changeAddress(@RequestBody Address address) {
        addressService.changeAddress(address);
    }

    @RequestMapping(value = "address/{id}", method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
    }

    @ExceptionHandler
    public String handleValidateExceptions(InvalidAddressException ex){
        return ex.getMessage();
    }


}
