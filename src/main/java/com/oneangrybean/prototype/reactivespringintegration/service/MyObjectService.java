package com.oneangrybean.prototype.reactivespringintegration.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.oneangrybean.prototype.reactivespringintegration.model.MyObject;
import com.oneangrybean.prototype.reactivespringintegration.model.MySecondaryInformation;
import com.oneangrybean.prototype.reactivespringintegration.model.MyTertiaryInformation;

import org.springframework.stereotype.Service;

@Service
public class MyObjectService {
    private List<MyObject> myObjectList;
    private List<MySecondaryInformation> mySecondaryInformationList;
    private List<MyTertiaryInformation> myTertiaryInformationList;

    @PostConstruct
    public void afterPropertiesSet() {
        
        final Supplier<Stream<Integer>> intStreamSupplier = () -> Stream.of(1,2,3);

        myObjectList =
            intStreamSupplier.get()
                    .map(id -> {
                        final MyObject obj = new MyObject();
                        obj.setId(String.valueOf(id));
                        obj.setName(String.format("MyObject[%d]", id));
                        return obj;
                    })
                    .collect(Collectors.toList());

        mySecondaryInformationList =
            intStreamSupplier.get()
                .map(id -> {
                    final MySecondaryInformation obj = new MySecondaryInformation();
                    obj.setId(String.valueOf(id));
                    obj.setData(String.format("Seconary Data [%d]", id));
                    return obj;
                })
                .collect(Collectors.toList());

        myTertiaryInformationList =
            intStreamSupplier.get()
                .map(id -> {
                    final MyTertiaryInformation obj = new MyTertiaryInformation();
                    obj.setId(String.valueOf(id));
                    obj.setData(String.format("Tertiary Data [%d]", id));
                    return obj;
                })
                .collect(Collectors.toList());
        
    }

    public List<String> list() {
        return myObjectList.stream().map(MyObject::getId).collect(Collectors.toList());
    }

    public Optional<MyObject> getMyObject(final String id) {
        return myObjectList.stream().filter(obj -> id.equals(obj.getId())).findFirst();
    }

    public Optional<MySecondaryInformation> getSecondaryInformation(final String id) {
        return mySecondaryInformationList.stream().filter(obj -> id.equals(obj.getId())).findFirst();
    }

    public Optional<MyTertiaryInformation> getTertiaryInformation(final String id) {
        return myTertiaryInformationList.stream().filter(obj -> id.equals(obj.getId())).findFirst();
    }
}
