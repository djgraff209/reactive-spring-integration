package com.oneangrybean.prototype.reactivespringintegration.handler;

import java.util.List;
import java.util.function.Supplier;

import com.oneangrybean.prototype.reactivespringintegration.model.MyObject;
import com.oneangrybean.prototype.reactivespringintegration.model.MySecondaryInformation;
import com.oneangrybean.prototype.reactivespringintegration.model.MyTertiaryInformation;
import com.oneangrybean.prototype.reactivespringintegration.service.MyObjectService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MyObjectController {
    private MyObjectService myObjectService;

    final Supplier<ResponseStatusException> exceptionSupplier =
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND);

    public MyObjectController(final MyObjectService myObjectService) {
        this.myObjectService = myObjectService;
    }

    @GetMapping(value = "/myobject")
    public CarrierObject<List<String>> list() {
        return CarrierObject.of(myObjectService.list());
    }

    @GetMapping(value = "/myobject/{id}")
    public CarrierObject<MyObject> getMyObject(@PathVariable("id") final String id) {
        return CarrierObject.of(myObjectService.getMyObject(id).orElseThrow(exceptionSupplier));
    }

    @GetMapping(value="/myobject/{id}/secondary")
    public CarrierObject<MySecondaryInformation> getSecondaryInformation(@PathVariable("id") final String id) {
        return CarrierObject.of(myObjectService.getSecondaryInformation(id).orElseThrow(exceptionSupplier));
    }
    

    @GetMapping(value="/myobject/{id}/tertiary")
    public CarrierObject<MyTertiaryInformation> getTertiaryInformation(@PathVariable("id") final String id) {
        return CarrierObject.of(myObjectService.getTertiaryInformation(id).orElseThrow(exceptionSupplier));
    }
}
